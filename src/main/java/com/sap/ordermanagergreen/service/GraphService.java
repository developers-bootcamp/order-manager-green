package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.sap.ordermanagergreen.dto.ProductCountDto;
import com.sap.ordermanagergreen.dto.TopProductDTO;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import com.sap.ordermanagergreen.repository.IProductCategoryRepository;
import com.sap.ordermanagergreen.repository.IProductRepository;
import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.model.OrderStatus;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.List;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import java.time.Month;
import java.util.stream.Collectors;
import com.sap.ordermanagergreen.model.MonthInYear;

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

    public List<TopProductDTO> getTopProductsGroupedByMonth(LocalDate fromMonth, LocalDate untilMonth) {
        MongoCollection<org.bson.Document> orderCollection = mongoTemplate.getCollection("Order");


        AggregateIterable<org.bson.Document> result = orderCollection.aggregate(Arrays.asList(
           /*     new org.bson.Document("$match",
                        new org.bson.Document("auditData.createDate",
                                new org.bson.Document("$gte", fromMonth)
                                        .append("$lt", untilMonth))),*/
                new org.bson.Document("$unwind",
                        new org.bson.Document("path", "orderItemsList")),
                new org.bson.Document("$project",
                        new org.bson.Document("month",
                                new org.bson.Document("$month",
                                        new org.bson.Document("$toDate", "$auditData.createDate")))
                                .append("product", "orderItemsList.product_id")
                                .append("quantity", "orderItemsList.quantity")),
                new org.bson.Document("$group",
                        new org.bson.Document("_id",
                                new org.bson.Document("month", "$month")
                                        .append("product", "$product"))
                                .append("totalQuantity",
                                        new org.bson.Document("$sum", "$quantity"))),
                new org.bson.Document("$sort",
                        new org.bson.Document("totalQuantity", -1L)),
                new org.bson.Document("$group",
                        new org.bson.Document("_id", "$_id.month")
                                .append("products",
                                        new org.bson.Document("$push",
                                                new org.bson.Document("product", "$_id.product")
                                                        .append("totalQuantity", "$totalQuantity"))))));

        List<TopProductDTO> topProducts = new ArrayList<>();
        for (org.bson.Document document : result) {
            int month = document.getInteger("_id");
            List<Document> products = document.get("products", List.class);
            List<ProductCountDto> productCountList = products.stream()
                    .map(productDocument -> {
                        String productName = productDocument.getString("product");
                        int count = productDocument.getInteger("totalQuantity");
                        return new ProductCountDto(productName, count);
                    })
                    .collect(Collectors.toList());
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            MonthInYear lastMonth = new MonthInYear(currentYear, month);
            TopProductDTO topProductDTO = new TopProductDTO(lastMonth, productCountList);
            topProducts.add(topProductDTO);
        }

        return topProducts;
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