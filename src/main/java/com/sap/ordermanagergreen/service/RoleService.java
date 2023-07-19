package com.sap.ordermanagergreen.service;

import org.springframework.stereotype.Service;
import com.sap.ordermanagergreen.model.Role;
import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class RoleService {

    IRoleRepository rolesRepository;

    @Autowired
    public RoleService(IRoleRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<Role> getAll() {
        return rolesRepository.findAll();
    }

    public Role getById(String id) {
        return rolesRepository.findById(id).get();
    }

    public void add(Role roles) {
        rolesRepository.save(roles);
    }

    public Role put(String id, Role roles) {
        rolesRepository.deleteById(id);
        rolesRepository.save(roles);
        return roles;
    }

    public void deleteById(String roleId) {
        rolesRepository.deleteById(roleId);
    }

    public Role getByName(AvailableRole name) {
      return rolesRepository.getByName(name);
    }

}
