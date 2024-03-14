//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.head_001_001;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.adapters.Adapter1;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Calendar;


/**
 * <p>Java class for BusinessApplicationHeaderV01 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessApplicationHeaderV01"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CharSet" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}UnicodeChartsCode" minOccurs="0"/&gt;
 *         &lt;element name="Fr" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}Party9Choice"/&gt;
 *         &lt;element name="To" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}Party9Choice"/&gt;
 *         &lt;element name="BizMsgIdr" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}Max35Text"/&gt;
 *         &lt;element name="MsgDefIdr" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}Max35Text"/&gt;
 *         &lt;element name="BizSvc" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="CreDt" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}ISONormalisedDateTime"/&gt;
 *         &lt;element name="CpyDplct" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}CopyDuplicate1Code" minOccurs="0"/&gt;
 *         &lt;element name="PssblDplct" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}YesNoIndicator" minOccurs="0"/&gt;
 *         &lt;element name="Prty" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}BusinessMessagePriorityCode" minOccurs="0"/&gt;
 *         &lt;element name="Sgntr" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}SignatureEnvelope" minOccurs="0"/&gt;
 *         &lt;element name="Rltd" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}BusinessApplicationHeader1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@Getter
@XmlAccessorType(XmlAccessType.FIELD )
@XmlType(name = "BusinessApplicationHeaderV01", propOrder = {
    "charSet",
    "fr",
    "to",
    "bizMsgIdr",
    "msgDefIdr",
    "bizSvc",
    "creDt",
    "cpyDplct",
    "pssblDplct",
    "prty",
    "sgntr",
    "rltd"
})
@XmlRootElement(name = "AppHdr", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.01")

public class AppHdr implements Serializable {
    private static final long serialVersionUID = -1L;
    @XmlElement(name = "CharSet")
    protected String charSet;
    @XmlElement(name = "Fr", required = true)
    protected Party9Choice fr;
    @XmlElement(name = "To", required = true)
    protected Party9Choice to;
    @XmlElement(name = "BizMsgIdr", required = true)
    protected String bizMsgIdr;
    @XmlElement(name = "MsgDefIdr", required = true)
    protected String msgDefIdr;
    @XmlElement(name = "BizSvc")
    protected String bizSvc;
    @XmlElement(name = "CreDt", required = true, type = String.class)
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(Adapter1.class)
    protected Calendar creDt;
    @XmlElement(name = "CpyDplct")
    @XmlSchemaType(name = "string")
    protected CopyDuplicate1Code cpyDplct;
    @XmlElement(name = "PssblDplct")
    protected Boolean pssblDplct;
    @XmlElement(name = "Prty")
    protected String prty;
    @XmlElement(name = "Sgntr")
    protected SignatureEnvelope sgntr;
    @XmlElement(name = "Rltd")
    protected BusinessApplicationHeader1 rltd;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public void setFr(Party9Choice fr) {
        this.fr = fr;
    }

    public void setTo(Party9Choice to) {
        this.to = to;
    }

    public void setBizMsgIdr(String bizMsgIdr) {
        this.bizMsgIdr = bizMsgIdr;
    }

    public void setMsgDefIdr(String msgDefIdr) {
        this.msgDefIdr = msgDefIdr;
    }

    public void setBizSvc(String bizSvc) {
        this.bizSvc = bizSvc;
    }

    public void setCreDt(Calendar creDt) {
        this.creDt = creDt;
    }

    public void setCpyDplct(CopyDuplicate1Code cpyDplct) {
        this.cpyDplct = cpyDplct;
    }

    public void setPssblDplct(Boolean pssblDplct) {
        this.pssblDplct = pssblDplct;
    }

    public void setPrty(String prty) {
        this.prty = prty;
    }

    public void setSgntr(SignatureEnvelope sgntr) {
        this.sgntr = sgntr;
    }

    public void setRltd(BusinessApplicationHeader1 rltd) {
        this.rltd = rltd;
    }
}
