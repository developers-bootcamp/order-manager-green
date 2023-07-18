package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class GraphService {
    private final MongoTemplate mongoTemplate;
    @Autowired
    public GraphService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public Map<Month,Map<Integer,Integer>> getDeliverCancelOrders() {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeMonthsAgo = currentDate.minusMonths(3);

        Aggregation aggregation = newAggregation(
                match(Criteria.where("auditData.createDate").gte(threeMonthsAgo)),
                project()
                        .andExpression("month(auditData.createDate)").as("month")
                        .and("orderStatusId").as("orderStatusId"),
                group("month")
                        .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatusId").equalToValue("0")).then(1).otherwise(0)).as("cancelled")
                        .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatusId").equalToValue("1")).then(1).otherwise(0)).as("delivered"),
                project()
                        .and("_id").as("month")
                        .and("cancelled").as("cancelled")
                        .and("delivered").as("delivered")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "Orders", Document.class);
        List<Document> mappedResults = results.getMappedResults();

        Map<Month,Map<Integer,Integer>> resultMap = new HashMap<>();
        for (Document mappedResult : mappedResults) {
            Month month = Month.of(mappedResult.getInteger("month"));
            int cancelled = mappedResult.getInteger("cancelled", 0);
            int delivered = mappedResult.getInteger("delivered", 0);

            Map<Integer, Integer> tempMap = new HashMap<>();
            tempMap.put(cancelled, delivered);
            resultMap.put(month,tempMap);
        }

        return resultMap;
    }

}
