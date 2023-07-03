package com.sap.ordermanegergreen.service;

import com.sap.ordermanegergreen.model.Company;
import com.sap.ordermanegergreen.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService  {
    ICompanyRepository companyRepository;

    @Autowired
    public CompanyService(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getById(String id) {
        return companyRepository.findById(id).get();
    }

    public void add(Company company) {
        companyRepository.save(company);
    }

    public Company editById(Company company, String id) {
        companyRepository.deleteById(id);
        companyRepository.save(company);
        return company;
    }

    public void deletebyId(String companyId) {
        companyRepository.deleteById(companyId);
    }
}
