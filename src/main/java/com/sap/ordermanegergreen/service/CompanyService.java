package com.sap.ordermanegergreen.service;

import org.springframework.stereotype.Service;
import com.sap.ordermanegergreen.model.Company;
import com.sap.ordermanegergreen.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CompanyService {

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

    public Company put(String id, Company company) {
        companyRepository.deleteById(id);
        companyRepository.save(company);
        return company;
    }

    public void deletebyId(String companyId) {
        companyRepository.deleteById(companyId);
    }

    public boolean existsByName(String name) {
      return companyRepository.existsByName(name);
    }

}
