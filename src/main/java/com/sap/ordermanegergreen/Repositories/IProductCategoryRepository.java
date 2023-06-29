package com.sap.ordermanegergreen.Repositories;

import com.sap.ordermanegergreen.Models.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface IProductCategoryRepository extends MongoRepository<ProductCategory, String> {
    boolean existsByName(String categoryName);

}
