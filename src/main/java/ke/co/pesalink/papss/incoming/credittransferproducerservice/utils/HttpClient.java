package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;


import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpClient {
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    private final AppConfig appConfig;
    public HttpClient(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    public ResponseEntity<String> makeHttpCall(String path, HttpMethod method, @Nullable String body, Map<String, String> additionalHeaders) throws RestClientException {
        RequestEntity<?> requestEntity;

        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getPapssIpsDns())
                .port(appConfig.getPapssIpsPort())
                .path(path)
                .build().toUri();


        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        httpHeaders.add(appConfig.getPapssChannelHeaderKey(), appConfig.getPapssChannelHeaderValue());
        httpHeaders.add(appConfig.getPapssVersionHeaderKey(), appConfig.getPapssVersionHeaderKey());

        for (Map.Entry<String,String> entry : additionalHeaders.entrySet()) {
            httpHeaders.add(entry.getKey(), entry.getValue());
        }

        if (method.matches("GET")) {
            requestEntity = RequestEntity.get(url).headers(httpHeaders).build();
        }else {
            RequestEntity.BodyBuilder bodyBuilder = RequestEntity.post(url);

            if (body == null) {
                requestEntity = bodyBuilder.headers(httpHeaders).build();
            }else {
                requestEntity = bodyBuilder.headers(httpHeaders).body(body);
            }
        }

        logger.info("Sending request to {} ", Map.of("url",url));

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        logger.info("Received response {}.", response);

        return response;
    }

    public ResponseEntity<String> makeHttpCall(String path, HttpMethod method) {
        return makeHttpCall(path, method, null, new HashMap<>());
    }
}
