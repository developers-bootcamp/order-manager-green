package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.CompanyNotExistException;
import com.sap.ordermanagergreen.exception.UserDosentExistException;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.repository.ICompanyRepository;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import com.sap.ordermanagergreen.repository.IProductRepository;
import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import com.sap.ordermanagergreen.repository.IUserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<Order> get(Integer pageNo, Integer pageSize, String companyId,  List<OrderStatus> orderStatus, String sortBy) {
        Pageable paging;
        if (sortBy != "") {
            Sort sort = Sort.by(sortBy).ascending();
            paging = PageRequest.of(pageNo, pageSize, sort);
        } else {
            paging = PageRequest.of(pageNo, pageSize);
        }
        return orderRepository.findByOrderStatusInAndCompanyId(paging,orderStatus,companyId);
    }
    public String add(Order order, TokenDTO token) throws CompanyNotExistException, UserDosentExistException, Exception {
        if (companyRepository.findById(token.getCompanyId()).get() == null)
            throw new CompanyNotExistException("company not exist");
        order.setCompany(companyRepository.findById(token.getCompanyId()).get());
        if (userRepository.findById(token.getUserId()).get() == null)
            throw new UserDosentExistException("employee dosent exist");
        order.setEmployee(userRepository.findById(token.getUserId()).get());
        Order newOrdr = this.orderRepository.insert(order);
        return newOrdr.getId();
    }
    public void update(String id, Order order) throws ObjectNotExistException {
        if (orderRepository.findById(id).isEmpty())
            throw new ObjectNotExistException("order");
        orderRepository.save(order);
    }
    public Map<String, HashMap<Double, Integer>> calculate(Order order) {
        List<OrderItem> items = new ArrayList<OrderItem>();
        order.getOrderItemsList().forEach(e -> {
            Product p = productRepository.findById(e.getProduct().getId()).get();
            items.add(OrderItem.builder().product(p).quantity(e.getQuantity()).build());
        });
        order.setOrderItemsList(items);
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