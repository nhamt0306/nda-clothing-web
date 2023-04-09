package com.example.clothingstore.controller;

import com.example.clothingstore.config.mapper.JwtResponse;
import com.example.clothingstore.config.mapper.ResponseMessage;
import com.example.clothingstore.config.mapper.SignInForm;
import com.example.clothingstore.config.mapper.SignUpForm;
import com.example.clothingstore.model.CartEntity;
import com.example.clothingstore.model.RoleEntity;
import com.example.clothingstore.model.RoleName;
import com.example.clothingstore.model.UserEntity;
import com.example.clothingstore.security.jwt.JwtProvider;
import com.example.clothingstore.security.principal.UserPrinciple;
import com.example.clothingstore.service.impl.CartServiceImpl;
import com.example.clothingstore.service.impl.RoleServiceImpl;
import com.example.clothingstore.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*")   //Để ghép AuthController với các controller khác
@RequestMapping
@RestController
public class AuthenticationController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CartServiceImpl cartService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpForm signUpForm){
        if (userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("Tên tài khoản đã tồn tại! Vui lòng thử lại", "false"), HttpStatus.CONFLICT);
        }
        if (userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("Email đã tồn tại! Vui lòng thử lại","false"), HttpStatus.CONFLICT);
        }


        UserEntity user = new UserEntity(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), signUpForm.getPhonenumber(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRole =signUpForm.getRoles();
        Set<RoleEntity> roles = new HashSet<>();
        strRole.forEach(role -> {
            switch (role){
                case "ADMIN":
                    RoleEntity adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            ()-> new RuntimeException("Role không hợp lệ!")
                    );
                    roles.add(adminRole);
                    break;
                default:
                    RoleEntity userRole = roleService.findByName(RoleName.USER).orElseThrow(
                            ()-> new RuntimeException("Role không hợp lệ!")
                    );
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        user.setCartEntity(new CartEntity(user.getId()));
        userService.save(user);
        cartService.save(user.getId());
        return new ResponseEntity<>(new ResponseMessage("Tạo user thành công!"), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInForm signInForm){
        // UsernamePasswordAuthenticationToken sẽ kiểm tra thông tin người dùng
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword())
        );
        // Set token lên hệ thống
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Tạo jwt token
        String token = jwtProvider.createToken(authentication.getName());
        // Lấy ra thông tin người dùng hiện tại trên hệ thống
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        // Trả về kết quả.
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(), userPrinciple.getId(), userPrinciple.getAuthorities(), userPrinciple.getAvatar()));
    }

    @GetMapping("/login")
    public ResponseEntity<?> getRequest(HttpServletRequest request) {
        System.out.println("go to the login page");
        String username = request.getParameter("userName");
        SignInForm signInForm = new SignInForm();
        signInForm.setUsername(username);
        signInForm.setPassword(username);

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword())
//        );
//        // Set token lên hệ thống
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        // Tạo jwt token
//        String token = jwtProvider.createToken(authentication.getName());
//        // Lấy ra thông tin người dùng hiện tại trên hệ thống
//        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
//        // Trả về kết quả.

        return login(signInForm);
//        login(signInForm);

//        URI urlPayment = new URI("http://localhost:3000");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(urlPayment);
//        httpHeaders.set("token",token);
//        httpHeaders.set("name", userPrinciple.getName());
//        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}
