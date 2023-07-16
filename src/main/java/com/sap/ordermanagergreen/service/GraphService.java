package com.sap.ordermanagergreen.service;

import com.mongodb.client.model.Projections;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.repository.IOrderRepository;
import org.bson.conversions.Bson;
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


}

