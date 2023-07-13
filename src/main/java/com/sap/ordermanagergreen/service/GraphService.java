package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.swing.text.Document;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class GraphService {

    IOrderRepository orderRepository;

    @Autowired
    public GraphService(IOrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    @Autowired
    private MongoTemplate mongoTemplate;

    public Map<User, Integer> getTopEmployee(){

        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        Criteria criteria = Criteria.where("orderStatusId").is("delivered").and("auditData.createDate").gte(threeMonthsAgo);
        MatchOperation match = Aggregation.match(criteria);
        LookupOperation lookup = Aggregation.lookup("User","employeeId","id","employee");
        GroupOperation group = group("employeeId").count().as("CountOfDeliveredOrders");
//        group.lookup("users", "employeeId", "id","user",
//                LookupOptions.builder().returnDocument(LookupOptions.ReturnDocument.AFTER).build());
        group.push("user");
        SortOperation sort = sort(Sort.Direction.DESC, "CountOfDeliveredOrders");
        LimitOperation limit = limit(5);
//        Projection projection = Projection.include("User", "CountOfDeliveredOrders"));



        Aggregation aggregation = newAggregation(
                match,
                lookup,
                group,
                sort,
                limit
//                projection
        );

        AggregationResults<Document> groupResults
                = mongoTemplate.aggregate(aggregation, "orders", Document.class);

        Map<User, Integer> result = new HashMap<>();
        for (Document document : groupResults.getMappedResults()) {
//            User user = document.get("user", User.class);
//            Integer count = document.getInteger("CountOfDeliveredOrders");
//            result.put(user, count);
        }

        return result;
    }
}

