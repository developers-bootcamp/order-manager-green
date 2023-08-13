package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/Graph")
public class GraphController {
    @Autowired
    public GraphService graphService;

    @GetMapping("/topEmployee")
    public List<TopEmployeeDTO> getTopEmployee() {
        return graphService.getTopEmployee();
    }
}