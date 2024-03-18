package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Random;

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

    protected static Random random() {
        return new Random();
    }


    public static String generateUniqueTransactionId() {
        final Random random = random();
        final String prefix = "AD";

        StringBuilder stringBuilder = new StringBuilder().append(prefix);

        final int transactionIdLength = 10;

        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";


        for (int i =0; i<= transactionIdLength; i++) {
            stringBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return stringBuilder.toString();
    }
}
