package com.PTIV.loja.de.games.service;

import com.PTIV.loja.de.games.model.Role;
import com.PTIV.loja.de.games.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getCustomerRole() {
        return roleRepository.findById(1).get();
    }
}

