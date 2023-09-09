package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends MongoRepository<Product, String> {

    boolean existsByNameAndCompanyId(String Name,String companyId);

    List<Product> findAllByCompany_Id(String id);

    @Query(value = "{'name': {$regex : '^?0', $options: 'i'},'company':?1}", fields = "{'id': 1, 'name': 1}")
    List<Product> findProductsByNameStartingWithAndCompany_IdEqual(String prefix, String id);

}
