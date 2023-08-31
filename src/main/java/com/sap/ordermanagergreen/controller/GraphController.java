package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.time.Month;
import java.util.Map;

import static com.sap.ordermanagergreen.OrderManagerGreenApplication.MY_URL;
import java.time.Month;
import java.util.Map;

@CrossOrigin(MY_URL)
@RestController
@RequestMapping("/Graph")
public class GraphController {

    @Autowired
    private  GraphService graphService;

    @GetMapping("/topEmployee")
    public ResponseEntity<List<TopEmployeeDTO>> getTopEmployee() {
        try{
            return ResponseEntity.ok(graphService.getTopEmployee());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/topProduct")
    public List<GraphService.MonthlyProductSalesResult> topProduct() {
        return graphService.getMonthlyProductSales();
    }

    @GetMapping("/getDeliverCancelOrders")
    public ResponseEntity<List<DeliverCancelOrdersDTO>> getDeliverCancelOrders() {
        try {
            return ResponseEntity.ok(graphService.getDeliverCancelOrders());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/fill")
    public void fill(){
        graphService.fill();
    }
}