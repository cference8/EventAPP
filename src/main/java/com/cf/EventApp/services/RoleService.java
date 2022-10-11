package com.cf.EventApp.services;

import com.cf.EventApp.daos.RoleRepository;
import com.cf.EventApp.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repo;


    public Role getRoleById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Role getRoleByRole(String r) {
        return repo.getRoleByRole(r);
    }

    public List<Role> getAllRoles() {
        return (List<Role>) repo.findAll();
    }
}
