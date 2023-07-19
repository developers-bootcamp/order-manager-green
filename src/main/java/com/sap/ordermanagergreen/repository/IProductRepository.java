package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends MongoRepository<Product, String> {
    
    boolean existsByName(String Name);
    List<Product> findAllByCompanyId(String companyId);
    @Query(value ="{'name': {$regex : '^?0', $options: 'i'},'companyId':?1}", fields = "{'id': 1, 'name': 1}")
    List<Product> findProductsByNameStartingWithAndCompanyIdEqual(String prefix,String companyId);
    
}
