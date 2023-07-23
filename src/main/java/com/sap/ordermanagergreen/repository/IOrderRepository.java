package com.sap.ordermanagergreen.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sap.ordermanagergreen.dto.TopProductDTO;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import java.time.Month;
import org.bson.Document;
import com.sap.ordermanagergreen.dto.ProductCountDto;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.AggregateIterable;

@Repository
public interface IOrderRepository extends MongoRepository<Order, String> {
    List<Order> findByOrderStatusAndCompany_Id(Pageable pageable, OrderStatus orderStatusId, String companyName);

    //    @Query()
    default List<TopProductDTO> getTopProductsGroupedByMonth(Date fromMonth, Date untilMonth) {
        MongoClient mongoClient = new MongoClient(mongodb);
        MongoDatabase database = mongoClient.getDatabase("Orders");
        MongoCollection<Document> collection = database.getCollection("Orders");

        AggregateIterable<Document> result = getcollection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("audit_data.create_date",
                                new Document("$gte", fromMonth)
                                        .append("$lt", untilMonth))),
                new Document("$unwind",
                        new Document("path", "$order_items")),
                new Document("$project",
                        new Document("month",
                                new Document("$month",
                                        new Document("$toDate", "$audit_data.create_date")))
                                .append("product", "$order_items.product_id")
                                .append("quantity", "$order_items.quantity")),
                new Document("$group",
                        new Document("_id",
                                new Document("month", "$month")
                                        .append("product", "$product"))
                                .append("totalQuantity",
                                        new Document("$sum", "$quantity"))),
                new Document("$sort",
                        new Document("totalQuantity", -1L)),
                new Document("$group",
                        new Document("_id", "$_id.month")
                                .append("products",
                                        new Document("$push",
                                                new Document("product", "$_id.product")
                                                        .append("totalQuantity", "$totalQuantity"))))));

        List<TopProductDTO> topProducts = new ArrayList<>();
        for (Document document : result) {
            int month = document.getInteger("_id");
            List<Document> products = document.get("products", List.class);
            List<ProductCountDto> productCountList = products.stream()
                    .map(productDocument -> {
                        String productName = productDocument.getString("product");
                        int count = productDocument.getInteger("totalQuantity");
                        return new ProductCountDto(productName, count);
                    })
                    .collect(Collectors.toList());

            TopProductDTO topProductDTO = new TopProductDTO(Month.of(month), productCountList);
            topProducts.add(topProductDTO);
        }

        return topProducts;
    }
}
