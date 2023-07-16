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
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
@Service
public class GraphService {
    private final MongoTemplate mongoTemplate;
    @Autowired
    public GraphService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public ResponseEntity<List<DeliverCancelOrdersDTO>> getDeliverCancelOrders() {
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

        for (Document result : results) {
            System.out.println("🤔🤔🤔🤔🤔🤔");
            System.out.println(result.toJson());
        }

        List<DeliverCancelOrdersDTO> resultDTOs = new ArrayList<>();
        for (Document mappedResult : mappedResults) {
            Month month = Month.of(mappedResult.getInteger("month"));
            int cancelled = mappedResult.getInteger("cancelled", 0);
            int delivered = mappedResult.getInteger("delivered", 0);

            DeliverCancelOrdersDTO resultDTO = new DeliverCancelOrdersDTO();
            resultDTO.setMonth(month);
            resultDTO.setCancelled(cancelled);
            resultDTO.setDelivered(delivered);

            resultDTOs.add(resultDTO);
        }

        return ResponseEntity.ok(resultDTOs);
    }
}
