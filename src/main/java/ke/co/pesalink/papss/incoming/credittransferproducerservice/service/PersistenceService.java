package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import montran.message.Message;

public interface PersistenceService {
    void saveTransaction(Message message);
}
