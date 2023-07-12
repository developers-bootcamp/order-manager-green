package com.sap.ordermanegergreen.Repositories;

import com.sap.ordermanegergreen.Models.Orders;
import com.sap.ordermanegergreen.Models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IOrderRepository2 extends MongoRepository<Orders, String> {
}
