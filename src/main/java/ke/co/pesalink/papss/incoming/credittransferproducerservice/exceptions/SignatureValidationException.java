package ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions;

public class SignatureValidationException extends RuntimeException{
    public SignatureValidationException(String message) {
        super(message);
    }

    public SignatureValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
