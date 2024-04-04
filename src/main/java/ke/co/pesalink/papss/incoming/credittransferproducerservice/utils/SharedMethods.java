package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.SignatureValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;


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

    private void validateSignature(Document document) throws SignatureValidationException{
        if (document == null) {
            throw new SignatureValidationException("Could not marshall payload to a DOM object");
        }

        NodeList nl = document.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
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

            if (!coreValidity) {
                throw new SignatureValidationException("Signature validation failed");
            }
        }
    }

    public boolean validateSignature(String xml){
        boolean isValid = false;
        Document document = buildDocument(xml);

        try {
            validateSignature(document);
            isValid = true;
        }catch(SignatureValidationException e) {
            log.error("Signature validation failed ", e);
        }
        return isValid;
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
        return XMLSignatureFactory.getInstance("DOM");
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
