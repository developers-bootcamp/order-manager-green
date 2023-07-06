package com.sap.ordermanegergreen.repositories;

import com.sap.ordermanegergreen.models.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface IProductCategoryRepository extends MongoRepository<ProductCategory, String> {
    boolean existsByName(String categoryName);

}
