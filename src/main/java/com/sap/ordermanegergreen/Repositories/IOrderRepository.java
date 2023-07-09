package com.sap.ordermanegergreen.Repositories;

import com.sap.ordermanegergreen.Models.Orders;
import com.sap.ordermanegergreen.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Component
public interface IOrderRepository extends PagingAndSortingRepository<Orders, Integer> {
    List<Orders> findByOrderStatusId(Pageable pageable, String companyId,String orderStatusId);
}
