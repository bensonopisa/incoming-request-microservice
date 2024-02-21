package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;

public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);
    // prevent instantiation
    private LoggerUtil() {}
    public static void infoLog(String message) {
        logger.info("Message : {} ", message);
    }

    public static void errorLog(HttpStatusCode httpStatusCode, String message) {
        logger.error("Error encountered: {} | Message {}", httpStatusCode, message);
    }
}
