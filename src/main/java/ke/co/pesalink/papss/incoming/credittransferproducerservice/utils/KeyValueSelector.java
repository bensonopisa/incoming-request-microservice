package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;

import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.*;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class KeyValueSelector extends KeySelector {

    private final KeyStore keyStore;
    public KeyValueSelector(KeyStore keyStore) {
        this.keyStore = keyStore;
    }
    @Override
    public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context) throws KeySelectorException {
        if (keyInfo == null) {
            throw new KeySelectorException("Null KeyInfo object!");
        }

        SignatureMethod sm = (SignatureMethod) method;
        List<?> list = keyInfo.getContent();

        for (Object o : list) {
            XMLStructure xmlStructure = (XMLStructure) o;

            KeySelectorResult ksr = null;

            if (xmlStructure instanceof X509Data x509Data) {
                try {
                    ksr = x509DataSelect(x509Data, sm);
                } catch (KeyStoreException ex) {
                    throw new KeySelectorException(ex);
                }
                if (ksr != null) {
                    return ksr;
                }
            }
        }

        // return null if no match was found
       try{
           Key pkey = keyStore.getCertificate("ca ").getPublicKey();
           return new SimpleKeySelectorResult(pkey);
       }catch (KeyStoreException ex) {
           throw new KeySelectorException(ex);
       }
    }


    private KeySelectorResult x509DataSelect(X509Data x509Data, SignatureMethod sm) throws KeyStoreException{
        KeySelectorResult ksr = null;

        Iterator<?> it = x509Data.getContent().iterator();

        while(it.hasNext()) {

            // check for issuer serial
            if (it.next() instanceof X509IssuerSerial x509IssuerSerial) {
                X509CertSelector x509CertSelector = new X509CertSelector();
                x509CertSelector.setSerialNumber(x509IssuerSerial.getSerialNumber());
                X500Principal principal = new X500Principal(x509IssuerSerial.getIssuerName());
                x509CertSelector.setIssuer(principal);

                ksr = keyStoreSelect(x509CertSelector, sm);
            }

            // check for subject name
            else if (it.next() instanceof String subjectName) {
                X509CertSelector certSelector = new X509CertSelector();
                certSelector.setSubject(new X500Principal(subjectName));

                ksr = keyStoreSelect(certSelector, sm);
            }

            if (ksr != null) {
                return ksr;
            }
        }
        return null;
    }

    private KeySelectorResult keyStoreSelect(CertSelector certSelector, SignatureMethod sm) throws KeyStoreException{
        Enumeration<String> aliases  = keyStore.aliases();
        while(aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
            if (cert != null && certSelector.match(cert) && algEquals(sm, cert.getSigAlgName()) ) {
                return new SimpleKeySelectorResult(cert.getPublicKey());
            }
        }
        return null;
    }

    /**
     * Simple key selector containing the public key
     */
    @Getter
    private static class SimpleKeySelectorResult implements KeySelectorResult {
        private final Key key;
        SimpleKeySelectorResult(Key key) {
            this.key = key;
        }
    }

    // compare the algorithms used
    private boolean algEquals(SignatureMethod sm, String signatureAlgorithm) {
        if (sm == null || signatureAlgorithm == null ){
            return false;
        }
        return StringUtils.compare(sm.getAlgorithm(), signatureAlgorithm) == 0;
    }
}
