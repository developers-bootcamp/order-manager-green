package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.OrderDTO;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.model.OrderItem;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Product;
import com.sap.ordermanagergreen.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderChargingService {
    @Autowired
    private IProductRepository productRepository;

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
            ///queue


        }
    }
}
