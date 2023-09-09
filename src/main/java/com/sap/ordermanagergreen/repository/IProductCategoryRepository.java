package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductCategoryRepository extends MongoRepository<ProductCategory, String> {

    boolean existsByNameAndCompany_Id(String categoryName,String companyId);
    List<ProductCategory> findAllByCompany_Id(String companyId);
    ProductCategory findByName(String name);

}
