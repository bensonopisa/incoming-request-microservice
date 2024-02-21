package ke.co.pesalink.papss.incoming.credittransferproducerservice.dto;

import java.time.LocalDateTime;

public record TransactionRequest(String adapterRequestId, String payload, LocalDateTime localDateTime) {
}
