package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;


import org.apache.hc.core5.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Component
public class HttpClient {
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    public HttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> makeHttpCall(URI url, HttpMethod method, @Nullable String body, Map<String, String> additionalHeaders) throws HttpException {
        RequestEntity<?> requestEntity;

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        httpHeaders.add("X-PAPSSRTP-Channel", "KE9");
        httpHeaders.add("X-PAPSS-RTP-Version", "1");

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

        try {
            ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
            logger.info("Received response {}.", response);

            return response;

        }catch (RestClientException rce) {
            logger.error("An exception occured while initiating http request", rce);
            throw new HttpException("Error initiating http request ",rce);
        }
    }
}
