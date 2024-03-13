package ke.co.pesalink.papss.incoming.credittransferproducerservice.configs;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.SelectableAliasKeyManager;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.SharedMethods;
import lombok.RequiredArgsConstructor;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.*;

/**
 * Rest client configurations
 */
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final AppConfig appConfig;

    SharedMethods sharedMethods  = new SharedMethods();
    @Bean
    public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
            IOException, UnrecoverableKeyException {

        KeyStore keyStore = sharedMethods.loadKeystore(appConfig.getKeyStorePath().getInputStream(), appConfig.getKeyStorePassword().toCharArray(), appConfig.getKeyStoreType());

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

        final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(sslConFactory)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        restTemplate.setInterceptors();
        return restTemplate;
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
