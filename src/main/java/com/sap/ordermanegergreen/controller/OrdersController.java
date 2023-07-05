package com.sap.ordermanegergreen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/orders")
public class OrdersController {
    
    private OrdersService ordersService;
    private JwtToken jwtToken;
    
    @Autowired
    public OrdersController(OrdersService ordersService,JwtToken jwtToken) {
        this.jwtToken=jwtToken;
        this.ordersService = ordersService;
    }
    
    @GetMapping
    @RequestMapping("/getToken/{token}")
    public TokenDTO getToken(@PathVariable String token) {
        return jwtToken.decodeToken(token);
    }
    
    @GetMapping
    public List<Order> getAll() {
        return ordersservice.getAll();
    }

    @GetMapping("/{id}")
    public Company getById(@PathVariable String id) {
        return companyService.getById(id);
    }

    @PostMapping
    public void add(@RequestBody Company company) {
        companyService.add(company);
    }

    @PutMapping("/{id}")
    public Company editById(@RequestBody Company company, @PathVariable String id) {
        return companyService.editById(company, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        companyService.deletebyId(id);
    }
}
