package ke.co.pesalink.papss.incoming.credittransferproducerservice.dto;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.SharedMethods;

import java.time.LocalDateTime;

public record TransactionRequest(String request, String x_request_id, String action, LocalDateTime localDateTime) {
    public TransactionRequest(String request, String action){
        this(request, SharedMethods.generateUniqueTransactionId(), action, LocalDateTime.now());
    }
}
