package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;


public class LoggerUtil {

    protected static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    public void log(String message, @Nullable String className, @Nullable String method, LogLevel logLevel) {
        switch(logLevel)  {
            case INFO -> {

            }
            case ERROR -> {

            }
            case DEBUG  -> {

            }
            case TRACE -> {

            }
        }
    }
}