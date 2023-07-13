package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.model.Role;
import org.springframework.stereotype.Service;
import com.sap.ordermanagergreen.model.AvailableRoles;
import com.sap.ordermanagergreen.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class RolesService {

    IRoleRepository roleRepository;

    @Autowired
    public RolesService(IRoleRepository rolesRepository) {
        this.roleRepository = rolesRepository;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getById(String id) {
        return roleRepository.findById(id).get();
    }

    public void add(Role role) {
        roleRepository.save(role);
    }

    public Role put(String id, Role role) {
        roleRepository.deleteById(id);
        roleRepository.save(role);
        return role;
    }

    public void deleteById(String roleId) {
        roleRepository.deleteById(roleId);
    }

//    public Role getByName(AvailableRoles name) {
//      return roleRepository.getByName(name);
//    }

}
