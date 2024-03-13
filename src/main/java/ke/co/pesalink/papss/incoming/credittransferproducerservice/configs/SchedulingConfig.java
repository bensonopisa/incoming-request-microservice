package ke.co.pesalink.papss.incoming.credittransferproducerservice.configs;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import ke.co.pesalink.papss.incoming.credittransferproducerservice.utils.XmlUtils;
import montran.message.Message;
import montran.rcon.Recon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.net.URI;
import java.time.Duration;


@EnableScheduling
@Configuration
public class SchedulingConfig implements SchedulingConfigurer, Runnable {
    protected final RestTemplate restTemplate;
    protected final AppConfig appConfig;

    final XmlUtils xmlUtils;
    private final Logger logger = LoggerFactory.getLogger(SchedulingConfig.class);

    public SchedulingConfig(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;

        xmlUtils = new XmlUtils(appConfig);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addFixedRateTask(this, Duration.ofSeconds(5));
    }

    @Override
    public void run() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set(HttpHeaders.CONTENT_TYPE , MediaType.APPLICATION_XML.toString());
        httpHeaders.add("X-PAPSSRTP-Channel", "LR2020");
        httpHeaders.add("X-PAPSS-RTP-Version", "1");

        URI uri = UriComponentsBuilder.fromHttpUrl(appConfig.getPapssIpsDns())
                .port(appConfig.getPapssIpsPort())
                .path(appConfig.getIpsMessagePath())
                .build().toUri();

        RequestEntity<?> request = RequestEntity.get(uri).headers(httpHeaders).build();

        logger.info("About to send request to uri {}", uri);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Poller service  is successful");

            HttpHeaders responseHeaders = response.getHeaders();

            String messageStatus = responseHeaders.getFirst("X-PAPSSRTP-ReqSts");
//
//            if (messageStatus == null) {
//                logger.info("No message available for processing");
//                return;
//            }

            String messageSequenceId = responseHeaders.getFirst("X-PAPSSRTP-MessageSeq");

            logger.info("Message sequence id {}", messageSequenceId);

            String messageType = responseHeaders.getFirst("X-PAPSSRTP-MessageType");

            logger.info("Message Type {}", messageType);

            String possibleDuplicate = responseHeaders.getFirst("X-PAPSSRTP-PossibleDuplicate");

            if (possibleDuplicate != null) {
                logger.info("This is potentially a duplicate message");
            }

            String remainingMessages = responseHeaders.getFirst("X-PAPSSRTP-RemainingOutputs");

            if (remainingMessages != null) {
                logger.info("{} messages awaiting to be processed", remainingMessages);
            }

            assert response.getBody() != null;

//            boolean isValid = false;

//            try{
//                 = xmlUtils.parseXml(response.getBody());
//            }catch(Exception ex) {
//                logger.error("Exception occured during sinature validation ", ex);
//            }
//
//            if (!isValid) {
//                logger.error("Signature validation failed");
//                // generate a pac.002 message and send back to papss rejected status
//            }

            String xml = """
                    <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                    <hdr:Message xmlns:hdr="urn:montran:message.01">
                        <hdr:AppHdr xmlns:hdr="urn:iso:std:iso:20022:tech:xsd:head.001.001.01">
                            <Fr>
                                <FIId>
                                    <FinInstnId>
                                        <ClrSysMmbId>
                                            <ClrSysId>
                                                <Prtry>PAPSS</Prtry>
                                            </ClrSysId>
                                            <MmbId>KE1002</MmbId>
                                        </ClrSysMmbId>
                                    </FinInstnId>
                                </FIId>
                            </Fr>
                            <To>
                                <FIId>
                                    <FinInstnId>
                                        <ClrSysMmbId>
                                            <ClrSysId>
                                                <Prtry>PAPSS</Prtry>
                                            </ClrSysId>
                                            <MmbId>XA0001</MmbId>
                                        </ClrSysMmbId>
                                    </FinInstnId>
                                </FIId>
                            </To>
                            <BizMsgIdr>20240123KE1002120503</BizMsgIdr>
                            <MsgDefIdr>pacs.008.001.07</MsgDefIdr>
                            <BizSvc>RTP</BizSvc>
                            <CreDt>2024-01-23T10:05:03.635Z</CreDt>
                            <Sgntr>
                                <Signature xmlns="http://www.w3.org/2000/09/xmldsig#">
                                    <SignedInfo>
                                        <CanonicalizationMethod Algorithm="http://www.w3.org/TR/2001/REC-xml-c14n-20010315"/>
                                        <SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
                                        <Reference URI="">
                                            <Transforms>
                                                <Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
                                                <Transform Algorithm="http://www.w3.org/2006/12/xml-c14n11"/>
                                            </Transforms>
                                            <DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
                                            <DigestValue>fVqxWejFalRusbvs9inaoyMXAfmwJzpyFa5Ebt92aA0=</DigestValue>
                                        </Reference>
                                    </SignedInfo>
                                    <SignatureValue>vJ5JCxAVU7RGPPazGNSut56p/QOnDVAEyU1jZYJzY8+vrE10wKO8AhA7v+5gSiRH1tRfhKvP4J/e&#13;
                    Cwt/z1suXA2i1pWu9/GK2GSs5AQtqEfWFxeAzS7G5GIwBgrWaZzc4I8GIHZlH2l2Avsrj+A5DV3i&#13;
                    q9f1fLuf77TlItSCB4DLz69FUPOtEEHe62HVOHkgOYpnmjP91XnmPbKzdkqLK2rm5DnmXrKcAWH8&#13;
                    2CPTjMd9w7G5yUgk4mHmjOn/nHVtCNTw4S0XRYmTxGR4SV3rn5cxu6587NotKGtAPIZuwO5HD1TH&#13;
                    hSPJxWeB4e6t4UaQXaDTzpLOxIZ1unYS9/kbZw==
                                    </SignatureValue>
                                    <KeyInfo>
                                        <X509Data>
                                            <X509SubjectName>CN=tezk-c1</X509SubjectName>
                                            <X509IssuerSerial>
                                                <X509IssuerName>CN=AFXM CA</X509IssuerName>
                                                <X509SerialNumber>222063791874351954580927885950191632387</X509SerialNumber>
                                            </X509IssuerSerial>
                                        </X509Data>
                                    </KeyInfo>
                                </Signature>
                            </Sgntr>
                        </hdr:AppHdr>
                        <hdr:FIToFICstmrCdtTrf xmlns="urn:iso:std:iso:20022:tech:xsd:pacs.008.001.07">
                            <GrpHdr>
                                <MsgId>20240123KE1002120503</MsgId>
                                <CreDtTm>2024-01-23T10:05:03.635Z</CreDtTm>
                                <NbOfTxs>1</NbOfTxs>
                                <TtlIntrBkSttlmAmt Ccy="GNF">100000</TtlIntrBkSttlmAmt>
                                <IntrBkSttlmDt>2024-01-23</IntrBkSttlmDt>
                                <SttlmInf>
                                    <SttlmMtd>CLRG</SttlmMtd>
                                    <ClrSys>
                                        <Prtry>PAPSS</Prtry>
                                    </ClrSys>
                                </SttlmInf>
                                <PmtTpInf>
                                    <SvcLvl>
                                        <Prtry>INST</Prtry>
                                    </SvcLvl>
                                    <LclInstrm>
                                        <Cd>ET</Cd>
                                    </LclInstrm>
                                    <CtgyPurp>
                                        <Cd>SUPP</Cd>
                                    </CtgyPurp>
                                </PmtTpInf>
                                <InstgAgt>
                                    <FinInstnId>
                                        <BICFI>UBAGGNCN</BICFI>
                                        <ClrSysMmbId>
                                            <ClrSysId>
                                                <Prtry>PAPSS</Prtry>
                                            </ClrSysId>
                                            <MmbId>KE1002</MmbId>
                                        </ClrSysMmbId>
                                    </FinInstnId>
                                </InstgAgt>
                            </GrpHdr>
                            <CdtTrfTxInf>
                                <PmtId>
                                    <InstrId>20240123KE1002120503</InstrId>
                                    <EndToEndId>ENDTOENDREF123</EndToEndId>
                                    <TxId>20240123KE1002120503</TxId>
                                </PmtId>
                                <IntrBkSttlmAmt Ccy="GNF">100000</IntrBkSttlmAmt>
                                <AccptncDtTm>2024-01-23T10:05:03.635Z</AccptncDtTm>
                                <InstdAmt Ccy="NGN"/>
                                <XchgRate>1</XchgRate>
                                <ChrgBr>SLEV</ChrgBr>
                                <UltmtDbtr>
                                    <Nm>John Doe</Nm>
                                </UltmtDbtr>
                                <Dbtr>
                                    <Nm>John Doe</Nm>
                                    <PstlAdr>
                                        <Ctry>GN</Ctry>
                                        <AdrLine>user@papss.com</AdrLine>
                                    </PstlAdr>
                                </Dbtr>
                                <DbtrAcct>
                                    <Id>
                                        <Othr>
                                            <Id>1000000000</Id>
                                            <SchmeNm>
                                                <Cd>BBAN</Cd>
                                            </SchmeNm>
                                        </Othr>
                                    </Id>
                                </DbtrAcct>
                                <DbtrAgt>
                                    <FinInstnId>
                                        <BICFI>UBAGGNCN</BICFI>
                                        <ClrSysMmbId>
                                            <MmbId>KE1002</MmbId>
                                        </ClrSysMmbId>
                                    </FinInstnId>
                                </DbtrAgt>
                                <CdtrAgt>
                                    <FinInstnId>
                                        <BICFI>AFXMNGNG</BICFI>
                                        <ClrSysMmbId>
                                            <ClrSysId>
                                                <Prtry>PAPSS</Prtry>
                                            </ClrSysId>
                                            <MmbId>NG2020</MmbId>
                                        </ClrSysMmbId>
                                    </FinInstnId>
                                </CdtrAgt>
                                <Cdtr>
                                    <Nm>Mr Ultimate Papss User</Nm>
                                    <PstlAdr>
                                        <Ctry>NG</Ctry>
                                        <AdrLine>123 Street Number NG</AdrLine>
                                    </PstlAdr>
                                </Cdtr>
                                <CdtrAcct>
                                    <Id>
                                        <Othr>
                                            <Id>1000000000</Id>
                                            <SchmeNm>
                                                <Cd>BBAN</Cd>
                                            </SchmeNm>
                                        </Othr>
                                    </Id>
                                </CdtrAcct>
                                <UltmtCdtr>
                                    <Nm>Mr Ultimate Papss User</Nm>
                                    <CtryOfRes>NG</CtryOfRes>
                                    <CtctDtls>
                                        <EmailAdr>
                                            user@papss.com
                                        </EmailAdr>
                                        <Othr>002630721234567</Othr>
                                    </CtctDtls>
                                </UltmtCdtr>
                                <InstrForCdtrAgt>
                                    <Cd>PHOB</Cd>
                                    <InstrInf>HBSLAU4T/1000000000/LT499228</InstrInf>
                                </InstrForCdtrAgt>
                                <RmtInf>
                                    <Ustrd>Trade Transfer Invoice for purchase of Cassava</Ustrd>
                                </RmtInf>
                            </CdtTrfTxInf>
                            <SplmtryData>
                                <Envlp>
                                    <AuditInfo>
                                        <IP>10.77.7.13</IP>
                                        <PC>vi-papss</PC>
                                    </AuditInfo>
                                </Envlp>
                            </SplmtryData>
                        </hdr:FIToFICstmrCdtTrf>
                    </hdr:Message>
                    """;
            JAXBContext jaxbContext;
            try {
                jaxbContext = JAXBContext.newInstance(Message.class);
                Unmarshaller unmarshaller  = jaxbContext.createUnmarshaller();

                Message message = (Message) unmarshaller.unmarshal(new InputSource(new StringReader(xml)));
                String agentInstId = message.getAppHdr().getFr().getFIId().getFinInstnId().getClrSysMmbId()
                        .getClrSysId().getPrtry().trim();
                logger.info("Agent institution id {}", agentInstId);

            }catch(JAXBException jEx){
                logger.error("Exception ", jEx);
            }
        }
    }
}
