package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.*;
import com.sap.ordermanagergreen.model.Filter;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.service.OrderService;
import com.sap.ordermanagergreen.util.JwtToken;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping
    public ResponseEntity<List<Order>> get(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "3") Integer pageSize,
                                           @RequestParam("orderStatus") List<OrderStatus> orderStatus,
                                           @RequestParam String orderBy,@RequestBody List<Filter> filters
            ,@RequestHeader("Authorization") String token) {

        TokenDTO tokenDto=null;
        try{
            tokenDto = JwtToken.decodeToken(token);}

        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        List<Order> orders = null;
        try{
          //  Map<String,Object> filters=new HashMap<>();
           // filters.put("cvc","111");

            orders = this.orderService.get(pageNo, pageSize, tokenDto.getCompanyId(), orderStatus,orderBy,filters);
            //long count=orderService.count();
            org.springframework.http.HttpHeaders responseHeader=new org.springframework.http.HttpHeaders();
           // responseHeader.set("totalCount",String.valueOf(count));
            return ResponseEntity.ok().headers(responseHeader).body(orders);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
@GetMapping("/count")
public long countData(@RequestParam("orderStatus") List<OrderStatus> orderStatus ,@RequestHeader("Authorization") String token)
{
    TokenDTO tokenDto=JwtToken.decodeToken(token);
    Map<String,Object> filters=new HashMap<>();
return orderService.count(filters,orderStatus,tokenDto.getCompanyId());
}
@SneakyThrows
    @PostMapping
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @RequestBody Order order) {
        TokenDTO tokenDto;
        try {
            tokenDto = JwtToken.decodeToken(token);
            ;
//            if (!tokenDto.getCompanyId().equals(order.getCompany().getId()))
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        try {
            return ResponseEntity.ok(this.orderService.add(order,tokenDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @SneakyThrows
    public ResponseEntity update(@RequestHeader("token") String token, @PathVariable String id, @RequestBody Order order)  {
        TokenDTO tokenDto = JwtToken.decodeToken(token);
        if (tokenDto.getCompanyId() != order.getCompany().getId())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            this.orderService.update(id, order);
        } catch (ObjectNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/calculate")
    public ResponseEntity<Map<String, HashMap<Double, Integer>>> calculate(@RequestBody Order order) {
        try {
            orderService.calculate(order);
            return ResponseEntity.ok(this.orderService.calculate(order));
        } catch (Exception e) {
            Map<String, HashMap<Double, Integer>> t=new HashMap<>();
            t.put(e.getMessage(),new HashMap<>());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t);
        }
    }
}