package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.DTO.TokenDTO;
import com.sap.ordermanegergreen.Models.Orders;
import com.sap.ordermanegergreen.Models.Product;
import com.sap.ordermanegergreen.Services.OrderService;
import com.sap.ordermanegergreen.Utils.JwtToken;
import com.sap.ordermanegergreen.exception.ObjectNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/Orders")
public class OrdersController {
    private OrderService orderService;
    private JwtToken jwtToken;

    @Autowired
    public OrdersController(OrderService orderService, JwtToken jwtToken) {
        this.orderService = orderService;
        this.jwtToken = jwtToken;
    }

    @GetMapping
    @RequestMapping("/")
    public ResponseEntity<List<Orders>> getOrders(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam int employeeId, @RequestParam String orderStatus
            , @RequestHeader("token") String token) {
        TokenDTO tokenDto = this.jwtToken.decodeToken(token);
        String companyId = tokenDto.getCompanyId();
        String orderTmp="1";

        List<Orders> orders = null;
        orders = this.orderService.getOrders(pageNo, pageSize, companyId, employeeId, orderStatus);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<String> createOrder(@RequestHeader("token") String token, @RequestBody Orders order) {
        TokenDTO tokenDto = jwtToken.decodeToken(token);
        if (!tokenDto.getCompanyId() .equals(order.getCompanyId().getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            return ResponseEntity.ok(this.orderService.createOrder(order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @PutMapping
    @RequestMapping("/update")
    public ResponseEntity updateOrder(@RequestHeader("token") String token, @RequestBody Orders order) throws ObjectNotExist {
        TokenDTO tokenDto = jwtToken.decodeToken(token);
        if (tokenDto.getCompanyId() != order.getCompanyId().getId())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            this.orderService.updateOrder(order);
        } catch (ObjectNotExist e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
        return ResponseEntity.status(HttpStatus.OK).build();

    }
    @PostMapping
    @RequestMapping("/calculate")
    public ResponseEntity< Map<String, HashMap<Double,Integer>> >calculate(Map<String,Integer> orderItems){
        try{
        return ResponseEntity.ok( this.orderService.calculate(orderItems));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }


    }
}







