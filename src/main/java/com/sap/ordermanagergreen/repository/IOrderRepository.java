package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface IOrderRepository extends MongoRepository<Orders, String> {
}
