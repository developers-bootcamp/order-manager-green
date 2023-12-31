package com.sap.ordermanagergreen.service;

import ch.qos.logback.core.spi.AbstractComponentTracker;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.CompanyNotExistException;
import com.sap.ordermanagergreen.exception.UserDosentExistException;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.model.Currency;
import com.sap.ordermanagergreen.repository.*;
import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import lombok.SneakyThrows;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.net.http.HttpHeaders;
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
    private CurrencyExchangeService currencyExchangeService;
    @Autowired
    private OrderChargingBL orderChargingBL;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Order> get(Integer pageNo, Integer pageSize, String companyId,  List<OrderStatus> orderStatus, String sortBy,List<Filter>filters) throws Exception {//,
        Pageable paging;
        if (sortBy != "") {
            Sort sort = Sort.by(sortBy).ascending();
            paging = PageRequest.of(pageNo, pageSize, sort);
        } else {
            paging = PageRequest.of(pageNo, pageSize);
        }
        Criteria criteria = Criteria.where("orderStatus").in(orderStatus)
                .and("companyId.id").is(companyId);
        criteria.where("orderStatus").in(orderStatus);
        filters.forEach( e -> {
            criteria.and(e.getFieldName()).is(e.getFilterValue());
    });

        Query query = new Query(criteria);
query.with(paging);
//        List<Order>aa= mongoTemplate.find(query, Order.class);
//        Page<Order> resultPage = new PageImpl<Order>(aa , paging,7);
//        resultPage.getTotalPages();
//return resultPage;
        List<Order>ans= mongoTemplate.find(query, Order.class);

       return ans;



        //return orderRepository.findByOrderStatusInAndCompanyId(paging,orderStatus,companyId);//,query

    }
//@SneakyThrows
//    public String add(Order order, TokenDTO token) throws JsonProcessingException,UserDosentExistException,CompanyNotExistException {
//        order.setCompany(companyRepository.findById(token.getCompanyId()).get());
//
//        if (userRepository.findById(token.getUserId()).get() == null)
//            throw new UserDosentExistException("employee dosent exist");
//        order.setEmployee(userRepository.findById(token.getUserId()).get());
//        try{
//            Order newOrder = this.orderRepository.insert(order);
//            if(newOrder.getOrderStatus()==OrderStatus.APPROVED)
//                orderChargingBL.chargingStep(newOrder);
//            return newOrder.getId();}
//        catch (Exception e){
//            System.out.println(e);
//            throw  new Exception();
//        }
//        Criteria criteria = Criteria.where("orderStatus").in(orderStatus)
//                .and("companyId.id").is(companyId);
//        criteria.where("orderStatus").in(orderStatus);
//        filters.forEach((key,val) -> {
//            criteria.and(key).is(val);
//        });
//
//        Query query = new Query(criteria);
//        query.with(paging);
////        List<Order>aa= mongoTemplate.find(query, Order.class);
////        Page<Order> resultPage = new PageImpl<Order>(aa , paging,7);
////        resultPage.getTotalPages();
////return resultPage;
//        List<Order>ans= mongoTemplate.find(query, Order.class);
//
//        return ans;
//
//
//
//        //return orderRepository.findByOrderStatusInAndCompanyId(paging,orderStatus,companyId);//,query
//
//    }



@SneakyThrows
    public String add(Order order, TokenDTO token) {

        if (companyRepository.findById(token.getCompanyId()).get() == null)
            throw new CompanyNotExistException("company not exist");
        order.setCompany(companyRepository.findById(token.getCompanyId()).get());

        if (userRepository.findById(token.getUserId()).get() == null)
            throw new UserDosentExistException("employee dosent exist");
        order.setEmployee(userRepository.findById(token.getUserId()).get());
        try {
            Order newOrdr = this.orderRepository.insert(order);
            return newOrdr.getId();
        } catch (Exception e) {
            System.out.println("");
        }
        return "";

    }

    public void update(String id, Order order) throws ObjectNotExistException {
        if (orderRepository.findById(id).isEmpty())
            throw new ObjectNotExistException("order");
        orderRepository.save(order);
    }

    public Map<String, HashMap<Double, Integer>> calculate(Order order) throws Exception {
        List<OrderItem> items = new ArrayList<OrderItem>();
        String gate;
        order.getOrderItemsList().forEach(e -> {
            Product p = productRepository.findById(e.getProduct().getId()).get();
            items.add(OrderItem.builder().product(p).quantity(e.getQuantity()).build());
        });
        order.setOrderItemsList(items);
        try {
            gate = currencyExchangeService.getGate(order.getCompany().getCurrency(), order.getCurrency());
        }catch (Exception e){
            throw new Exception("Error connecting to Redis: " + e.getMessage());
        }
        HashMap<String, HashMap<Double, Integer>> calculatedOrder = new HashMap<String, HashMap<Double, Integer>>();

        double totalAmount = 0;
        for (int i = 0; i < order.getOrderItemsList().stream().count(); i++) {
            OrderItem oi = order.getOrderItemsList().get(i);
            Product p = oi.getProduct();
            HashMap<Double, Integer> o = new HashMap<Double, Integer>();
            double amount = 0;
            if (p.getDiscountType() == DiscountType.FIXED_AMOUNT) {
                amount = (p.getPrice() - p.getDiscount()) * order.getOrderItemsList().get(i).getQuantity()*Double.parseDouble(gate);
                o.put(amount, p.getDiscount());
            } else {
                amount = (p.getPrice() / 100 * (100 - p.getDiscount()) * order.getOrderItemsList().get(i).getQuantity())*Double.parseDouble(gate);
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
    public long count(Map<String,Object>filters,List<OrderStatus>orderStatus,String companyId){

        Criteria criteria = Criteria.where("orderStatus").in(orderStatus)
                .and("companyId.id").is(companyId);
        criteria.where("orderStatus").in(orderStatus);
        filters.forEach((key,val) -> {
            criteria.and(key).is(val);
        });

        Query query = new Query(criteria);
        return mongoTemplate.count(query,Order.class);
    }

}