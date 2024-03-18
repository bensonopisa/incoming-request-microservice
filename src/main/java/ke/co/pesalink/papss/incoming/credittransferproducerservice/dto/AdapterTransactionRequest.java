package ke.co.pesalink.papss.incoming.credittransferproducerservice.dto;

import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.SharedMethods;

import java.time.LocalDateTime;

public record AdapterTransactionRequest(String payload, String x_request_id, String action, LocalDateTime localDateTime) {
    public AdapterTransactionRequest(String payload, String action, LocalDateTime localDateTime){
        this(payload, SharedMethods.generateUniqueTransactionId(), action, localDateTime);
    }
}
