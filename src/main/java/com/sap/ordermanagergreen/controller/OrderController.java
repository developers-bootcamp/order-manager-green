package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.service.OrderService;
import com.sap.ordermanagergreen.util.JwtToken;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping
    public ResponseEntity<List<Order>> get(@NonNull @RequestParam Integer pageNo,
                                            @RequestParam(defaultValue = "10") @PositiveOrZero Integer pageSize,
                                            @RequestParam OrderStatus orderStatus
            ,@NotBlank @RequestHeader("token") String token) {
        TokenDTO tokenDto = JwtToken.decodeToken(token);
        List<Order> orders = null;
        orders = orderService.get(pageNo, pageSize, tokenDto.getCompanyId(), orderStatus);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<String> add(@NotBlank @RequestHeader("Authorization") String token,@Valid @RequestBody Order order) {
        TokenDTO tokenDto;
        try {
             tokenDto = JwtToken.decodeToken(token);
//            if (!tokenDto.getCompanyId().equals(order.getCompany().getId()))
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        try {
            return ResponseEntity.ok(orderService.add(order,tokenDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
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