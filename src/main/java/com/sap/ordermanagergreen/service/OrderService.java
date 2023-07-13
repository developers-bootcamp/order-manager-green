package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import com.sap.ordermanagergreen.repository.IProductRepository;

import com.sap.ordermanagergreen.exception.ObjectNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private IOrderRepository orderRepository;
    private IProductRepository productRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IProductRepository productRepository) {
        this.orderRepository = orderRepository;

        this.productRepository = productRepository;
    }

    public List<Orders> getOrders(Integer pageNo, Integer pageSize, String companyId, int employeeId, OrderStatus orderStatus) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        return orderRepository.findByOrderStatusAndCompanyId(paging, orderStatus, companyId);

    }

    public String createOrder(Orders order) {
        Orders newOrdr = this.orderRepository.insert(order);
        return newOrdr.getId();
    }

    public void updateOrder(String id, Orders order) throws ObjectNotExist {

        if (orderRepository.findById(id).isEmpty())
            throw new ObjectNotExist();
        orderRepository.save(order);

    }

    public Map<String, HashMap<Double, Integer>> calculate(Orders order) {
        HashMap<String, HashMap<Double, Integer>> calculatedOrder = new HashMap<String, HashMap<Double, Integer>>();
        double totalAmount = 0;

        for (int i = 0; i < order.getOrderItemsList().stream().count(); i++) {
            OrderItems oi = order.getOrderItemsList().get(i);
            Product p = oi.getProductId();
            HashMap<Double, Integer> o = new HashMap<Double, Integer>();
            double amount = 0;
            if (p.getDiscountType() == DiscountTypes.FIXED_AMOUNT) {
                amount = p.getPrice() - p.getDiscount();
                o.put(amount, p.getDiscount());
            } else {
                amount = (p.getPrice() * p.getDiscount()) / 100 * (100 - p.getDiscount());
                o.put(amount, p.getDiscount());
            }
            calculatedOrder.put(p.getId(), o);
            totalAmount += amount;
        }
        HashMap<Double, Integer> o = new HashMap<Double, Integer>();
        o.put(totalAmount, -1);
        calculatedOrder.put("-1", o);
        return calculatedOrder;
    }

}
