package ke.co.pesalink.papss.incoming.credittransferproducerservice.handlers;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.LoggerUtil;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;

import java.io.IOException;

@Component
public class CustomResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
       if (response.getStatusCode().is5xxServerError()) {
           // handle server errors e.g Bad Gateway
           LoggerUtil.errorLog(response.getStatusCode(), response.getStatusText());
       }
       if (response.getStatusCode().is4xxClientError()) {
           // handle client error e.g ConnectTimeout, SocketTimeout
           LoggerUtil.errorLog(response.getStatusCode(), response.getStatusText());
       }
    }
}
