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
 * <p>Java class for Party11Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Party11Choice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="OrgId" type="{urn:iso:std:iso:20022:tech:xsd:acmt.023.001.02}OrganisationIdentification8"/&gt;
 *         &lt;element name="PrvtId" type="{urn:iso:std:iso:20022:tech:xsd:acmt.023.001.02}PersonIdentification5"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party11Choice", propOrder = {
    "prvtId",
    "orgId"
})
public class Party11Choice
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "PrvtId")
    protected PersonIdentification5 prvtId;
    @XmlElement(name = "OrgId")
    protected OrganisationIdentification8 orgId;

    /**
     * Gets the value of the prvtId property.
     * 
     * @return
     *     possible object is
     *     {@link PersonIdentification5 }
     *     
     */
    public PersonIdentification5 getPrvtId() {
        return prvtId;
    }

    /**
     * Sets the value of the prvtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonIdentification5 }
     *     
     */
    public void setPrvtId(PersonIdentification5 value) {
        this.prvtId = value;
    }

    /**
     * Gets the value of the orgId property.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationIdentification8 }
     *     
     */
    public OrganisationIdentification8 getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationIdentification8 }
     *     
     */
    public void setOrgId(OrganisationIdentification8 value) {
        this.orgId = value;
    }

}
