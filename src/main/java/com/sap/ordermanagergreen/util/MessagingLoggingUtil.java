package com.sap.ordermanagergreen.util;

import com.sap.ordermanagergreen.model.OrderDTO;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class MessagingLoggingUtil {
    public static void logReceivedMessage(String queue, OrderDTO message) {
        log.info("Listener on queue [{}] received message [{}]", queue, message);
    }

    public static void logSendMessage(String exchange, String routingKey, OrderDTO message) {
        log.info("Message [{}] send to exchange [{}] with routing key [{}]", message.getId(), exchange, routingKey);
    }

//    public static void logSendMessage(String exchange, String routingKey, OrderForQueue message, Map<String, Object> headers) {
//        log.info("Message [{}] send to exchange [{}] with routing key [{}] and headers [{}]", message.getId(), exchange, routingKey, headers);
//    }
}