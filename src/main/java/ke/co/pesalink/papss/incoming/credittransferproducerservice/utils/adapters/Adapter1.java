package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.adapters;

import jakarta.xml.bind.DatatypeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.Calendar;

public class Adapter1 extends XmlAdapter<String, Calendar> {

    public Calendar unmarshal(String value) {
        return DatatypeConverter.parseDate(value);
    }

    public String marshal(Calendar value) {
        return DatatypeConverter.printDateTime(value);
    }
}
