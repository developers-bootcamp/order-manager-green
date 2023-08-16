package com.sap.ordermanagergreen.util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ordermanagergreen.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;



import static com.sap.ordermanagergreen.util.RabbitMQConfig.*;
import static com.sap.ordermanagergreen.util.MessagingLoggingUtil.logReceivedMessage;
public class RabbitMQConsumer {

    ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = {QUEUE_AFTER_CHARG})
    public void listenOnAfterChargQueue(String order) throws JsonProcessingException {
        OrderDTO orderDTO = objectMapper.readValue(order, OrderDTO.class);
        logReceivedMessage(QUEUE_AFTER_CHARG, orderDTO);
        MessagingLoggingUtil.logReceivedMessage(QUEUE_AFTER_CHARG,orderDTO);
    }
}
