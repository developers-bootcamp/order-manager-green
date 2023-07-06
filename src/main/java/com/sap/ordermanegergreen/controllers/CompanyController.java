package com.sap.ordermanegergreen.controllers;

import com.sap.ordermanegergreen.dto.TokenDTO;
import com.sap.ordermanegergreen.models.Company;
import com.sap.ordermanegergreen.services.CompanyService;
import com.sap.ordermanegergreen.utils.JwtToken;
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
    public List<Company> getAll() {
        return companyService.getAll();
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

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        companyService.deletebyId(id);
    }
}
