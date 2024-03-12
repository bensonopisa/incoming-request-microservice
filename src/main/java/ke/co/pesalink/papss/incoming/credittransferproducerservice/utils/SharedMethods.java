package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;

public class SharedMethods {
    private final Logger logger = LoggerFactory.getLogger(SharedMethods.class);

    public synchronized KeyStore loadKeystore(String path, char[] password, String keystoreType) {
        KeyStore result = null;

        Provider provider = Security.getProvider("SunJCE");

        try {
            KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection(password);
            KeyStore.Builder builder = null;
            if (path == null) {
                builder = KeyStore.Builder.newInstance(keystoreType, provider, protection);
            } else {
                logger.info("Loading keystore from: " + path + " Type: " + keystoreType);

                if (!new File(path).exists()) {
                    throw new FileNotFoundException("Keystore not found at " + path);
                }

                builder = KeyStore.Builder.newInstance(keystoreType, null, new File(path), protection);
            }

            result = builder.getKeyStore();
        } catch (Exception var7) {
            result = null;
            logger.error("Could not load the keystore from: " + path, var7);
        }
        return result;
    }
}
