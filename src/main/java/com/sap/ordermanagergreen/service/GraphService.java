package com.sap.ordermanagergreen.service;

import com.mongodb.client.model.Projections;
import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.List;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class GraphService {

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

        public List<DeliverCancelOrdersDTO> getDeliverCancelOrders() {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeMonthsAgo = currentDate.minusMonths(3);

        Aggregation aggregation = newAggregation(
                match(Criteria.where("auditData.updateDate").gte(threeMonthsAgo)),
                project()
                        .andExpression("month(auditData.updateDate)").as("month")
                        .and("orderStatusId").as("orderStatusId"),
                group("month")
                        .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatusId").equalToValue("0")).then(1).otherwise(0)).as("cancelled")
                        .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatusId").equalToValue("1")).then(1).otherwise(0)).as("delivered"),
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

