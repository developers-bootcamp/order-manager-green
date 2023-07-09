package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.Orders;
import com.sap.ordermanegergreen.Repositories.IOrderRepository;
import com.sap.ordermanegergreen.Repositories.IOrderRepository2;
import com.sap.ordermanegergreen.exception.ObjectNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private IOrderRepository orderRepository;
    private IOrderRepository2 orderRepository2;

    @Autowired
    public OrderService(IOrderRepository orderRepository,IOrderRepository2 orderRepository2) {
        this.orderRepository = orderRepository;
        this.orderRepository2 = orderRepository2;
    }

    public List<Orders> getOrders(Integer pageNo, Integer pageSize, String companyId, int employeeId, String orderStatus) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        return orderRepository.findByOrderStatusId(paging, companyId, orderStatus);

    }
    public String createOrder(Orders order){
        Orders newOrdr=this.orderRepository2.insert(order);
        return newOrdr.getId();
    }
    public void updateOrder(Orders order) throws ObjectNotExist {

        if(orderRepository2.findById(order.getId()).isEmpty())
           throw new ObjectNotExist();
        orderRepository2.save(order);

    }
}
