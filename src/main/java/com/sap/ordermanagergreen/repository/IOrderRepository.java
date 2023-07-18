package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends MongoRepository<Order, String> {
    List<Order> findByOrderStatusAndCompanyId(Pageable pageable, OrderStatus orderStatusId, String companyName);
}
