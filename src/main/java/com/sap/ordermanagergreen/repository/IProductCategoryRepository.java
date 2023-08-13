package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.Order;
import org.springframework.stereotype.Component;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductCategoryRepository extends MongoRepository<ProductCategory, String> {
    
    boolean existsByName(String categoryName);
    List<ProductCategory> findAllByCompany_Id(String companyId);

}
