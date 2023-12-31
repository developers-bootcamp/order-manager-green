package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.OrderDTO;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.model.OrderItem;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Product;
import com.sap.ordermanagergreen.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderChargingService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;
    public void chargingStep(Order order) {
        if (order.getOrderStatus() == OrderStatus.APPROVED) {
            order.setOrderStatus(OrderStatus.CHARGING);
            for (OrderItem item : order.getOrderItemsList()) {
                Product p = productRepository.findById(item.getProduct().getId()).get();
                if (p.getInventory() == 0) {
                    order.setOrderStatus(OrderStatus.PAYMENT_CANCELED);
                    return;
                } else {
                    p.setInventory(p.getInventory() - item.getQuantity());
                    productRepository.save(p);
                }
            }

            OrderDTO orderDTO = this.modelMapper.map(order, OrderDTO.class);
            //push orderDto to queue


        }
    }
    public void paymentListener(){

        //listener

        OrderDTO orderDto=new OrderDTO();//from queue
        Order order = this.modelMapper.map(orderDto, Order.class);

        if(order.getOrderStatus()==OrderStatus.APPROVED)
        {
            order.setOrderStatus(OrderStatus.PACKING);
        }
        else{
            order.setOrderStatus(OrderStatus.PAYMENT_CANCELED);
            for (OrderItem item : order.getOrderItemsList()) {
                Product p = productRepository.findById(item.getProduct().getId()).get();
                    p.setInventory(p.getInventory() + item.getQuantity());
                    productRepository.save(p);
                }
            }
        }

    }

