//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_004_001;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Authorisation1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="Authorisation1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="AUTH"/&gt;
 *     &lt;enumeration value="FDET"/&gt;
 *     &lt;enumeration value="FSUM"/&gt;
 *     &lt;enumeration value="ILEV"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Authorisation1Code")
@XmlEnum
public enum Authorisation1Code {

    AUTH,
    FDET,
    FSUM,
    ILEV;

    public String value() {
        return name();
    }

    public static Authorisation1Code fromValue(String v) {
        return valueOf(v);
    }

}
