package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class GraphService {

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<TopEmployeeDTO> getTopEmployee(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = LocalDate.now().minusMonths(3).format(formatter);
        String endDate = LocalDate.now().format(formatter);

        Aggregation aggregation = newAggregation(
                match(where("order_status").is("DONE")
                        .and("audit_data.create_date").gte(startDate).lt(endDate)),
                group("employee_id").count().as("CountOfDeliveredOrders"),
                lookup("User","employee_id","id","user"),
                sort(Sort.Direction.DESC, "CountOfDeliveredOrders"),
                limit(5),
                project("user","CountOfDeliveredOrders").andExclude("_id")
        );

        AggregationResults<org.bson.Document> groupResults
                = mongoTemplate.aggregate(aggregation, "Orders", org.bson.Document.class);
        System.out.println(groupResults.getMappedResults());

        List<TopEmployeeDTO> result = new ArrayList<>();
        for (Document document : groupResults.getMappedResults()) {
            TopEmployeeDTO topEmployeeDTO = new TopEmployeeDTO();
            topEmployeeDTO.setUser((User) document.get("user[0]"));
            topEmployeeDTO.setCountOfDeliveredOrders(document.getInteger("CountOfDeliveredOrders"));
            result.add(topEmployeeDTO);
        }

        return result;
    }

}

