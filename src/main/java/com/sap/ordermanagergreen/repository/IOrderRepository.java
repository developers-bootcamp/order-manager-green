package com.sap.ordermanagergreen.repository;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface IOrderRepository extends MongoRepository<Order, String> {
List<Order>findByOrderStatusInAndCompanyId(Pageable pageable, List<OrderStatus> orderStatus,String companyId);//,Query query

  //  List<Order> find(Query query);

//List<Order>find(Query query);
//List<Order>findByFilter(Map<String,String>filters);



//List<Order> findByOrderStatusAndCompanyId(Pageable pageable, OrderStatus orderStatus,String companyId);


}
