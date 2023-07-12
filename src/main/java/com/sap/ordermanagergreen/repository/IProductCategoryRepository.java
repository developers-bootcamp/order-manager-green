package com.sap.ordermanagergreen.repository;

import org.springframework.stereotype.Component;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

@Component
public interface IProductCategoryRepository extends MongoRepository<ProductCategory, String> {
    
    boolean existsByName(String categoryName);

}
