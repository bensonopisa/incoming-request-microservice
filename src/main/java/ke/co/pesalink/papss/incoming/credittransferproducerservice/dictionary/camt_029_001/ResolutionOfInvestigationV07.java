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
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ResolutionOfInvestigationV07 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResolutionOfInvestigationV07"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Assgnmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}CaseAssignment3"/&gt;
 *         &lt;element name="RslvdCase" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}Case3" minOccurs="0"/&gt;
 *         &lt;element name="Sts" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}InvestigationStatus3Choice"/&gt;
 *         &lt;element name="CxlDtls" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}UnderlyingTransaction17" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="StmtDtls" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}StatementResolutionEntry2" minOccurs="0"/&gt;
 *         &lt;element name="CrrctnTx" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}CorrectiveTransaction2Choice" minOccurs="0"/&gt;
 *         &lt;element name="RsltnRltdInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}ResolutionInformation1" minOccurs="0"/&gt;
 *         &lt;element name="SplmtryData" type="{urn:iso:std:iso:20022:tech:xsd:camt.029.001.07}SupplementaryData1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResolutionOfInvestigationV07", propOrder = {
    "assgnmt",
    "rslvdCase",
    "sts",
    "cxlDtls",
    "stmtDtls",
    "crrctnTx",
    "rsltnRltdInf",
    "splmtryDatas"
})
public class ResolutionOfInvestigationV07
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "Assgnmt", required = true)
    protected CaseAssignment3 assgnmt;
    @XmlElement(name = "RslvdCase")
    protected Case3 rslvdCase;
    @XmlElement(name = "Sts", required = true)
    protected InvestigationStatus3Choice sts;
    @XmlElement(name = "CxlDtls")
    protected List<UnderlyingTransaction17> cxlDtls;
    @XmlElement(name = "StmtDtls")
    protected StatementResolutionEntry2 stmtDtls;
    @XmlElement(name = "CrrctnTx")
    protected CorrectiveTransaction2Choice crrctnTx;
    @XmlElement(name = "RsltnRltdInf")
    protected ResolutionInformation1 rsltnRltdInf;
    @XmlElement(name = "SplmtryData")
    protected List<SupplementaryData1> splmtryDatas;

    /**
     * Gets the value of the assgnmt property.
     * 
     * @return
     *     possible object is
     *     {@link CaseAssignment3 }
     *     
     */
    public CaseAssignment3 getAssgnmt() {
        return assgnmt;
    }

    /**
     * Sets the value of the assgnmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseAssignment3 }
     *     
     */
    public void setAssgnmt(CaseAssignment3 value) {
        this.assgnmt = value;
    }

    /**
     * Gets the value of the rslvdCase property.
     * 
     * @return
     *     possible object is
     *     {@link Case3 }
     *     
     */
    public Case3 getRslvdCase() {
        return rslvdCase;
    }

    /**
     * Sets the value of the rslvdCase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Case3 }
     *     
     */
    public void setRslvdCase(Case3 value) {
        this.rslvdCase = value;
    }

    /**
     * Gets the value of the sts property.
     * 
     * @return
     *     possible object is
     *     {@link InvestigationStatus3Choice }
     *     
     */
    public InvestigationStatus3Choice getSts() {
        return sts;
    }

    /**
     * Sets the value of the sts property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvestigationStatus3Choice }
     *     
     */
    public void setSts(InvestigationStatus3Choice value) {
        this.sts = value;
    }

    /**
     * Gets the value of the cxlDtls property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the cxlDtls property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCxlDtls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UnderlyingTransaction17 }
     * 
     * 
     */
    public List<UnderlyingTransaction17> getCxlDtls() {
        if (cxlDtls == null) {
            cxlDtls = new ArrayList<UnderlyingTransaction17>();
        }
        return this.cxlDtls;
    }

    /**
     * Gets the value of the stmtDtls property.
     * 
     * @return
     *     possible object is
     *     {@link StatementResolutionEntry2 }
     *     
     */
    public StatementResolutionEntry2 getStmtDtls() {
        return stmtDtls;
    }

    /**
     * Sets the value of the stmtDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatementResolutionEntry2 }
     *     
     */
    public void setStmtDtls(StatementResolutionEntry2 value) {
        this.stmtDtls = value;
    }

    /**
     * Gets the value of the crrctnTx property.
     * 
     * @return
     *     possible object is
     *     {@link CorrectiveTransaction2Choice }
     *     
     */
    public CorrectiveTransaction2Choice getCrrctnTx() {
        return crrctnTx;
    }

    /**
     * Sets the value of the crrctnTx property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectiveTransaction2Choice }
     *     
     */
    public void setCrrctnTx(CorrectiveTransaction2Choice value) {
        this.crrctnTx = value;
    }

    /**
     * Gets the value of the rsltnRltdInf property.
     * 
     * @return
     *     possible object is
     *     {@link ResolutionInformation1 }
     *     
     */
    public ResolutionInformation1 getRsltnRltdInf() {
        return rsltnRltdInf;
    }

    /**
     * Sets the value of the rsltnRltdInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResolutionInformation1 }
     *     
     */
    public void setRsltnRltdInf(ResolutionInformation1 value) {
        this.rsltnRltdInf = value;
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
