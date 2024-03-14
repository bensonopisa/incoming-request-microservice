package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

public class SharedMethods {
    public synchronized KeyStore loadKeystore(InputStream is, char[] password, String keystoreType) throws KeyStoreException {
        try {
            KeyStore keyStore = KeyStore.getInstance(keystoreType);

            keyStore.load(is, password);

            return keyStore;

        } catch (Exception var7) {
            throw new KeyStoreException(var7);
        }
    }
}
