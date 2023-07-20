package com.sap.ordermanagergreen.service;

import org.springframework.stereotype.Service;
import com.sap.ordermanagergreen.model.Role;
import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private IRoleRepository rolesRepository;
    public List<Role> get() {
        return rolesRepository.findAll();
    }

    public Role get(String id) {
        return rolesRepository.findById(id).get();
    }

    public Role get(AvailableRole name) {
        return rolesRepository.getByName(name);
    }

    public void add(Role roles) {
        rolesRepository.save(roles);
    }

    public Role update(String id, Role roles) {
        rolesRepository.deleteById(id);
        rolesRepository.save(roles);
        return roles;
    }

    public void delete(String roleId) {
        rolesRepository.deleteById(roleId);
    }
}
