package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/graph")
public class GraphController {
    @Autowired
    private  GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping
    @RequestMapping("/topEmployee")
    public List<TopEmployeeDTO> getTopEmployee() {
        return graphService.getTopEmployee();

    }
}