//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package montran.com.rtp.sttlm;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.math.BigInteger;


/**
 * <p>Java class for SettlementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SettlementType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nsi" type="{urn:com.montran:rtp:sttlm.01}NetSettlementType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="reference" use="required" type="{urn:com.montran:rtp:sttlm.01}ReferenceType" /&gt;
 *       &lt;attribute name="spaceSequence" type="{urn:com.montran:rtp:sttlm.01}SequenceType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SettlementType", propOrder = {
    "nsi"
})
public class SettlementType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(required = true)
    protected NetSettlementType nsi;
    @XmlAttribute(name = "reference", required = true)
    protected String reference;
    @XmlAttribute(name = "spaceSequence")
    protected BigInteger spaceSequence;

    /**
     * Gets the value of the nsi property.
     * 
     * @return
     *     possible object is
     *     {@link NetSettlementType }
     *     
     */
    public NetSettlementType getNsi() {
        return nsi;
    }

    /**
     * Sets the value of the nsi property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetSettlementType }
     *     
     */
    public void setNsi(NetSettlementType value) {
        this.nsi = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Gets the value of the spaceSequence property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSpaceSequence() {
        return spaceSequence;
    }

    /**
     * Sets the value of the spaceSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSpaceSequence(BigInteger value) {
        this.spaceSequence = value;
    }

}
