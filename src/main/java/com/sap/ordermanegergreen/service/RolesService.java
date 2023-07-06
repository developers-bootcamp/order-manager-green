package com.sap.ordermanegergreen.service;

import org.springframework.stereotype.Service;
import com.sap.ordermanegergreen.model.Roles;
import com.sap.ordermanegergreen.model.AvailableRoles;
import com.sap.ordermanegergreen.repository.IRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class RolesService {

    IRolesRepository rolesRepository;

    @Autowired
    public RolesService(IRolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<Roles> getAll() {
        return rolesRepository.findAll();
    }

    public Roles getById(String id) {
        return rolesRepository.findById(id).get();
    }

    public void add(Roles roles) {
        rolesRepository.save(roles);
    }

    public Roles put(String id, Roles roles) {
        rolesRepository.deleteById(id);
        rolesRepository.save(roles);
        return roles;
    }

    public void deleteById(String roleId) {
        rolesRepository.deleteById(roleId);
    }

    public Roles getByName(AvailableRoles name) {
      return rolesRepository.getByName(name);
    }

}
