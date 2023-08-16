package com.sap.ordermanagergreen.util;
import com.sap.ordermanagergreen.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;



import static com.sap.ordermanagergreen.util.RabbitMQConfig.*;
import static com.sap.ordermanagergreen.util.MessagingLoggingUtil.logReceivedMessage;
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitListener.class);

    @RabbitListener(queues = {QUEUE_AFTER_CHARG})
    public void listenOnAfterChargQueue(OrderDTO order) {
        logReceivedMessage(QUEUE_AFTER_CHARG, order);
       // LOGGER.info("Received message -> %s ",order);
    }
}
