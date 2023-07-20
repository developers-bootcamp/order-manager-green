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
    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IProductRepository productRepository) {
        this.orderRepository = orderRepository;

        this.productRepository = productRepository;
    }

    public List<Order> get(Integer pageNo, Integer pageSize, String companyId, int employeeId, OrderStatus orderStatus) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        return orderRepository.findByOrderStatusAndCompany_Id(paging, orderStatus, companyId);

    }

    public String add(Order order) {
        Order newOrdr = this.orderRepository.insert(order);
        return newOrdr.getId();
    }

    public void update(String id, Order order) throws ObjectNotExist {

        if (orderRepository.findById(id).isEmpty())
            throw new ObjectNotExist();
        orderRepository.save(order);

    }

    public Map<String, HashMap<Double, Integer>> calculate(Order order) {
        HashMap<String, HashMap<Double, Integer>> calculatedOrder = new HashMap<String, HashMap<Double, Integer>>();
        double totalAmount = 0;

        for (int i = 0; i < order.getOrderItemsList().stream().count(); i++) {
            OrderItem oi = order.getOrderItemsList().get(i);
            Product p = oi.getProduct();
            HashMap<Double, Integer> o = new HashMap<Double, Integer>();
            double amount = 0;
            if (p.getDiscountType() == DiscountType.FIXED_AMOUNT) {
                amount =( p.getPrice() - p.getDiscount())*order.getOrderItemsList().get(i).getQuantity();
                o.put(amount, p.getDiscount());
            } else {
                amount = (p.getPrice() * p.getDiscount()) / 100 * (100 - p.getDiscount())*order.getOrderItemsList().get(i).getQuantity();
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
