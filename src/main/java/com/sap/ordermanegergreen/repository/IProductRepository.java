package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String Name);
}
