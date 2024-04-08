//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_004_001;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
//import org.w3._2001.xmlschema.Adapter1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * <p>Java class for OriginalGroupHeader2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OriginalGroupHeader2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OrgnlMsgId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.07}Max35Text"/&gt;
 *         &lt;element name="OrgnlMsgNmId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.07}Max35Text"/&gt;
 *         &lt;element name="OrgnlCreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.07}ISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="RtrRsnInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.07}PaymentReturnReason1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginalGroupHeader2", propOrder = {
    "orgnlMsgId",
    "orgnlMsgNmId",
    "orgnlCreDtTm",
    "rtrRsnInves"
})
public class OriginalGroupHeader2
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "OrgnlMsgId", required = true)
    protected String orgnlMsgId;
    @XmlElement(name = "OrgnlMsgNmId", required = true)
    protected String orgnlMsgNmId;
    @XmlElement(name = "OrgnlCreDtTm", type = String.class)
//    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar orgnlCreDtTm;
    @XmlElement(name = "RtrRsnInf")
    protected List<PaymentReturnReason1> rtrRsnInves;

    /**
     * Gets the value of the orgnlMsgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    /**
     * Sets the value of the orgnlMsgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlMsgId(String value) {
        this.orgnlMsgId = value;
    }

    /**
     * Gets the value of the orgnlMsgNmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlMsgNmId() {
        return orgnlMsgNmId;
    }

    /**
     * Sets the value of the orgnlMsgNmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlMsgNmId(String value) {
        this.orgnlMsgNmId = value;
    }

    /**
     * Gets the value of the orgnlCreDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getOrgnlCreDtTm() {
        return orgnlCreDtTm;
    }

    /**
     * Sets the value of the orgnlCreDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlCreDtTm(Calendar value) {
        this.orgnlCreDtTm = value;
    }

    /**
     * Gets the value of the rtrRsnInves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the rtrRsnInves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRtrRsnInves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentReturnReason1 }
     * 
     * 
     */
    public List<PaymentReturnReason1> getRtrRsnInves() {
        if (rtrRsnInves == null) {
            rtrRsnInves = new ArrayList<PaymentReturnReason1>();
        }
        return this.rtrRsnInves;
    }

}
