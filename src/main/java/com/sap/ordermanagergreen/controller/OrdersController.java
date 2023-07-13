package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Orders;
import com.sap.ordermanagergreen.service.OrderService;
import com.sap.ordermanagergreen.util.JwtToken;

import com.sap.ordermanagergreen.exception.ObjectNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/orders")
public class OrdersController {
    private OrderService orderService;
    private JwtToken jwtToken;

    @Autowired
    public OrdersController(OrderService orderService, JwtToken jwtToken) {
        this.orderService = orderService;
        this.jwtToken = jwtToken;
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getOrders(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam int employeeId, @RequestParam OrderStatus orderStatus
            , @RequestHeader("token") String token) {
        TokenDTO tokenDto = this.jwtToken.decodeToken(token);
        List<Orders> orders = null;
        orders = this.orderService.getOrders(pageNo, pageSize, tokenDto.getCompanyId(), employeeId, orderStatus);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestHeader("token") String token, @RequestBody Orders order) {
        try{
        TokenDTO tokenDto = jwtToken.decodeToken(token);
        if (!tokenDto.getCompanyId() .equals(order.getCompanyId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

        try {
            return ResponseEntity.ok(this.orderService.createOrder(order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOrder(@RequestHeader("token") String token, @PathVariable  String id,@RequestBody Orders order) throws ObjectNotExist {
        TokenDTO tokenDto = jwtToken.decodeToken(token);
        if (tokenDto.getCompanyId() != order.getCompanyId())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            this.orderService.updateOrder(id,order);
        } catch (ObjectNotExist e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
        return ResponseEntity.status(HttpStatus.OK).build();

    }
    @PostMapping
    @RequestMapping("/calculate")
    public ResponseEntity< Map<String, HashMap<Double,Integer>> >calculate(@RequestBody Orders order){
        try{
        return ResponseEntity.ok( this.orderService.calculate(order));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }


    }
}







