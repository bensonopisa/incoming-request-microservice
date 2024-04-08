package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Slf4j
public class KeyStoreUtils {

    public static synchronized KeyStore loadKeyStore(String provider, Resource resource, char[] password, String keyStoreType) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore ks = null;
        try {
            KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection(password);
            KeyStore.Builder builder;

            if (resource.exists() && resource.isFile()) {
                log.info("Loading keystore from {}", resource.getURL().getPath());
                builder = KeyStore.Builder.newInstance(keyStoreType, null, resource.getFile(), protection);
            }else {
                builder = KeyStore.Builder.newInstance(keyStoreType, Security.getProvider(provider), protection);
            }

            ks = builder.getKeyStore();
        }catch(Exception e) {
            log.error("Error loading keystore from {}", resource.getURL().getPath(), e.getCause());
        }
        return ks;
    }
}
