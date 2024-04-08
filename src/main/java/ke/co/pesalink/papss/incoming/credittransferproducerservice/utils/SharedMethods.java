package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.SignatureValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.crypto.*;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class SharedMethods {
    private final AppConfig appConfig;

    private final HttpClient httpClient;

    protected static Random random() {
        return new Random();
    }


    public static String generateUniqueTransactionId() {
        final Random random = random();
        // adapter prefix
        final String adapter_prefix = "AD";

        StringBuilder stringBuilder = new StringBuilder().append(adapter_prefix);

        final int transactionIdLength = 10;

        final String CHARACTERS = Constants.CHARACTERS;


        for (int i =0; i<= transactionIdLength; i++) {
            stringBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return stringBuilder.toString();
    }

    @SuppressWarnings({"unused" })
    public void  validateSignature(String xml) throws ParserConfigurationException, IOException, SAXException, MarshalException, XMLSignatureException, KeyStoreException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        boolean coreValidity;

        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(new InputSource(new StringReader(xml)));

        NodeList nodeList = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

        if (nodeList.getLength() == 0) {
            throw new SignatureValidationException("Cannot find signature element");
        }

        List<X509Certificate> keyStoreCerts = certificateList();

        DOMValidateContext context = new DOMValidateContext(new KeySelector() {
            @Override
            public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context) throws KeySelectorException {
                if (keyInfo == null ){
                    throw new KeySelectorException("digital signature keyinfo is null");
                }

                SignatureMethod sm = (SignatureMethod) method;

                List<?> infoContent = keyInfo.getContent();

                String sub = null;
                X509IssuerSerial issuerSerial = null;

                X509Certificate matchedCert = null;

                for(Object o : infoContent) {
                    if (o instanceof X509Data x509Data) {
                        List<?> elements = x509Data.getContent();

                        for (Object element : elements) {
                            if (element instanceof String s) {
                                sub = s;
                            }
                            if (element instanceof X509IssuerSerial x509IssuerSerial) {
                                issuerSerial = x509IssuerSerial;
                            }
                        }
                    }
                }

                if (issuerSerial != null && StringUtils.isNotEmpty(sub)) {
                    for (X509Certificate cert : keyStoreCerts) {
                        if (cert.getSerialNumber().equals(issuerSerial.getSerialNumber())
                                && cert.getIssuerX500Principal().getName().equals(issuerSerial.getIssuerName())
                                && !cert.getSubjectX500Principal().getName().isEmpty() && cert.getSubjectX500Principal().getName().equals(sub)) {
                            matchedCert = cert;
                        }
                    }
                }

                if (matchedCert == null) {
                    throw new KeySelectorException("Cannot retrieve certificate using x509Data");
                }
                // check for certificate validity

                if (algEquals(sm.getAlgorithm(), matchedCert.getPublicKey().getAlgorithm())) {
                    return new SimpleKeySelectorResult(matchedCert);
                }

                throw new KeySelectorException("No Key element found");

            }
        }, nodeList.item(0));


        XMLSignatureFactory xmlSignatureFactory = XMLSignatureFactory.getInstance("DOM");

        XMLSignature signature = xmlSignatureFactory.unmarshalXMLSignature(context);


        coreValidity = signature.validate(context);


        if (!coreValidity) {
            log.error("Signature validation failed");
            boolean sv = signature.getSignatureValue().validate(context);

            log.error("Signature validation status: {}", sv);

            Iterator<Reference> iterator = signature.getSignedInfo().getReferences().iterator();

            for (int j = 0; iterator.hasNext(); j++) {
                Reference ref = iterator.next();
                log.info("[Ref {} validity status ==> {}", j, ref.validate(context));
            }
        }

        if (!coreValidity) {
            throw new XMLSignatureException("Signature validation failed.");
        }
    }

    private static class SimpleKeySelectorResult implements KeySelectorResult {
        private final X509Certificate certificate;

        SimpleKeySelectorResult(X509Certificate certificate) {
            this.certificate = certificate;
        }

        public Key getKey( ) {
            return certificate.getPublicKey();
        }
    }


    private boolean algEquals(String algURI, String algName) {
        if (algName.equalsIgnoreCase("DSA") && algURI.equalsIgnoreCase("http://www.w3.org/2000/09/xmldsig#dsa-sha1")) {
            return true;
        }else if (algName.equalsIgnoreCase("RSA") && algURI.equalsIgnoreCase("http://www.w3.org/2000/09/xmldsig#rsa-sha1")) {
            return true;
        }
        else if (algName.equalsIgnoreCase("EC") && algURI.equalsIgnoreCase("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256")) {
            return true;
        }
        else {
            return algName.equalsIgnoreCase("RSA") && algURI.equalsIgnoreCase("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
        }
    }
    private List<X509Certificate> certificateList() throws KeyStoreException {
       KeyStore keyStore = getKeyStore();

       List<X509Certificate> certs = new ArrayList<>();

       Enumeration<String> aliases = keyStore.aliases();

       while(aliases.hasMoreElements()) {
           String alias = aliases.nextElement();

           X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
           certs.add(certificate);
       }

       return certs;
    }


    protected KeyStore getKeyStore() {
        KeyStore keystore = null;
        try {
            keystore = KeyStoreUtils.loadKeyStore(appConfig.getKeyStoreProvider(),appConfig.getKeyStorePath(), appConfig.getKeyStorePassword().toCharArray(), appConfig.getKeyStoreType());
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            log.error("Error loading keystore {}", e.getMessage());
        }
        return keystore;
    }

    public ResponseEntity<String> makeApiRequest() {
        ResponseEntity<String> response = null;
        try {
            // passing new hashmap since all the
            response = httpClient.makeHttpCall(appConfig.getIpsMessagePath(), HttpMethod.GET);

        }catch(RestClientException rce) {
            final String error = "Error occurred while making api call.";

            if (rce instanceof ResourceAccessException e) {
                log.error(error + "{}", e.getLocalizedMessage());
            }
            if (rce.getCause() instanceof RestClientResponseException e) {
                log.error(error + "Status {}. Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            }
        }

        return response;
    }
}
