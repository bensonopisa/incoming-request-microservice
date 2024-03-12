package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class XmlCalendarAdapter extends XmlAdapter<String, Calendar> {

    private DatatypeFactory datatypeFactory;

    XmlCalendarAdapter() throws DatatypeConfigurationException {
        this.datatypeFactory = DatatypeFactory.newInstance();
    }
    @Override
    public Calendar unmarshal(String s) throws Exception {
        return datatypeFactory.newXMLGregorianCalendar(s).toGregorianCalendar();
    }

    @Override
    public String marshal(Calendar calendar) throws Exception {
       return datatypeFactory.newXMLGregorianCalendar((GregorianCalendar) calendar).toXMLFormat();
    }
}
