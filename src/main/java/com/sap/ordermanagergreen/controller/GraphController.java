package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/graph")
public class GraphController {
    @Autowired
    private  GraphService graphService;
}