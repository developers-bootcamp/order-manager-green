package com.sap.ordermanagergreen.util;

import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.model.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import static com.sap.ordermanagergreen.util.RabbitMQConfig.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultExchangeProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessageWaitingForCharge(Order order) {
        OrderDTO msg = new OrderDTO();
        msg.setCustomer(order.getCustomer()!=null?order.getCustomer().getFullName() :"empty");
        msg.setCustomer("costomer!");
        System.out.println("🤷‍♂️🤷‍♂️:"+msg);
        rabbitTemplate.convertAndSend(QUEUE_WAITING_FOR_CHARG, msg);
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