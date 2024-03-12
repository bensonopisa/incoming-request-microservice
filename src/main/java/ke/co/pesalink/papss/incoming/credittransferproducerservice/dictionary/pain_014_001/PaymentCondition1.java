//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pain_014_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;


/**
 * <p>Java class for PaymentCondition1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentCondition1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AmtModAllwd" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}TrueFalseIndicator"/&gt;
 *         &lt;element name="EarlyPmtAllwd" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}TrueFalseIndicator"/&gt;
 *         &lt;element name="DelyPnlty" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}Max140Text" minOccurs="0"/&gt;
 *         &lt;element name="ImdtPmtRbt" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}AmountOrRate1Choice" minOccurs="0"/&gt;
 *         &lt;element name="GrntedPmtReqd" type="{urn:iso:std:iso:20022:tech:xsd:pain.014.001.07}TrueFalseIndicator"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentCondition1", propOrder = {
    "amtModAllwd",
    "earlyPmtAllwd",
    "delyPnlty",
    "imdtPmtRbt",
    "grntedPmtReqd"
})
public class PaymentCondition1
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "AmtModAllwd")
    protected boolean amtModAllwd;
    @XmlElement(name = "EarlyPmtAllwd")
    protected boolean earlyPmtAllwd;
    @XmlElement(name = "DelyPnlty")
    protected String delyPnlty;
    @XmlElement(name = "ImdtPmtRbt")
    protected AmountOrRate1Choice imdtPmtRbt;
    @XmlElement(name = "GrntedPmtReqd")
    protected boolean grntedPmtReqd;

    /**
     * Gets the value of the amtModAllwd property.
     * 
     */
    public boolean isAmtModAllwd() {
        return amtModAllwd;
    }

    /**
     * Sets the value of the amtModAllwd property.
     * 
     */
    public void setAmtModAllwd(boolean value) {
        this.amtModAllwd = value;
    }

    /**
     * Gets the value of the earlyPmtAllwd property.
     * 
     */
    public boolean isEarlyPmtAllwd() {
        return earlyPmtAllwd;
    }

    /**
     * Sets the value of the earlyPmtAllwd property.
     * 
     */
    public void setEarlyPmtAllwd(boolean value) {
        this.earlyPmtAllwd = value;
    }

    /**
     * Gets the value of the delyPnlty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelyPnlty() {
        return delyPnlty;
    }

    /**
     * Sets the value of the delyPnlty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelyPnlty(String value) {
        this.delyPnlty = value;
    }

    /**
     * Gets the value of the imdtPmtRbt property.
     * 
     * @return
     *     possible object is
     *     {@link AmountOrRate1Choice }
     *     
     */
    public AmountOrRate1Choice getImdtPmtRbt() {
        return imdtPmtRbt;
    }

    /**
     * Sets the value of the imdtPmtRbt property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountOrRate1Choice }
     *     
     */
    public void setImdtPmtRbt(AmountOrRate1Choice value) {
        this.imdtPmtRbt = value;
    }

    /**
     * Gets the value of the grntedPmtReqd property.
     * 
     */
    public boolean isGrntedPmtReqd() {
        return grntedPmtReqd;
    }

    /**
     * Sets the value of the grntedPmtReqd property.
     * 
     */
    public void setGrntedPmtReqd(boolean value) {
        this.grntedPmtReqd = value;
    }

}
