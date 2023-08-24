package com.sap.ordermanagergreen.service;
//import com.sap.ordermanagergreen.dto.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sap.ordermanagergreen.dto.OrderDTO;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.model.OrderItem;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Product;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import com.sap.ordermanagergreen.repository.IProductRepository;
import com.sap.ordermanagergreen.util.rabbitMQ.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrderChargingBL {



        @Autowired
        private IProductRepository productRepository;

        @Autowired
        private IOrderRepository orderRepository;
        @Autowired
        private Producer producer;


        public void chargingStep(Order order) throws JsonProcessingException {
                if (order.getOrderStatus() == OrderStatus.APPROVED) {
                        order.setOrderStatus(OrderStatus.CHARGING);
                        for (OrderItem item : order.getOrderItemsList()) {
                                Product p = productRepository.findById(item.getProduct().getId()).get();
                                if (p.getInventory() < item.getQuantity()) {
                                        order.setOrderStatus(OrderStatus.PAYMENT_CANCELED);
                                        return;
                                } else {
                                        p.setInventory((int) (p.getInventory() - item.getQuantity()));
                                        productRepository.save(p);
                                }
                        }

                        producer.sendMessageWaitingForCharge(order);
        }}
        public void postChargingStep(OrderDTO orderDTO){
            Order order =orderRepository.findById(orderDTO.getId()).orElse(null);
            if(order.getOrderStatus()==OrderStatus.APPROVED)
            {
                order.setOrderStatus(OrderStatus.PACKING);
            }
            else{
                order.setOrderStatus(OrderStatus.PAYMENT_CANCELED);
                for (OrderItem item : order.getOrderItemsList()) {
                    Product p = productRepository.findById(item.getProduct().getId()).get();
                    p.setInventory((int) (p.getInventory() + item.getQuantity()));
                    productRepository.save(p);
                }
            }
                orderRepository.save(order);
        }
}





