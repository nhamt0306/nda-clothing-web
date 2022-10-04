package com.example.clothingstore.service.impl;


import com.example.clothingstore.model.RoleEntity;
import com.example.clothingstore.model.RoleName;
import com.example.clothingstore.repository.RoleRepository;
import com.example.clothingstore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;


    @Override
    public Optional<RoleEntity> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
