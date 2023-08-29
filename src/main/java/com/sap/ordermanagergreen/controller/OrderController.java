package com.sap.ordermanagergreen.controller;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.*;
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

import static com.sap.ordermanagergreen.OrderManagerGreenApplication.MY_URL;

@RestController
@CrossOrigin(MY_URL)
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping
    public ResponseEntity<List<Order>> get(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "1") Integer pageSize,
                                           @RequestParam("orderStatus") List<OrderStatus> orderStatus,
                                           @RequestParam String orderBy
            ,@RequestHeader("Authorization") String token) {
        TokenDTO tokenDto=null;
        try{
            tokenDto = JwtToken.decodeToken(token);}
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        List<Order> orders = null;
        try{
            List<OrderStatus> statusList=new  ArrayList<OrderStatus>();
            statusList.add(OrderStatus.CREATED);
            statusList.add(OrderStatus.APPROVED);
            orders = this.orderService.get(pageNo, pageSize, tokenDto.getCompanyId(), orderStatus,orderBy);
            return ResponseEntity.ok(orders);}
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
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
        }
        catch (CompanyNotExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (UserDosentExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (Exception e) {
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
    @PostMapping("/calculate")
    public ResponseEntity<Map<String, HashMap<Double, Integer>>> calculate(@RequestBody Order order) {
        try {
            return ResponseEntity.ok(this.orderService.calculate(order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}