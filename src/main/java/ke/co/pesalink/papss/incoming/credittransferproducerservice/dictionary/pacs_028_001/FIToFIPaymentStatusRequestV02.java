//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_028_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for FIToFIPaymentStatusRequestV02 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FIToFIPaymentStatusRequestV02"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.02}GroupHeader53"/&gt;
 *         &lt;element name="OrgnlGrpInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.02}OriginalGroupInformation27" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="TxInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.02}PaymentTransaction94" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SplmtryData" type="{urn:iso:std:iso:20022:tech:xsd:pacs.028.001.02}SupplementaryData1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FIToFIPaymentStatusRequestV02", propOrder = {
    "grpHdr",
    "orgnlGrpInves",
    "txInves",
    "splmtryDatas"
})
public class FIToFIPaymentStatusRequestV02
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "GrpHdr", required = true)
    protected GroupHeader53 grpHdr;
    @XmlElement(name = "OrgnlGrpInf")
    protected List<OriginalGroupInformation27> orgnlGrpInves;
    @XmlElement(name = "TxInf")
    protected List<PaymentTransaction94> txInves;
    @XmlElement(name = "SplmtryData")
    protected List<SupplementaryData1> splmtryDatas;

    /**
     * Gets the value of the grpHdr property.
     * 
     * @return
     *     possible object is
     *     {@link GroupHeader53 }
     *     
     */
    public GroupHeader53 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupHeader53 }
     *     
     */
    public void setGrpHdr(GroupHeader53 value) {
        this.grpHdr = value;
    }

    /**
     * Gets the value of the orgnlGrpInves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the orgnlGrpInves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrgnlGrpInves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OriginalGroupInformation27 }
     * 
     * 
     */
    public List<OriginalGroupInformation27> getOrgnlGrpInves() {
        if (orgnlGrpInves == null) {
            orgnlGrpInves = new ArrayList<OriginalGroupInformation27>();
        }
        return this.orgnlGrpInves;
    }

    /**
     * Gets the value of the txInves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the txInves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTxInves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTransaction94 }
     * 
     * 
     */
    public List<PaymentTransaction94> getTxInves() {
        if (txInves == null) {
            txInves = new ArrayList<PaymentTransaction94>();
        }
        return this.txInves;
    }

    /**
     * Gets the value of the splmtryDatas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the splmtryDatas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSplmtryDatas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplementaryData1 }
     * 
     * 
     */
    public List<SupplementaryData1> getSplmtryDatas() {
        if (splmtryDatas == null) {
            splmtryDatas = new ArrayList<SupplementaryData1>();
        }
        return this.splmtryDatas;
    }

}
