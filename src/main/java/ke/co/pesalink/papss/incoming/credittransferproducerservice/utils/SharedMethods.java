package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.SignatureValidationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.crypto.Data;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.*;


@Slf4j
@Component
public class SharedMethods {
    private final AppConfig appConfig;

    public SharedMethods(AppConfig appConfig) {
        this.appConfig = appConfig;
    }


    public synchronized KeyStore loadKeystore(InputStream is, char[] password, String keystoreType) throws KeyStoreException {
        try {
            KeyStore keyStore = KeyStore.getInstance(keystoreType);

            keyStore.load(is, password);

            return keyStore;

        } catch (Exception var7) {
            throw new KeyStoreException(var7);
        }
    }

    protected static Random random() {
        return new Random();
    }


    public static String generateUniqueTransactionId() {
        final Random random = random();
        // adapter prefix
        final String adapter_prefix = "AD";

        StringBuilder stringBuilder = new StringBuilder().append(adapter_prefix);

        final int transactionIdLength = 10;

        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";


        for (int i =0; i<= transactionIdLength; i++) {
            stringBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return stringBuilder.toString();
    }


    private String generateSignature(Data data, X509Certificate signingCert, PrivateKey signingKey)
            throws SignatureException {
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

        try {
            List<Transform> trfs = new ArrayList();
            trfs.add(fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature",
                    (TransformParameterSpec) null));
            trfs.add(fac.newCanonicalizationMethod("http://www.w3.org/2006/12/xml-c14n11",
                    (C14NMethodParameterSpec) null));
            Reference ref = fac.newReference("", fac.newDigestMethod("http://www.w3.org/2001/04/xmlenc#sha256", null),
                    trfs, null, null);
            String sigAlg = "";
            if (signingKey instanceof ECPrivateKey) {
                sigAlg = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";
            } else {
                if (!(signingKey instanceof RSAPrivateKey)) {
                    throw new IllegalArgumentException("Uknown PrivateKeyType: " + signingKey.getClass());
                }

                sigAlg = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
            }

            SignedInfo si = fac.newSignedInfo(
                    fac.newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315",
                            (C14NMethodParameterSpec) null),
                    fac.newSignatureMethod(sigAlg, null), Collections.singletonList(ref));
            KeyInfoFactory kif = fac.getKeyInfoFactory();
            List<Object> x509Content = new ArrayList();
            x509Content.add(signingCert.getSubjectDN().getName());
            x509Content
                    .add(kif.newX509IssuerSerial(signingCert.getIssuerDN().getName(), signingCert.getSerialNumber()));
            X509Data xd = kif.newX509Data(x509Content);
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            CanonicalizationMethod canonicalizationMethod = fac.newCanonicalizationMethod(
                    "http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (C14NMethodParameterSpec) null);
            OctetStreamData transformedData = (OctetStreamData) canonicalizationMethod.transform(data, null);
            Document doc = dbf.newDocumentBuilder().parse(transformedData.getOctetStream());
            Node parentNode = null;
            NodeList parentList = doc.getElementsByTagNameNS("*", "Sgntr");
            // Node parentNode;
            if (parentList.getLength() == 0) {
                parentList = doc.getElementsByTagNameNS("*", "AppHdr");
                parentNode = doc.createElementNS(parentList.item(0).getFirstChild().getNextSibling().getNamespaceURI(),
                        "Sgntr");
                parentNode = parentList.item(0).appendChild(parentNode);
            } else {
                parentNode = parentList.item(0);
            }

            DOMSignContext dsc = new DOMSignContext(signingKey, parentNode);
            XMLSignature signature = fac.newXMLSignature(si, ki);
            signature.sign(dsc);
            StringWriter swr = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            trans.transform(new DOMSource(doc), new StreamResult(swr));
            return swr.toString();
        } catch (Exception var24) {
            throw new SignatureException("Error signing data", var24);
        }
    }


    public boolean validateSignature(String xml) throws SignatureValidationException{
        Document xmlContent = buildDocument(xml);

        if (xmlContent == null) {
            throw new SignatureValidationException("Could not marshall payload to a DOM object");
        }

        NodeList nl = xmlContent.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        if (nl.getLength() == 0) {
            throw new SignatureValidationException("Cannot find Signature element");
        } else {
            XMLSignatureFactory fac = this.getXMLSignatureFactory();

            KeyStore keyStore;

            try{
               keyStore = loadKeystore(appConfig.getKeyStorePath().getInputStream(), appConfig.getKeyStorePassword().toCharArray(), appConfig.getKeyStoreType());
            }catch(IOException | KeyStoreException e) {
                throw new SignatureValidationException("Keystore failed to load");
            }


            if(keyStore == null) {
                throw new SignatureValidationException("Keystore could not be loaded");
            }

            DOMValidateContext valContext = new DOMValidateContext(
                    new KeyValueSelector(keyStore), nl.item(0));

            boolean coreValidity = false;

            try {
                XMLSignature signature = fac.unmarshalXMLSignature(valContext);
                boolean fullyProtected = false;

                Iterator var8 = signature.getSignedInfo().getReferences().iterator();

                label49: {
                    Reference ref;
                    do {
                        if (!var8.hasNext()) {
                            break label49;
                        }

                        Object o = var8.next();
                        ref = (Reference) o;
                    } while (ref.getURI() != null && !ref.getURI().isEmpty());

                    fullyProtected = true;
                }

                if (!fullyProtected) {
                    throw new SignatureValidationException("The signature was not protecting the whole message");
                }

                coreValidity = signature.validate(valContext);
                if (!coreValidity) {
                    log.warn("Signature failed core validation");
                    boolean sv = signature.getSignatureValue().validate(valContext);
                    log.warn("signature validation status: " + sv);
                    Iterator<?> i = signature.getSignedInfo().getReferences().iterator();

                    for (int j = 0; i.hasNext(); ++j) {
                        boolean refValid = ((Reference) i.next()).validate(valContext);
                        log.warn("ref[" + j + "] validity status: " + refValid);
                    }
                } else {
                    X509Certificate siginingCert = ((SimpleKeySelectorResult) signature
                            .getKeySelectorResult()).getCert();
                    Date now = new Date();
                    if (siginingCert.getNotAfter().before(now) || siginingCert.getNotBefore().after(now)
                            || !this.validateRevocation(siginingCert)) {
                        throw new SignatureValidationException(
                                "Signature validation failed. Certificate not valid anymore/yet");
                    }
                }
            } catch (MarshalException | XMLSignatureException var12) {
                log.error("Failed validating XML signature", var12);
            }

            return coreValidity;
        }
    }

    protected boolean validateRevocation(X509Certificate cert) {
        return true;
    }

    private static class SimpleKeySelectorResult implements KeySelectorResult {
        private final X509Certificate cert;

        SimpleKeySelectorResult(X509Certificate cert) {
            this.cert = cert;
        }
        public X509Certificate getCert() {
            return this.cert;
        }
        public Key getKey() {
            return this.cert.getPublicKey();
        }
    }

    protected XMLSignatureFactory getXMLSignatureFactory() {
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        return fac;
    }

    protected org.w3c.dom.Document buildDocument(String xml)  {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultNSInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            return documentBuilder.parse(new InputSource(new StringReader(xml)));

        }catch(ParserConfigurationException | IOException | SAXException e) {
            log.error("Failed to extract document");
            return null;
        }
    }
}
