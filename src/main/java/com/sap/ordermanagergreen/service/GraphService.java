package com.sap.ordermanagergreen.service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.sap.ordermanagergreen.dto.MonthlyProductSalesResult;
import com.sap.ordermanagergreen.dto.ProductCountDto;
import com.sap.ordermanagergreen.dto.TopProductDTO;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import com.sap.ordermanagergreen.repository.IProductCategoryRepository;
import com.sap.ordermanagergreen.repository.IProductRepository;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;
import java.util.Currency;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.DateOperators.Month.month;

@Service
public class GraphService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Getter
    @Setter
    public class ProductSalesResult {
        private Product product;
        private int totalQuantity;

        // Getters and setters
    }
    public List<MonthlyProductSalesResult> getMonthlyProductSales() {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeMonthsAgo = currentDate.minusMonths(3);

        Aggregation aggregation = newAggregation(
                match(Criteria.where("auditData.createDate").gte(LocalDate.now().minusMonths(3))),
                match(Criteria.where("orderStatus").is(OrderStatus.DONE)),
                unwind("orderItemsList"), // Unwind the orderItemsList array
                group("orderItemsList.product") // Group by product_id
                        .sum("orderItemsList.quantity").as("totalQuantity"), // Sum the quantity for each product
                project("totalQuantity").and("_id").as("product")
        );


        AggregationResults<ProductSalesResult> results = mongoTemplate.aggregate(
                aggregation, Order.class, ProductSalesResult.class
        );

        return null; //results.getMappedResults().stream().map();
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
            Month lastMonth = new Month(currentYear, month);
            TopProductDTO topProductDTO = new TopProductDTO(lastMonth, productCountList);
            topProducts.add(topProductDTO);
        }

        return topProducts;
    }
}

