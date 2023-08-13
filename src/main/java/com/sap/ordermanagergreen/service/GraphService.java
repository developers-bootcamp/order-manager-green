package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class GraphService {

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    public MongoTemplate mongoTemplate;

    public List<TopEmployeeDTO> getTopEmployee() {

        Aggregation aggregation = newAggregation(
                match(Criteria.where("auditData.createDate").gte(LocalDate.now().minusMonths(3))),
                match(Criteria.where("orderStatus").is(OrderStatus.DONE)),
                group("employee").count().as("countOfDeliveredOrders"),
                project("countOfDeliveredOrders").and("_id").as("employee"),
                sort(Sort.Direction.DESC, "countOfDeliveredOrders"),
                limit(5)
        );

        AggregationResults<TopEmployeeDTO> result = mongoTemplate.aggregate(
                aggregation, "Order", TopEmployeeDTO.class
        );

        return result.getMappedResults();
    }
}

