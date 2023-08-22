package com.sap.ordermanagergreen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.sap.ordermanagergreen.model.Role;
import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.repository.IRoleRepository;

import java.util.List;

@Service
public class RoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private IRoleRepository rolesRepository;

    public List<Role> get() {
        return rolesRepository.findAll();
    }

    public Role getById(String id) {
        return rolesRepository.findById(id).get();
    }

    public Role get(AvailableRole name) {
        return rolesRepository.getByName(name);
    }

    public void add(Role roles) {
        LOGGER.info("add role:"+roles.getName());
        rolesRepository.save(roles);
    }

    public Role update(String id, Role roles) {
        LOGGER.info("update role");
        rolesRepository.deleteById(id);
        rolesRepository.save(roles);
        return roles;
    }

    public void delete(String roleId) {
        rolesRepository.deleteById(roleId);
    }

}