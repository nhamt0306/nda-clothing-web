package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.RoleEntity;
import com.example.clothingstore.model.UserEntity;
import com.example.clothingstore.repository.RoleRepository;
import com.example.clothingstore.repository.UserRepository;
import com.example.clothingstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // up role user to admin
    @Override
    public Boolean upRole(String email) {
        // Get user
        Optional<UserEntity> curUser = userRepository.findByEmail(email);
        // Get admin role
        Optional<RoleEntity> roleAdmin = roleRepository.findById(Long.parseLong("1"));
        // Get role cur user
        Set<RoleEntity> roleUser = curUser.get().getRoles();
        // Check if role's user not contain "admin" role --> user only have role "user"
        if (!roleUser.contains(roleAdmin.get()))
        {
            roleUser.add(roleAdmin.get());
            curUser.get().setRoles(roleUser);
            curUser.get().setUpdate_at(new Timestamp(System.currentTimeMillis()));
            userRepository.save(curUser.get());
            return true;
        }
        return false;
    }

    // down role admin to user
    @Override
    public Boolean downRole(String email) {
        // Get user
        Optional<UserEntity> curUser = userRepository.findByEmail(email);
        // Get admin role
        Optional<RoleEntity> roleAdmin = roleRepository.findById(Long.parseLong("1"));
        // Get role cur user
        Set<RoleEntity> roleUser = curUser.get().getRoles();
        // Check if role's user not contain "admin" role --> user only have role "user"
        if (roleUser.contains(roleAdmin.get()))
        {
            roleUser.remove(roleAdmin.get());
            curUser.get().setRoles(roleUser);
            curUser.get().setUpdate_at(new Timestamp(System.currentTimeMillis()));
            userRepository.save(curUser.get());
            return true;
        }
        return false;
    }
}
