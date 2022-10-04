package com.example.clothingstore.service;



import com.example.clothingstore.model.RoleEntity;
import com.example.clothingstore.model.RoleName;

import java.util.Optional;

public interface RoleService {
    Optional<RoleEntity> findByName(RoleName name);
}
