package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.adapters;


import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XmlGregorianCalendarAdapter extends XmlAdapter<String, XMLGregorianCalendar> {

    private final DatatypeFactory factory;

    XmlGregorianCalendarAdapter() throws DatatypeConfigurationException {
        this.factory = DatatypeFactory.newInstance();
    }

    @Override
    public XMLGregorianCalendar unmarshal(String value) throws Exception {
        return factory.newXMLGregorianCalendar(value);
    }

    @Override
    public String marshal(XMLGregorianCalendar value) throws Exception {
        if (value != null) {
            return value.toXMLFormat();
        }
        return null;
    }

}