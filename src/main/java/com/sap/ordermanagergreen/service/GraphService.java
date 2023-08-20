package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.List;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;

import java.time.LocalDate;
import java.util.*;
import java.time.Month;

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

        List<TopEmployeeDTO> topEmployee = result.getMappedResults();

        return topEmployee;
    }

        public List<DeliverCancelOrdersDTO> getDeliverCancelOrders() {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeMonthsAgo = currentDate.minusMonths(3);

        Aggregation aggregation = newAggregation(
                match(Criteria.where("auditData.updateDate").gte(threeMonthsAgo)),
                project()
                        .andExpression("month(auditData.updateDate)").as("month")
                        .and("orderStatus").as("orderStatus"),
                group("month")
                        .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatus").equalToValue(OrderStatus.DONE)).then(1).otherwise(0)).as("cancelled")
                        .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatus").equalToValue(OrderStatus.PAYMENT_CANCELED)).then(1).otherwise(0)).as("delivered"),
                project()
                        .and("_id").as("month")
                        .and("cancelled").as("cancelled")
                        .and("delivered").as("delivered")
        );

        AggregationResults<org.bson.Document> results = mongoTemplate.aggregate(aggregation, "Orders", org.bson.Document.class);
        List<org.bson.Document> mappedResults = results.getMappedResults();

            List<DeliverCancelOrdersDTO> resultsDTO = new ArrayList<>();
        for (Document mappedResult : mappedResults) {
            Month month = Month.of(mappedResult.getInteger("month"));
            int cancelled = mappedResult.getInteger("cancelled", 0);
            int delivered = mappedResult.getInteger("delivered", 0);

            DeliverCancelOrdersDTO resultDTO = new DeliverCancelOrdersDTO();
            resultDTO.setMonth(month);
            resultDTO.setCancelled(cancelled);
            resultDTO.setDelivered(delivered);

            resultsDTO.add(resultDTO);
        }

        return resultsDTO;
    }
}

