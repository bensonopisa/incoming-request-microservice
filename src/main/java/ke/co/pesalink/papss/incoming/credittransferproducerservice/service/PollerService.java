package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

/**
 * This is the service that is used to poll pending messages from PAPSS
 * at regular interval.
 * returns a collection of message requests.
 */
public interface PollerService {
    // polls for  messages from PAPSS switch
    Iterable<Object> fetchMessages();
}
