package com.sap.ordermanagergreen.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import static com.sap.ordermanagergreen.util.RabbitMQConfig.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultExchangeProducer {
    private final RabbitTemplate rabbitTemplate;
    ObjectMapper objectMapper = new ObjectMapper();
    public void sendMessageWaitingForCharge(Order order) throws JsonProcessingException {
        OrderDTO msg = new OrderDTO();
        msg.setCustomer(order.getCustomer()!=null?order.getCustomer().getFullName() :"empty");
        msg.setCustomer("costomer!");
        System.out.println("🤷‍♂️🤷‍♂️:"+msg);
        String jsonOrder = objectMapper.writeValueAsString(msg);
        rabbitTemplate.convertAndSend(QUEUE_WAITING_FOR_CHARG, jsonOrder);
        MessagingLoggingUtil.logSendMessage("", QUEUE_WAITING_FOR_CHARG, msg);
    }

//    public void sendMessageWaitingForCharge(Order order) {
//        OrderDTO msg = new OrderDTO();
//        msg.setCustomer(order.getCustomer()!=null?order.getCustomer().getFullName() :"empty");
//        msg.setCustomer("costomer!");
//        System.out.println("🤷‍♂️🤷‍♂️:"+msg);
//        rabbitTemplate.convertAndSend(QUEUE_WAITING_FOR_CHARG, "i am a msg!!!");
//        //MessagingLoggingUtil.logSendMessage("", QUEUE_WAITING_FOR_CHARG, msg);
//    }

}