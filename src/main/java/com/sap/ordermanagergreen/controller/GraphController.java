package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.service.GraphService;
import com.sap.ordermanagergreen.util.JwtToken;
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
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    private  GraphService graphService;

    @GetMapping("/topEmployee")
    public ResponseEntity<List<TopEmployeeDTO>> getTopEmployee(@RequestHeader("Authorization") String token) {
        TokenDTO tokenDto = JwtToken.decodeToken(token);
        try{
            return ResponseEntity.ok(graphService.getTopEmployee(tokenDto.getCompanyId()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/topProduct")
    public  ResponseEntity<List<GraphService.MonthlyProductSalesResult>> topProduct() {
        try{
            return ResponseEntity.ok(graphService.getMonthlyProductSales());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getDeliverCancelOrders")
    public ResponseEntity<List<DeliverCancelOrdersDTO>> getDeliverCancelOrders() {
        try {
            return ResponseEntity.ok(graphService.getDeliverCancelOrders());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/dynamicGraph/{object}/{field}")
    public ResponseEntity<List<GraphService.DynamicGraphResult>> dynamicGraph( @PathVariable("object") String object,@PathVariable("field") String field) {
        try {
            return ResponseEntity.ok(graphService.dynamicGraph(object,field));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}