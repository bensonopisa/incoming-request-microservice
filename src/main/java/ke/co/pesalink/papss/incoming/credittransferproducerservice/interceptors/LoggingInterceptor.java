package ke.co.pesalink.papss.incoming.credittransferproducerservice.interceptors;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.LoggerUtil;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        LoggerUtil.infoLog("Sending a request to poll messages from  "+ buildUrl(request));
        return execution.execute(request, body);
    }

    protected String buildUrl(HttpRequest request) {
        return request.getURI().getScheme() +
                "://" +
                request.getURI().getHost() +
                request.getURI().getPath();
    }
}
