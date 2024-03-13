package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.UnmarshallException;
import montran.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.reader.StreamReader;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyStore;
import java.util.Iterator;

@Component
public class XmlUtils {

    private final DocumentBuilderFactory documentBuilderFactory;

    private final Logger logger = LoggerFactory.getLogger(XmlUtils.class);
    private final AppConfig appConfig;


    private final SharedMethods sharedMethods;

    public XmlUtils(AppConfig appConfig) {
        this.appConfig = appConfig;
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        sharedMethods = new SharedMethods();
    }

    public boolean validateRequestSignature(String xmlBody) throws ParserConfigurationException, SAXException, IOException, MarshalException, XMLSignatureException {
        DocumentBuilder documentBuilder  = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(new InputSource(new StringReader(xmlBody)));

        NodeList nodeList = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

        if(nodeList.getLength() == 0) {
            throw new IOException("Cannot find the signature element");
        }

        KeyStore keyStore = sharedMethods.loadKeystore(appConfig.getKeyStorePath().getFile().getPath(), appConfig.getKeyStorePassword().toCharArray(), appConfig.getKeyStoreType());

        DOMValidateContext domValidateContext = new DOMValidateContext(new KeyValueSelector(keyStore), nodeList.item(0));

        XMLSignatureFactory factory =
                XMLSignatureFactory.getInstance("DOM");

        XMLSignature signature =
                factory.unmarshalXMLSignature(domValidateContext);

        boolean coreValidity = signature.validate(domValidateContext);

        if (coreValidity) {
            logger.info("Signature validation Successful");

        }else {
            boolean sv =
                    signature.getSignatureValue().validate(domValidateContext);
            logger.info("Signature validation status {}", sv);

            Iterator<?> i = signature.getSignedInfo().getReferences().iterator();

            for (int j=0; i.hasNext(); j++) {
                boolean refValid = ((Reference) i.next()).validate(domValidateContext);
                logger.info("ref[{}] validity status: {} " , j , refValid);
            }
        }
        return coreValidity;
    }

    public Message parseXml(String xml) throws UnmarshallException{
        JAXBContext jaxbContext;

        try {
            jaxbContext = JAXBContext.newInstance(Message.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Message message = (Message) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));

            logger.info("Message received {}", message);

            return message;
        }catch(JAXBException ex) {
            throw new UnmarshallException("Error during unmarshalling ", ex);
        }
    }
}
