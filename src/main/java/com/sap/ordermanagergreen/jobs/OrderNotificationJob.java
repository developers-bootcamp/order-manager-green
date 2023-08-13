package com.sap.ordermanagergreen.jobs;

import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class OrderNotificationJob {

    private final OrderService orderService;

    public OrderNotificationJob(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(fixedRate = 1800000)
    @Transactional
    public void simulateOrderNotifications() throws ObjectNotExistException {
        List<Order> ordersToNotify = orderService.getOrdersWithNotificationFlag();

        for (Order order : ordersToNotify) {
            simulateNotification(order);
            order.setNotificationFlag(false);
            orderService.update(order.getId(),order);
        }
    }

    private void simulateNotification(Order order) {
        System.out.println("Simulated order notification sent for order ID: " + order.getId());
    }
}
