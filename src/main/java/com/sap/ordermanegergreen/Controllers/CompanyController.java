package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.Models.Company;
import com.sap.ordermanegergreen.Services.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController("/Company")
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public Company getById(@PathVariable String id) {
        return companyService.getById(id);
    }

    @PostMapping
    public void add(@RequestBody Company p) {
        companyService.add(p);
    }

    @PutMapping("{id}")
    public Company put(@RequestBody Company company, @PathVariable String id) {
        return companyService.put(company, id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        companyService.deletebyId(id);
    }
}
