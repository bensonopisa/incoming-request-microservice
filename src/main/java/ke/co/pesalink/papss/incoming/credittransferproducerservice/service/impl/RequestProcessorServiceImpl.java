package ke.co.pesalink.papss.incoming.credittransferproducerservice.service.impl;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.dto.TransactionRequest;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.service.RequestProcessorService;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.service.RequestQueueHandler;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestProcessorServiceImpl implements RequestProcessorService {

    private final List<RequestQueueHandler> requestHandlers;

    @Override
    public void processRequests(Iterable<Object> requestObjects) {
        for(Object o : requestObjects) {
            boolean isValid = CommonUtil.validateRequestSignature(null, (String)o);

            if (!isValid) {
                // add it to the failed request
                // continue
                // need to know what to do with the failed request
            }

            // generate a unique adapter request id
            String requestId = RandomStringUtils.random(12, false, true);

            TransactionRequest transactionRequest = new TransactionRequest(requestId, (String) o, LocalDateTime.now());

            // save to the db

            // route to the queues based on the type of transaction
        }
    }
}
