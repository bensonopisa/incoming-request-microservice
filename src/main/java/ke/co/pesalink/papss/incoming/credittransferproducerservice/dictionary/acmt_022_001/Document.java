//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.03.01 at 01:01:35 AM EAT 
//


package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.acmt_022_001;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;


/**
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdModAdvc" type="{urn:iso:std:iso:20022:tech:xsd:acmt.022.001.02}IdentificationModificationAdviceV02"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "idModAdvc"
})
@XmlRootElement(name = "Document")
public class Document
    implements Serializable
{

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "IdModAdvc", required = true)
    protected IdentificationModificationAdviceV02 idModAdvc;

    /**
     * Gets the value of the idModAdvc property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificationModificationAdviceV02 }
     *     
     */
    public IdentificationModificationAdviceV02 getIdModAdvc() {
        return idModAdvc;
    }

    /**
     * Sets the value of the idModAdvc property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificationModificationAdviceV02 }
     *     
     */
    public void setIdModAdvc(IdentificationModificationAdviceV02 value) {
        this.idModAdvc = value;
    }

}
