//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.camt_007_002;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;


/**
 * <p>Java class for SettlementInformation15 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SettlementInformation15"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InstgRmbrsmntAgt" type="{urn:iso:std:iso:20022:tech:xsd:camt.007.002.03}BranchAndFinancialInstitutionIdentification4" minOccurs="0"/&gt;
 *         &lt;element name="InstgRmbrsmntAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:camt.007.002.03}CashAccount16" minOccurs="0"/&gt;
 *         &lt;element name="InstdRmbrsmntAgt" type="{urn:iso:std:iso:20022:tech:xsd:camt.007.002.03}BranchAndFinancialInstitutionIdentification4" minOccurs="0"/&gt;
 *         &lt;element name="InstdRmbrsmntAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:camt.007.002.03}CashAccount16" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SettlementInformation15", propOrder = {
    "instgRmbrsmntAgt",
    "instgRmbrsmntAgtAcct",
    "instdRmbrsmntAgt",
    "instdRmbrsmntAgtAcct"
})
public class SettlementInformation15
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "InstgRmbrsmntAgt")
    protected BranchAndFinancialInstitutionIdentification4 instgRmbrsmntAgt;
    @XmlElement(name = "InstgRmbrsmntAgtAcct")
    protected CashAccount16 instgRmbrsmntAgtAcct;
    @XmlElement(name = "InstdRmbrsmntAgt")
    protected BranchAndFinancialInstitutionIdentification4 instdRmbrsmntAgt;
    @XmlElement(name = "InstdRmbrsmntAgtAcct")
    protected CashAccount16 instdRmbrsmntAgtAcct;

    /**
     * Gets the value of the instgRmbrsmntAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4 getInstgRmbrsmntAgt() {
        return instgRmbrsmntAgt;
    }

    /**
     * Sets the value of the instgRmbrsmntAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public void setInstgRmbrsmntAgt(BranchAndFinancialInstitutionIdentification4 value) {
        this.instgRmbrsmntAgt = value;
    }

    /**
     * Gets the value of the instgRmbrsmntAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount16 }
     *     
     */
    public CashAccount16 getInstgRmbrsmntAgtAcct() {
        return instgRmbrsmntAgtAcct;
    }

    /**
     * Sets the value of the instgRmbrsmntAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount16 }
     *     
     */
    public void setInstgRmbrsmntAgtAcct(CashAccount16 value) {
        this.instgRmbrsmntAgtAcct = value;
    }

    /**
     * Gets the value of the instdRmbrsmntAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification4 getInstdRmbrsmntAgt() {
        return instdRmbrsmntAgt;
    }

    /**
     * Sets the value of the instdRmbrsmntAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification4 }
     *     
     */
    public void setInstdRmbrsmntAgt(BranchAndFinancialInstitutionIdentification4 value) {
        this.instdRmbrsmntAgt = value;
    }

    /**
     * Gets the value of the instdRmbrsmntAgtAcct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount16 }
     *     
     */
    public CashAccount16 getInstdRmbrsmntAgtAcct() {
        return instdRmbrsmntAgtAcct;
    }

    /**
     * Sets the value of the instdRmbrsmntAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount16 }
     *     
     */
    public void setInstdRmbrsmntAgtAcct(CashAccount16 value) {
        this.instdRmbrsmntAgtAcct = value;
    }

}
