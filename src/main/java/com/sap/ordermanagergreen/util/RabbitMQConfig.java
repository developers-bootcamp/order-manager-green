package com.sap.ordermanagergreen.util;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    public static final String QUEUE_WAITING_FOR_CHARG = "waitingForPaimentQueue";
    public static final String QUEUE_AFTER_CHARG = "afterPaymentQueue";


    @Bean
    public Queue waitingForPaimentQueue() {
        return new Queue(QUEUE_WAITING_FOR_CHARG);
    }
    @Bean
    public Queue afterPaymentQueue() {
        return new Queue(QUEUE_AFTER_CHARG);
    }
}





