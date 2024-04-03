package ke.co.pesalink.papss.incoming.credittransferproducerservice.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.configs.AppConfig;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.acmt_023_001.IdentificationVerificationRequestV02;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_008_001.FIToFICustomerCreditTransferV07;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.pacs_028_001.FIToFIPaymentStatusRequestV02;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dictionary.payload_wrapper.Document;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.dto.AdapterTransactionRequest;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.ProcessingFailedException;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.SignatureValidationException;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.exceptions.UnmarshallException;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.Constants;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.HttpClient;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.SharedMethods;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import montran.message.Message;
import montran.rcon.Recon;
import org.apache.hc.core5.http.HttpException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyStoreException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class MessagePollerService implements MessagePoller{
    private final AppConfig appConfig;

    private final HttpClient httpClient;

    private final MessageRoutingService messageRoutingService;

    private final XmlUtils xmlUtils;

    public MessagePollerService(AppConfig appConfig, HttpClient httpClient, MessageRoutingService messageRoutingService) {
        this.appConfig = appConfig;
        this.httpClient = httpClient;
        this.messageRoutingService = messageRoutingService;
        this.xmlUtils = new XmlUtils(appConfig);
    }


    @Override
    public void run() {
        ResponseEntity<String> response;
        try {
            // I use an empty map since no additional  headers are required
           response = httpClient.makeHttpCall(appConfig.getIpsMessagePath(), HttpMethod.GET, null, new HashMap<>());
        }catch(HttpException exception) {
            log.error(exception.getMessage());
            return;
        }
        process(response);
    }

    void process(ResponseEntity<String> response) {
        String message = response.getBody();

        HttpHeaders responseHeaders = response.getHeaders();

        String messageSequenceNumber = null;
        String messageType =  Constants.pacs008;
        String remainingMessages = null;

        boolean possibleDuplicate;

        Optional<String> pollingRequestStatus = Optional.ofNullable(responseHeaders.getFirst("X-PAPSSRTP-ReqSts"));

        if (pollingRequestStatus.isPresent() && pollingRequestStatus.get().equals("EMPTY")) {
            log.info("No message found..polling again in {} seconds", appConfig.getPollerInterval().getSeconds());
            return;
        }

        if (responseHeaders.containsKey("X-PAPSSRTP-MessageSeq")) {
            messageSequenceNumber = responseHeaders.getFirst("X-PAPSSRTP-MessageSeq");
            log.info("Message sequence number: {}", messageSequenceNumber);
        }

        if (responseHeaders.containsKey("X-PAPSSRTP-MessageType")) {
            messageType = responseHeaders.getFirst("X-PAPSSRTP-MessageType");
            log.info("Message type: {}", messageType);
        }

        if (responseHeaders.containsKey(("X-PAPSSRTP-PossibleDuplicate"))) {
            possibleDuplicate = Boolean.parseBoolean(responseHeaders.getFirst("X-PAPSSRTP-PossibleDuplicate"));
            log.warn("Possible duplicate: {}", possibleDuplicate);
        }

        if (responseHeaders.containsKey(("X-PAPSSRTP-RemainingOutputs"))) {
            remainingMessages = responseHeaders.getFirst("X-PAPSSRTP-RemainingOutputs");

            log.info("{} messages remaining to be processed", remainingMessages);
        }


        if (messageType != null) {
            if (messageType.equals(Constants.pacs008)) {
                // send pac002 ack
                replyToPayment(message);
            }else {
                // confirm message
                confirmMessage(messageSequenceNumber);
            }
        }

//        String message = """
//                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
//                <hdr:Message xmlns:hdr="urn:montran:message.01">
//                	<hdr:AppHdr xmlns:hdr="urn:iso:std:iso:20022:tech:xsd:head.001.001.01">
//                		<Fr>
//                			<FIId>
//                				<FinInstnId>
//                					<ClrSysMmbId>
//                						<ClrSysId>
//                							<Prtry>PAPSS</Prtry>
//                						</ClrSysId>
//                						<MmbId>XA0001</MmbId>
//                					</ClrSysMmbId>
//                				</FinInstnId>
//                			</FIId>
//                		</Fr>
//                		<To>
//                			<FIId>
//                				<FinInstnId>
//                					<BICFI>TESTTPD5</BICFI>
//                					<ClrSysMmbId>
//                						<ClrSysId>
//                							<Prtry>PAPSS</Prtry>
//                						</ClrSysId>
//                						<MmbId>KE9000</MmbId>
//                					</ClrSysMmbId>
//                				</FinInstnId>
//                			</FIId>
//                		</To>
//                		<BizMsgIdr>CT0202403190000022201</BizMsgIdr>
//                		<MsgDefIdr>pacs.008.001.07</MsgDefIdr>
//                		<CreDt>2024-03-19T09:37:32Z</CreDt>
//                		<Sgntr>
//                			<Signature
//                				xmlns="http://www.w3.org/2000/09/xmldsig#">
//                				<SignedInfo>
//                					<CanonicalizationMethod Algorithm="http://www.w3.org/TR/2001/REC-xml-c14n-20010315"/>
//                					<SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
//                					<Reference URI="">
//                						<Transforms>
//                							<Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
//                							<Transform Algorithm="http://www.w3.org/2006/12/xml-c14n11"/>
//                						</Transforms>
//                						<DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
//                						<DigestValue>a0CpF79MQ7uevX6KE1BKHxmk/bf/RtPclHESWUwZEMo=</DigestValue>
//                					</Reference>
//                				</SignedInfo>
//                				<SignatureValue>iiYOvAfz2nWsYz1M0tHA3qZkIk1SYZT+vrbSQKQOtg1sErLlfbwcDwWAbAVtNjdTicco38dpvhgz&#13;
//                Fqr9wC6tJmw5pVpLEy9tozsvqmT0ggyywk+jAtCIAVECEahCUtyqu7K2C1HUdSHoI6xs8qiFcZQB&#13;
//                BFPHICarZmGlwBrN/FLNIAgFk5KRwK6U5Rzxnrfus6kF9jHxRRvjcPJmTCSGGGftubAr8eRbvkgs&#13;
//                gmpjl5Ni53pr6tPrlgJin1lMV/QIsdh+72dPp8VbmeYFj45nLB+i+f1RLoSvh37mD/mMjTTnDXX9&#13;
//                XNeaOfGH7mi1iNGuxEU7UpEI1MTnJyOpsO3Skw==</SignatureValue>
//                				<KeyInfo>
//                					<X509Data>
//                						<X509SubjectName>CN=server-c1</X509SubjectName>
//                						<X509IssuerSerial>
//                							<X509IssuerName>CN=AFXM CA</X509IssuerName>
//                							<X509SerialNumber>60805792545685327299985507214109568141</X509SerialNumber>
//                						</X509IssuerSerial>
//                					</X509Data>
//                				</KeyInfo>
//                			</Signature>
//                		</Sgntr>
//                	</hdr:AppHdr>
//                	<hdr:FIToFICstmrCdtTrf
//                		xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.07">
//                		<GrpHdr>
//                			<MsgId>CT0202403190000022201</MsgId>
//                			<CreDtTm>2024-03-19T09:37:32Z</CreDtTm>
//                			<NbOfTxs>1</NbOfTxs>
//                			<TtlIntrBkSttlmAmt Ccy="KES">457.60</TtlIntrBkSttlmAmt>
//                			<IntrBkSttlmDt>2024-03-19</IntrBkSttlmDt>
//                			<SttlmInf>
//                				<SttlmMtd>CLRG</SttlmMtd>
//                				<ClrSys>
//                					<Prtry>PAPSS</Prtry>
//                				</ClrSys>
//                			</SttlmInf>
//                			<PmtTpInf>
//                				<SvcLvl>
//                					<Cd>INST</Cd>
//                				</SvcLvl>
//                				<LclInstrm>
//                					<Cd>ET</Cd>
//                				</LclInstrm>
//                				<CtgyPurp>
//                					<Cd>CASH</Cd>
//                				</CtgyPurp>
//                			</PmtTpInf>
//                			<InstdAgt>
//                				<FinInstnId>
//                					<BICFI>ICBZSLFR</BICFI>
//                					<ClrSysMmbId>
//                						<ClrSysId>
//                							<Prtry>PAPSS</Prtry>
//                						</ClrSysId>
//                						<MmbId>KE9000</MmbId>
//                					</ClrSysMmbId>
//                				</FinInstnId>
//                			</InstdAgt>
//                		</GrpHdr>
//                		<CdtTrfTxInf>
//                			<PmtId>
//                				<InstrId>20240319SL1012000000000000024240578</InstrId>
//                				<EndToEndId>131321321</EndToEndId>
//                				<TxId>20240319SL1012000000000000024240578</TxId>
//                			</PmtId>
//                			<IntrBkSttlmAmt Ccy="KES">457.60</IntrBkSttlmAmt>
//                			<AccptncDtTm>2024-03-19T09:37:32Z</AccptncDtTm>
//                			<InstdAmt Ccy="SLE">800.00</InstdAmt>
//                			<ChrgBr>SLEV</ChrgBr>
//                			<Dbtr>
//                				<Nm>MUSA KUTUBU JAJUA</Nm>
//                				<PstlAdr>
//                					<Ctry>SL</Ctry>
//                					<AdrLine>SL</AdrLine>
//                				</PstlAdr>
//                			</Dbtr>
//                			<DbtrAcct>
//                				<Id>
//                					<Othr>
//                						<Id>401301000055634</Id>
//                						<SchmeNm>
//                							<Cd>BBAN</Cd>
//                						</SchmeNm>
//                					</Othr>
//                				</Id>
//                			</DbtrAcct>
//                			<DbtrAgt>
//                				<FinInstnId>
//                					<BICFI>ICBZSLFR</BICFI>
//                					<ClrSysMmbId>
//                						<ClrSysId>
//                							<Prtry>PAPSS</Prtry>
//                						</ClrSysId>
//                						<MmbId>SL1012</MmbId>
//                					</ClrSysMmbId>
//                				</FinInstnId>
//                			</DbtrAgt>
//                			<CdtrAgt>
//                				<FinInstnId>
//                					<BICFI>TESTTPD5</BICFI>
//                					<ClrSysMmbId>
//                						<ClrSysId>
//                							<Prtry>PAPSS</Prtry>
//                						</ClrSysId>
//                						<MmbId>KE9000</MmbId>
//                					</ClrSysMmbId>
//                				</FinInstnId>
//                			</CdtrAgt>
//                			<Cdtr>
//                				<Nm>LOLOOOLOLOOL</Nm>
//                				<PstlAdr>
//                					<Ctry>KE</Ctry>
//                					<AdrLine>KE</AdrLine>
//                				</PstlAdr>
//                			</Cdtr>
//                			<CdtrAcct>
//                				<Id>
//                					<Othr>
//                						<Id>12316546451563415</Id>
//                						<SchmeNm>
//                							<Cd>BBAN</Cd>
//                						</SchmeNm>
//                					</Othr>
//                				</Id>
//                			</CdtrAcct>
//                			<Purp>
//                				<Cd>ADVA</Cd>
//                			</Purp>
//                			<RmtInf>
//                				<Ustrd>TEST</Ustrd>
//                			</RmtInf>
//                		</CdtTrfTxInf>
//                		<SplmtryData>
//                			<Envlp>
//                				<AuditInfo>SECRET</AuditInfo>
//                			</Envlp>
//                		</SplmtryData>
//                	</hdr:FIToFICstmrCdtTrf>
//                </hdr:Message>""";


        boolean signatureValid = validateSignature(message);

        log.info("Signature valid {}", signatureValid);


        if (messageType.equals(Constants.recon001) ) {
            Recon recon  = (Recon) xmlUtils.unmarshall(message, Recon.class);

            log.info("Recon {}", recon);

            //@Todo what do we need to do with a recon
            confirmMessage(messageSequenceNumber);
            return;
        }

        if (messageType.equals(Constants.pacs002)) {

            confirmMessage(messageSequenceNumber);
        }

        routeMessageToQueue(messageType, message);
    }

    private boolean validateSignature(String xml){
        SharedMethods sharedMethods = new SharedMethods(appConfig);
        try {
            return sharedMethods.validateSignature(xml);
        }catch(SignatureValidationException e) {
            log.error("Signature validation failed ", e);
            return false;
        }
    }

    /**
     * @apiNote route messages to appropriate queue based on the message type
     * @param messageType the type of message
     */
    private void routeMessageToQueue(String messageType, String messageBody) {
        Objects.requireNonNull(messageType, "message type cannot be null");

       String payload = "";
       Message message = readRequestBody(messageBody);

       try{
           Document document = new Document();
           switch(messageType) {
               case Constants.pacs008 ->  {
                   // this is a credit transfer request
                   FIToFICustomerCreditTransferV07 creditTransfer = message.getFIToFICstmrCdtTrf();
                   document.setFIToFICstmrCdtTrf(creditTransfer);
               }
               case Constants.acmt023 ->  {
                   // account validation
                   IdentificationVerificationRequestV02 verificationRequest = message.getIdVrfctnReq();
                   document.setIdVrfctnReq(verificationRequest);
               }
               case Constants.pac028 -> {
                   // tsq
                   FIToFIPaymentStatusRequestV02 paymentStatusRequest = message.getFIToFIPmtStsReq();
                   document.setFIToFIPmtStsReq(paymentStatusRequest);
               }
               default -> {
                    log.info("Received message at {}", message.getAppHdr().getCreDt());
                    return;
               }
           }

           payload = xmlUtils.marshall(document);

       }catch(Exception e) {
           log.error("Error encountered while marshalling messages ", e);
       }
       AdapterTransactionRequest adapterTransactionRequest = new AdapterTransactionRequest(payload, appConfig.getCreditTransferType(), LocalDateTime.now());
       messageRoutingService.enQueue(messageType, adapterTransactionRequest);
    }

    Message readRequestBody(String body) {
        try {
            return (Message) xmlUtils.unmarshall(body, Message.class);
        }catch(UnmarshallException e) {
            throw new ProcessingFailedException("Failed to marshall request body", e);
        }
    }


    private void confirmMessage(String sequenceNumber) {
        log.info("Sending confirmation request to papss for message sequence {}", sequenceNumber);

        Map<String, String> additionalHeaders = Map.of("X-PAPSSRTP-MessageSeq", sequenceNumber);

        try {
            httpClient.makeHttpCall(appConfig.getIpsAcknowldgementPath(), HttpMethod.POST, null, additionalHeaders);
        }catch(HttpException e) {
            log.error("Exception encountered",e);
        }
        log.info("Message successfully confirmed...");
    }

    public void replyToPayment(String body) {
        Message message = (Message) xmlUtils.unmarshall(body, Message.class);

        Map<String, String> template = buildPacs002Template(message);
    }

    private Map<String, String> buildPacs002Template(Message message){
        Map<String, String> templateData = new HashMap<>();

//        templateData.put("fr_bicfi", message.getFIToFICstmrCdtTrf().getGrpHdr().)

        return templateData;
    }
}


