package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/graph")
public class GraphController {

    private final GraphService graphService;
    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }
    @GetMapping("/getDeliverCancelOrders")
    public ResponseEntity<List<DeliverCancelOrdersDTO>> getDeliverCancelOrders() {
        try{
            return ResponseEntity.ok(graphService.getDeliverCancelOrders());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }}
