package com.sap.ordermanagergreen.service;
//import com.sap.ordermanagergreen.dto.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.model.OrderItem;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Product;
import com.sap.ordermanagergreen.repository.IProductRepository;
import com.sap.ordermanagergreen.util.DefaultExchangeProducer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrderChargingBL {



        @Autowired
        private IProductRepository productRepository;
        @Autowired
        private DefaultExchangeProducer producer;
        @Autowired
        private ModelMapper modelMapper;
        public void chargingStep(Order order) throws JsonProcessingException {
         //   if (order.getOrderStatus() == OrderStatus.APPROVED) {
          //      order.setOrderStatus(OrderStatus.CHARGING);
         //      for (OrderItem item : order.getOrderItemsList()) {
                    //Product p = productRepository.findById(item.getProduct().getId()).get();
//                    if (p.getInventory() == 0) {
//                        order.setOrderStatus(OrderStatus.PAYMENT_CANCELED);
//                        return;
//                    } else {
//                        p.setInventory((int) (p.getInventory() - item.getQuantity()));
//                        productRepository.save(p);
//                    }
            //    }

                producer.sendMessageWaitingForCharge(order);

                //OrderDTO orderDTO = this.modelMapper.map(order, OrderDTO.class);
                //push orderDto to queue
            }
        }
//        public void paymentListener(){
//            //listener
//            //OrderDTO orderDto=new OrderDTO();//from queue
//            Order order = this.modelMapper.map(orderDto, Order.class);
//            if(order.getOrderStatus()==OrderStatus.APPROVED)
//            {
//                order.setOrderStatus(OrderStatus.PACKING);
//            }
//            else{
//                order.setOrderStatus(OrderStatus.PAYMENT_CANCELED);
//                for (OrderItem item : order.getOrderItemsList()) {
//                    Product p = productRepository.findById(item.getProduct().getId()).get();
//                    p.setInventory(p.getInventory() + item.getQuantity());
//                    productRepository.save(p);
//                }
//            }
//        }




