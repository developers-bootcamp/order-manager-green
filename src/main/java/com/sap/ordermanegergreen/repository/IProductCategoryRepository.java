package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductCategoryRepository extends MongoRepository<ProductCategory, String> {
    boolean existsByName(String categoryName);

}
