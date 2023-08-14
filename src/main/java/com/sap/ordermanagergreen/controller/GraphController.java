package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    private  GraphService graphService;
}
    @GetMapping("/getDeliverCancelOrders")
    public ResponseEntity<List<DeliverCancelOrdersDTO>> getDeliverCancelOrders() {
        try{
            return ResponseEntity.ok(graphService.getDeliverCancelOrders());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
}}