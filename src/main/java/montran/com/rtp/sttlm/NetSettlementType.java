//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package montran.com.rtp.sttlm;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for NetSettlementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NetSettlementType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nsi-part" type="{urn:com.montran:rtp:sttlm.01}NetSettlementInstruction" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="totalDebit" use="required" type="{urn:com.montran:rtp:sttlm.01}AmountType" /&gt;
 *       &lt;attribute name="totalCredit" use="required" type="{urn:com.montran:rtp:sttlm.01}AmountType" /&gt;
 *       &lt;attribute name="ccy" use="required" type="{urn:com.montran:rtp:sttlm.01}CurrencyType" /&gt;
 *       &lt;attribute name="reversedPosting" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetSettlementType", propOrder = {
    "nsiParts"
})
public class NetSettlementType
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "nsi-part", required = true)
    protected List<NetSettlementInstruction> nsiParts;
    @XmlAttribute(name = "totalDebit", required = true)
    protected BigDecimal totalDebit;
    @XmlAttribute(name = "totalCredit", required = true)
    protected BigDecimal totalCredit;
    @XmlAttribute(name = "ccy", required = true)
    protected String ccy;
    @XmlAttribute(name = "reversedPosting", required = true)
    protected boolean reversedPosting;

    /**
     * Gets the value of the nsiParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the nsiParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNsiParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NetSettlementInstruction }
     * 
     * 
     */
    public List<NetSettlementInstruction> getNsiParts() {
        if (nsiParts == null) {
            nsiParts = new ArrayList<NetSettlementInstruction>();
        }
        return this.nsiParts;
    }

    /**
     * Gets the value of the totalDebit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    /**
     * Sets the value of the totalDebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalDebit(BigDecimal value) {
        this.totalDebit = value;
    }

    /**
     * Gets the value of the totalCredit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    /**
     * Sets the value of the totalCredit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalCredit(BigDecimal value) {
        this.totalCredit = value;
    }

    /**
     * Gets the value of the ccy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcy(String value) {
        this.ccy = value;
    }

    /**
     * Gets the value of the reversedPosting property.
     * 
     */
    public boolean isReversedPosting() {
        return reversedPosting;
    }

    /**
     * Sets the value of the reversedPosting property.
     * 
     */
    public void setReversedPosting(boolean value) {
        this.reversedPosting = value;
    }

}
