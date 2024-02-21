package ke.co.pesalink.papss.incoming.credittransferproducerservice.service.impl;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.service.PollerService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PollerServiceImpl implements PollerService {
    private final RestClient restClient;
    public PollerServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Iterable<Object> fetchMessages() {
        return restClient
                .get()
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
