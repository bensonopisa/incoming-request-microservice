package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

public interface RequestProcessorService {
    void processRequests(Iterable<Object> requestObjects);
}
