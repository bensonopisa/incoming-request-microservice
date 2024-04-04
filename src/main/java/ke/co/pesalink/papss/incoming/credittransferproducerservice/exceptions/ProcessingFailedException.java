package ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions;

public class ProcessingFailedException extends RuntimeException{

    public ProcessingFailedException(String message) {
        super(message);
    }

    public ProcessingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
