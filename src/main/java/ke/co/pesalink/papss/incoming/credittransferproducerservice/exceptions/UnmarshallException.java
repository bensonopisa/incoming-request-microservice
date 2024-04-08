package ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions;

public class UnmarshallException extends RuntimeException {
    public UnmarshallException(String message) {
        super(message);
    }

    public UnmarshallException(String message, Throwable cause) {
        super(message, cause);
    }
}
