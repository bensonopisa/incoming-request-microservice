package ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.payload_wrapper;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.acmt_023_001.IdentificationVerificationRequestV02;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_008_001.FIToFICustomerCreditTransferV07;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_028_001.FIToFIPaymentStatusRequestV02;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Document")
public class Document {

    @XmlElement(name = "IdVrfctnReq")
    protected IdentificationVerificationRequestV02 idVrfctnReq;

    @XmlElement(name = "FIToFICstmrCdtTrf")
    protected FIToFICustomerCreditTransferV07 fiToFICstmrCdtTrf;

    @XmlElement(name = "FIToFIPmtStsReq")
    protected FIToFIPaymentStatusRequestV02 fiToFIPmtStsReq;

    /**
     * Gets the value of the idVrfctnReq property.
     *
     * @return
     *     possible object is
     *     {@link IdentificationVerificationRequestV02 }
     *
     */
    public IdentificationVerificationRequestV02 getIdVrfctnReq() {
        return idVrfctnReq;
    }

    /**
     * Sets the value of the idVrfctnReq property.
     *
     * @param value
     *     allowed object is
     *     {@link IdentificationVerificationRequestV02 }
     *
     */
    public void setIdVrfctnReq(IdentificationVerificationRequestV02 value) {
        this.idVrfctnReq = value;
    }

    /**
     * Gets the value of the fiToFICstmrCdtTrf property.
     *
     * @return
     *     possible object is
     *     {@link FIToFICustomerCreditTransferV07 }
     *
     */
    public FIToFICustomerCreditTransferV07 getFIToFICstmrCdtTrf() {
        return fiToFICstmrCdtTrf;
    }

    /**
     * Sets the value of the fiToFICstmrCdtTrf property.
     *
     * @param value
     *     allowed object is
     *     {@link FIToFICustomerCreditTransferV07 }
     *
     */
    public void setFIToFICstmrCdtTrf(FIToFICustomerCreditTransferV07 value) {
        this.fiToFICstmrCdtTrf = value;
    }

    /**
     * Gets the value of the fiToFIPmtStsReq property.
     *
     * @return
     *     possible object is
     *     {@link FIToFIPaymentStatusRequestV02 }
     *
     */
    public FIToFIPaymentStatusRequestV02 getFIToFIPmtStsReq() {
        return fiToFIPmtStsReq;
    }

    /**
     * Sets the value of the fiToFIPmtStsReq property.
     *
     * @param value
     *     allowed object is
     *     {@link FIToFIPaymentStatusRequestV02 }
     *
     */
    public void setFIToFIPmtStsReq(FIToFIPaymentStatusRequestV02 value) {
        this.fiToFIPmtStsReq = value;
    }
}
