//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package montran.positions;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * <p>Java class for ConditionInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConditionInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="accountCode" use="required" type="{urn:montran:positions.001}Max15Text" /&gt;
 *       &lt;attribute name="ccy" use="required" type="{urn:montran:positions.001}ActiveCurrencyCode" /&gt;
 *       &lt;attribute name="condType" type="{urn:montran:positions.001}ConditionType" /&gt;
 *       &lt;attribute name="balance" type="{urn:montran:positions.001}BalanceAmount_SimpleType" /&gt;
 *       &lt;attribute name="overdraft" type="{urn:montran:positions.001}Amount_SimpleType" /&gt;
 *       &lt;attribute name="debitAmount" type="{urn:montran:positions.001}Amount_SimpleType" /&gt;
 *       &lt;attribute name="debitCount" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="creditAmount" type="{urn:montran:positions.001}Amount_SimpleType" /&gt;
 *       &lt;attribute name="creditCount" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConditionInfo")
public class ConditionInfo
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlAttribute(name = "accountCode", required = true)
    protected String accountCode;
    @XmlAttribute(name = "ccy", required = true)
    protected String ccy;
    @XmlAttribute(name = "condType")
    protected ConditionType condType;
    @XmlAttribute(name = "balance")
    protected BigDecimal balance;
    @XmlAttribute(name = "overdraft")
    protected BigDecimal overdraft;
    @XmlAttribute(name = "debitAmount")
    protected BigDecimal debitAmount;
    @XmlAttribute(name = "debitCount")
    protected BigInteger debitCount;
    @XmlAttribute(name = "creditAmount")
    protected BigDecimal creditAmount;
    @XmlAttribute(name = "creditCount")
    protected BigInteger creditCount;

    /**
     * Gets the value of the accountCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * Sets the value of the accountCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountCode(String value) {
        this.accountCode = value;
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
     * Gets the value of the condType property.
     * 
     * @return
     *     possible object is
     *     {@link ConditionType }
     *     
     */
    public ConditionType getCondType() {
        return condType;
    }

    /**
     * Sets the value of the condType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionType }
     *     
     */
    public void setCondType(ConditionType value) {
        this.condType = value;
    }

    /**
     * Gets the value of the balance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBalance(BigDecimal value) {
        this.balance = value;
    }

    /**
     * Gets the value of the overdraft property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOverdraft() {
        return overdraft;
    }

    /**
     * Sets the value of the overdraft property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOverdraft(BigDecimal value) {
        this.overdraft = value;
    }

    /**
     * Gets the value of the debitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    /**
     * Sets the value of the debitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebitAmount(BigDecimal value) {
        this.debitAmount = value;
    }

    /**
     * Gets the value of the debitCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDebitCount() {
        return debitCount;
    }

    /**
     * Sets the value of the debitCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDebitCount(BigInteger value) {
        this.debitCount = value;
    }

    /**
     * Gets the value of the creditAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    /**
     * Sets the value of the creditAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditAmount(BigDecimal value) {
        this.creditAmount = value;
    }

    /**
     * Gets the value of the creditCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCreditCount() {
        return creditCount;
    }

    /**
     * Sets the value of the creditCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCreditCount(BigInteger value) {
        this.creditCount = value;
    }

}
