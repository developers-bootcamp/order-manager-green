package com.sap.ordermanagergreen.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sap.ordermanagergreen.mapper.OrderMapper;
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
        OrderDTO orderDTO = OrderMapper.INSTANCE.toDTO(order);
        String jsonOrder = objectMapper.writeValueAsString(orderDTO);
        rabbitTemplate.convertAndSend(QUEUE_WAITING_FOR_CHARG, jsonOrder);
        MessagingLoggingUtil.logSendMessage("", QUEUE_WAITING_FOR_CHARG, orderDTO);
    }


}