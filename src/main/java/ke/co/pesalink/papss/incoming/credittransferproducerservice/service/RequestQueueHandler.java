package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.dto.TransactionRequest;

public interface RequestQueueHandler {
    void queueRequest(TransactionRequest transactionRequest);
}
