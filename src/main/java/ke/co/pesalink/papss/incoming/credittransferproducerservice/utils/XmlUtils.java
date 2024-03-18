package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.UnmarshallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Iterator;

@Component
public class XmlUtils {
    private final Logger logger = LoggerFactory.getLogger(XmlUtils.class);
    private final AppConfig appConfig;

    public XmlUtils(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public boolean validateSignature(String xmlBody) throws ParserConfigurationException, SAXException, IOException, XMLSignatureException, MarshalException, KeyStoreException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultNSInstance();

        DocumentBuilder documentBuilder  = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(new InputSource(new StringReader(xmlBody)));

        NodeList nodeList = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

        if(nodeList.getLength() == 0) {
            throw new IOException("Cannot find the signature element");
        }

        SharedMethods sharedMethods = new SharedMethods();

        InputStream is = appConfig.getKeyStorePath().getInputStream();

        KeyStore keyStore = sharedMethods.loadKeystore(is, appConfig.getKeyStorePassword().toCharArray(), appConfig.getKeyStoreType());

        DOMValidateContext domValidateContext = new DOMValidateContext(new KeyValueSelector(keyStore), nodeList.item(0));

        // validate the signature securely
        domValidateContext.setProperty("org.jcp.xml.dsig.secureValidation", true);

        XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");

        XMLSignature signature = factory.unmarshalXMLSignature(domValidateContext);

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

    /**
     * @apiNote   function used to marshall messages to appropriate type
     * @param xmlBody the xml body in string format to be unmarshalled
     * @param clazz   the output class that holds the output
     * @return        an instance object of the generic type
     * @param <T>     generic identifier
     * @throws UnmarshallException  thrown when there is an error with the marshalling
     */
    public <T> Object unmarshall(String xmlBody , Class<T> clazz) throws UnmarshallException {
        JAXBContext jaxbContext;

        try {
            jaxbContext = JAXBContext.newInstance(clazz);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return unmarshaller.unmarshal(new StringReader(xmlBody));
        }catch(JAXBException jaxbException) {
            throw new UnmarshallException("Error when unmarshalling the message", jaxbException);
        }
    }

    public String marshall(Object data) throws Exception{
        JAXBContext jaxbContext;

        try {
            jaxbContext = JAXBContext.newInstance(data.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newDefaultNSInstance();

            dbFactory.setNamespaceAware(true);

            DocumentBuilder documentBuilder  = dbFactory.newDocumentBuilder();

            Document doc = documentBuilder.newDocument();

            marshaller.marshal(data, doc);

            return transform(doc);

        }catch(JAXBException jaxbException) {
            throw new UnmarshallException("Error when unmarshalling the message", jaxbException);
        }
    }

    protected String transform(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        StringWriter stringWriter = new StringWriter();

        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

        return stringWriter.toString();
    }
}
