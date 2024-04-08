package ke.co.pesalink.papss.incoming.credittransferproducerservice.configs;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.KeyStoreUtils;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.SelectableAliasKeyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
/**
 * Rest client configurations
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class RestTemplateConfig {

    private final AppConfig appConfig;
    @Bean
    public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        KeyStore keyStore;
        try {
            keyStore = KeyStoreUtils.loadKeyStore(appConfig.getKeyStoreProvider(), appConfig.getKeyStorePath(), appConfig.getKeyStorePassword().toCharArray(), appConfig.getKeyStoreType());
        }catch(KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new KeyStoreException(e);
        }

        KeyManager[] keyManagers = buildKeyManagers(keyStore, appConfig.getKeyStorePassword().toCharArray());
        TrustManager[] trustManagers = buildTrustManagers(keyStore);
        SelectableAliasKeyManager sakm = new SelectableAliasKeyManager((X509ExtendedKeyManager) keyManagers[0], appConfig.getKeyStoreAlias());

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(keyStore, TrustAllStrategy.INSTANCE)
                .loadKeyMaterial(keyStore, appConfig.getKeyStorePassword().toCharArray())
                .build();

        sslContext.init(new KeyManager[]{sakm}, trustManagers, new SecureRandom());

        SSLConnectionSocketFactory sslConFactory = SSLConnectionSocketFactoryBuilder.create()
                .setSslContext(sslContext)
                .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setTlsVersions(TLS.V_1_2)
                .build();

        // configuring the connection factory and timeouts
        final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(sslConFactory)
                .setDefaultConnectionConfig(connectionConfig())
                .build();

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }

    private ConnectionConfig connectionConfig() {
        return ConnectionConfig.custom()
                .setConnectTimeout(Timeout.of(appConfig.getConnectionTimeout()))
                .setSocketTimeout(Timeout.of(appConfig.getReadTimeout()))
                .build();
    }

    protected static KeyManager[] buildKeyManagers(KeyStore store, char[] password) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(store, password);
        return keyManagerFactory.getKeyManagers();
    }

    protected static TrustManager[] buildTrustManagers(KeyStore store) throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(store);
        return trustManagerFactory.getTrustManagers();
    }

}
