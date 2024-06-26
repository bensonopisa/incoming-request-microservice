//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.acmt_023_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;


/**
 * <p>Java class for IdentificationVerification2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentificationVerification2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:acmt.023.001.02}Max35Text"/&gt;
 *         &lt;element name="PtyAndAcctId" type="{urn:iso:std:iso:20022:tech:xsd:acmt.023.001.02}IdentificationInformation2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificationVerification2", propOrder = {
    "id",
    "ptyAndAcctId"
})
public class IdentificationVerification2
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "PtyAndAcctId", required = true)
    protected IdentificationInformation2 ptyAndAcctId;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the ptyAndAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificationInformation2 }
     *     
     */
    public IdentificationInformation2 getPtyAndAcctId() {
        return ptyAndAcctId;
    }

    /**
     * Sets the value of the ptyAndAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificationInformation2 }
     *     
     */
    public void setPtyAndAcctId(IdentificationInformation2 value) {
        this.ptyAndAcctId = value;
    }

}
