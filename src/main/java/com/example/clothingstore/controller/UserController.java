package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.ChangePasswordDTO;
import com.example.clothingstore.model.UserEntity;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    //Get all user
    @GetMapping("admin/users/getAllUser")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }

    //Get current user
    @GetMapping("/user/profile")
    public ResponseEntity<?> getCurUser(){
        UserEntity userEntity = userDetailService.getCurrentUser();
        UserEntity respone = new UserEntity(userEntity.getId(), userEntity.getUsername(), userEntity.getFullname(), userEntity.getPhone(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getAddress(), userEntity.getGender(), userEntity.getDob(),userEntity.getStatus());
        return ResponseEntity.ok(respone);
    }

    @GetMapping("admin/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id){
        try {
            return ResponseEntity.ok(userService.findById(id));
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindUser + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/profile/change")
    public Object changeProfile(@RequestBody UserEntity userEntity) throws ParseException {
        UserEntity user = userService.findByUsername(userEntity.getUsername()).get();
        user.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        if (userEntity.getFullname() != null)
        {
            user.setFullname(userEntity.getFullname());
        }
        if (userEntity.getAddress() != null)
        {
            user.setAddress(userEntity.getAddress());
        }
        if (userEntity.getPhone() != null)
        {
            user.setPhone(userEntity.getPhone());
        }
        if (userEntity.getDob() != null)
        {
            user.setDob(userEntity.getDob());
        }
        if (userEntity.getGender() != null)
        {
            user.setGender(userEntity.getGender());
        }
        userService.save(user);
        return "Change user profile success!";
    }

    @PostMapping("/user/changepassword")
    public Object changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws ParseException {
        UserEntity user = userDetailService.getCurrentUser();
        if (changePasswordDTO.getNewPassword().equals(changePasswordDTO.getRePassword()))
        {
            if (passwordEncoder.encode(changePasswordDTO.getCurPassword()).equals(user.getPassword()))
            {
                user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            }
            else
            {
                return "Password is incorrect!";
            }
        }
        else
        {
            return "Re-password is incorrect!";
        }

        return "Change password success!";
    }

    @PostMapping("admin/users/uprole")
    public ResponseEntity<?> upRoleUser(@RequestParam(value = "email", required = false) String email){
        String username = userService.findByUsername(email).get().getUsername();
        if (userService.upRole(email)){
            return ResponseEntity.ok("Update Role User "+username+" Success");
        }
        return ResponseEntity.ok("User "+username+" already have this role!!!");
    }

    @PostMapping("admin/users/downrole")
    public ResponseEntity<?> downRoleUser(@RequestParam(value = "email", required = false) String email){
        String username = userService.findByUsername(email).get().getUsername();
        if (userService.downRole(email)){
            return ResponseEntity.ok("Update Role User "+username+" Success");
        }
        return ResponseEntity.ok("User "+username+" already have this role!!!");
    }

    @DeleteMapping("admin/users/delete")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "email", required = false) String email)
    {
        if (userService.existsByEmail(email))
        {
            String username = userService.findByUsername(email).get().getUsername();
            Long userId = userService.findByEmail(email).get().getId();
            userService.deleteById(userId);
            return ResponseEntity.ok("Delete "+username+" success!");
        }
        return ResponseEntity.ok("Username unavailable!");
    }
}
