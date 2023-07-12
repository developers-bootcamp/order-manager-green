package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;

@Component
public interface IProductRepository extends MongoRepository<Product, String> {
    
    boolean existsByName(String Name);
    
}
