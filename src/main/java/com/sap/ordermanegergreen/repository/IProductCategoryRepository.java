package com.sap.ordermanegergreen.repository;

import org.springframework.stereotype.Component;
import com.sap.ordermanegergreen.model.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

@Component
public interface IProductCategoryRepository extends MongoRepository<ProductCategory, String> {
    
    boolean existsByName(String categoryName);

}
