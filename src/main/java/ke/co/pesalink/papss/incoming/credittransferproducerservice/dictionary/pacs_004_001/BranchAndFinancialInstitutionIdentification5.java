//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_004_001;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.io.Serializable;


/**
 * <p>Java class for BranchAndFinancialInstitutionIdentification5 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BranchAndFinancialInstitutionIdentification5"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FinInstnId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.07}FinancialInstitutionIdentification8"/&gt;
 *         &lt;element name="BrnchId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.07}BranchData2" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BranchAndFinancialInstitutionIdentification5", propOrder = {
    "finInstnId",
    "brnchId"
})
public class BranchAndFinancialInstitutionIdentification5
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "FinInstnId", required = true)
    protected FinancialInstitutionIdentification8 finInstnId;
    @XmlElement(name = "BrnchId")
    protected BranchData2 brnchId;

    /**
     * Gets the value of the finInstnId property.
     * 
     * @return
     *     possible object is
     *     {@link FinancialInstitutionIdentification8 }
     *     
     */
    public FinancialInstitutionIdentification8 getFinInstnId() {
        return finInstnId;
    }

    /**
     * Sets the value of the finInstnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinancialInstitutionIdentification8 }
     *     
     */
    public void setFinInstnId(FinancialInstitutionIdentification8 value) {
        this.finInstnId = value;
    }

    /**
     * Gets the value of the brnchId property.
     * 
     * @return
     *     possible object is
     *     {@link BranchData2 }
     *     
     */
    public BranchData2 getBrnchId() {
        return brnchId;
    }

    /**
     * Sets the value of the brnchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchData2 }
     *     
     */
    public void setBrnchId(BranchData2 value) {
        this.brnchId = value;
    }

}
