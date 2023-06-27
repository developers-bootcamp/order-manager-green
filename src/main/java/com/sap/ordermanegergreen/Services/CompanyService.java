package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.Company;
import com.sap.ordermanegergreen.Repositorys.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService implements ICompanyService {

    CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getById(String id) {
        return companyRepository.findById(id).get();
    }

    public void add(Company c) {
        companyRepository.save(c);
    }

    public Company put(Company company, String id) {
        companyRepository.deleteById(id);
        companyRepository.save(company);
        return company;
    }

    public void deletebyId(String id) {
        companyRepository.deleteById(id);
    }
}
