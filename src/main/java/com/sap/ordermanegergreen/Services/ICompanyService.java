package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.Company;

import java.util.List;

public interface ICompanyService {
    List<Company> getAll();
    Company getById(String id);
    void add(Company c);
    Company put(Company company, String id);
    void deletebyId(String id);}
