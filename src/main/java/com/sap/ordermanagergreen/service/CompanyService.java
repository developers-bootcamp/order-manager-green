package com.sap.ordermanagergreen.service;

import org.springframework.stereotype.Service;
import com.sap.ordermanagergreen.model.Company;
import com.sap.ordermanagergreen.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CompanyService {

    ICompanyRepository companyRepository;

    @Autowired
    public CompanyService(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> get() {
        return companyRepository.findAll();
    }

    public Company get(String id) {
        return companyRepository.findById(id).get();
    }

    public void add(Company company) {
        companyRepository.save(company);
    }

    public Company update(String id, Company company) {
        companyRepository.deleteById(id);
        companyRepository.save(company);
        return company;
    }

    public void delete(String companyId) {
        companyRepository.deleteById(companyId);
    }

    public boolean existsByName(String name) {
      return companyRepository.existsByName(name);
    }


}