package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TopProductDTO;
import com.sap.ordermanagergreen.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/graph")
public class GraphController {
    @Autowired
    private  GraphService graphService;


    @GetMapping("/topProduct")
    public List<TopProductDTO> topProduct() {
        graphService.getMonthlyProductSales();
        return graphService.getTopProductsGroupedByMonth(LocalDate.now().minusMonths(3).withDayOfMonth(1), LocalDate.now());
    }
}