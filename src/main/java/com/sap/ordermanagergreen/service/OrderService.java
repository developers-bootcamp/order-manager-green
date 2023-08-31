package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.model.Currency;
import com.sap.ordermanagergreen.repository.ICompanyRepository;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import com.sap.ordermanagergreen.repository.IProductRepository;
import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import com.sap.ordermanagergreen.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICompanyRepository companyRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private OrderChargingBL orderChargingBL;

    public List<Order> get(Integer pageNo, Integer pageSize, String companyId, int employeeId, OrderStatus orderStatus) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return orderRepository.findByOrderStatusAndCompany_Id(paging, orderStatus, companyId);
    }
@SneakyThrows
    public String add(Order order, TokenDTO token) throws JsonProcessingException {
        order.setCompany(companyRepository.findById(token.getCompanyId()).get());
        order.setEmployee(userRepository.findById(token.getUserId()).get());
        try{
            Order newOrder = this.orderRepository.insert(order);
            if(newOrder.getOrderStatus()==OrderStatus.APPROVED)
                orderChargingBL.chargingStep(newOrder);
            return newOrder.getId();}
        catch (Exception e){
            System.out.println(e);
            throw  new Exception();
        }
    }

    public void update(String id, Order order) throws ObjectNotExistException, JsonProcessingException {
        if (orderRepository.findById(id).isEmpty())
            throw new ObjectNotExistException("order");
        orderRepository.save(order);
        if(order.getOrderStatus()==OrderStatus.APPROVED)
            orderChargingBL.chargingStep(order);
    }

    public Map<String, HashMap<Double, Integer>> calculate(Order order) {
        List<OrderItem> items = new ArrayList<OrderItem>();
        String gate;
        order.getOrderItemsList().forEach(e -> {
            Product p = productRepository.findById(e.getProduct().getId()).get();
            items.add(OrderItem.builder().product(p).quantity(e.getQuantity()).build());
        });
        order.setOrderItemsList(items);
        //מוצר והכמות שלו
        gate=currencyExchangeService.getGate(order.getCompany().getCurrency(),order.getCurrency());
        //חישוב הcurrency
        HashMap<String, HashMap<Double, Integer>> calculatedOrder = new HashMap<String, HashMap<Double, Integer>>();
        double totalAmount = 0;
        for (int i = 0; i < order.getOrderItemsList().stream().count(); i++) {
            OrderItem oi = order.getOrderItemsList().get(i);
            Product p = oi.getProduct();
            HashMap<Double, Integer> o = new HashMap<Double, Integer>();
            double amount = 0;
            if (p.getDiscountType() == DiscountType.FIXED_AMOUNT) {
                amount = (p.getPrice() - p.getDiscount()) * order.getOrderItemsList().get(i).getQuantity();
                o.put(amount, p.getDiscount());
            } else {
                amount = (p.getPrice() / 100 * (100 - p.getDiscount()) * order.getOrderItemsList().get(i).getQuantity());
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