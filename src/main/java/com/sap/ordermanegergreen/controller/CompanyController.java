package com.sap.ordermanegergreen.controller;

import com.sap.ordermanegergreen.dto.TokenDTO;
import com.sap.ordermanegergreen.model.Company;
import com.sap.ordermanegergreen.service.CompanyService;
import com.sap.ordermanegergreen.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private CompanyService companyService;
    private JwtToken jwtToken;
    @Autowired
    public CompanyController(CompanyService companyService,JwtToken jwtToken) {
        this.jwtToken=jwtToken;
        this.companyService = companyService;
    }
    @GetMapping
    @RequestMapping("/getToken/{token}")
    public TokenDTO getToken(@PathVariable String token) {
        return jwtToken.decodeToken(token);
    }
    @GetMapping
    @RequestMapping("/getAll")
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @GetMapping
    @RequestMapping("/getById/{id}")
    public Company getById(@PathVariable String id) {
        return companyService.getById(id);
    }

    @PostMapping
    @RequestMapping("/add")
    public void add(@RequestBody Company company) {
        companyService.add(company);
    }

    @PutMapping
    @RequestMapping("/editById/{id}")
    public Company editById(@RequestBody Company company, @PathVariable String id) {
        return companyService.editById(company, id);
    }

    @DeleteMapping
    @RequestMapping("/deleteById/{id}")
    public void deleteById(@PathVariable String id) {
        companyService.deletebyId(id);
    }
}
