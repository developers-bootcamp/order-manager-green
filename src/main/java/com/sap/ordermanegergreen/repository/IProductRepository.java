package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String Name);
}
