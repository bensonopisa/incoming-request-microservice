//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.camt_029_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;


/**
 * <p>Java class for CorrectiveTransaction2Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorrectiveTransaction2Choice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Initn" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}CorrectivePaymentInitiation2"/&gt;
 *         &lt;element name="IntrBk" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}CorrectiveInterbankTransaction1"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorrectiveTransaction2Choice", propOrder = {
    "intrBk",
    "initn"
})
public class CorrectiveTransaction2Choice
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "IntrBk")
    protected CorrectiveInterbankTransaction1 intrBk;
    @XmlElement(name = "Initn")
    protected CorrectivePaymentInitiation2 initn;

    /**
     * Gets the value of the intrBk property.
     * 
     * @return
     *     possible object is
     *     {@link CorrectiveInterbankTransaction1 }
     *     
     */
    public CorrectiveInterbankTransaction1 getIntrBk() {
        return intrBk;
    }

    /**
     * Sets the value of the intrBk property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectiveInterbankTransaction1 }
     *     
     */
    public void setIntrBk(CorrectiveInterbankTransaction1 value) {
        this.intrBk = value;
    }

    /**
     * Gets the value of the initn property.
     * 
     * @return
     *     possible object is
     *     {@link CorrectivePaymentInitiation2 }
     *     
     */
    public CorrectivePaymentInitiation2 getInitn() {
        return initn;
    }

    /**
     * Sets the value of the initn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectivePaymentInitiation2 }
     *     
     */
    public void setInitn(CorrectivePaymentInitiation2 value) {
        this.initn = value;
    }

}
