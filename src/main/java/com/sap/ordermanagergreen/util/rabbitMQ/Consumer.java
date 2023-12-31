package com.sap.ordermanagergreen.util.rabbitMQ;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ordermanagergreen.dto.OrderDTO;
import com.sap.ordermanagergreen.service.OrderChargingBL;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.sap.ordermanagergreen.util.rabbitMQ.RabbitMQConfig.*;
import static com.sap.ordermanagergreen.util.rabbitMQ.MessagingLoggingUtil.logReceivedMessage;
@Service
public class Consumer {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private OrderChargingBL orderChargingBL=new OrderChargingBL();
    @RabbitListener(queues = {QUEUE_AFTER_CHARG})
    public void listenOnAfterChargQueue(String order) throws JsonProcessingException {
        OrderDTO orderDTO = objectMapper.readValue(order, OrderDTO.class);
        orderChargingBL.postChargingStep(orderDTO);
        logReceivedMessage(QUEUE_AFTER_CHARG, orderDTO);
        MessagingLoggingUtil.logReceivedMessage(QUEUE_AFTER_CHARG,orderDTO);
    }
}
