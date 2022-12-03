package com.example.clothingstore.controller;

import com.example.clothingstore.config.EmailTemplate;
import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.*;
import com.example.clothingstore.model.UserEntity;
import com.example.clothingstore.service.UserService;
import com.example.clothingstore.service.impl.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/recoveryPassword")
public class PasswordRecoveryController {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(path = "/getOtp")
    public OtpSendMailResponseDTO sendOtpRecoveryCodeToUserEmail(@RequestBody EmailRecoveryDTO email) {
        if(email.getEmail() == null) {
            throw new BadRequest("change password by address for user fail! need ?email= param");
        }
        UserEntity user = userService.findByEmail(email.getEmail()).get(); //Kiem tra user voi email khoi phuc
        String otpCode = LocalVariable.GetOTP();
        try {
            sendRecoveryPass(email.getEmail(), user.getUsername(), otpCode);
            user.setOtp(otpCode);
            userService.save(user);
        } catch (MessagingException e) {
            throw new BadRequest("gmail send fail");
        }

        return new OtpSendMailResponseDTO("Valid", "Please check mail to get OTP");
    }

    public void sendRecoveryPass(String addressGmail, String username, String otpCode) throws MessagingException {
        emailSenderService.sendAsHTML(
                addressGmail,
                "Bạn đã Yêu cầu khôi phục mật khẩu cho " + username,
                EmailTemplate.TemplateRecoveryPassword(username, otpCode)
        );
    }
// Tạo table, username, email và otp --> Lưu otp và email,
// sau khi người dùng xác thực otp thì check dưới db
// Nếu otp đúng thì cho người dùng nhập password --> Call API recovery/{email} để đổi password;

    @PostMapping(path = "/checkOtp")
    public Object changePasswordByOTP(@RequestBody RecoveryOTP recoveryOTP)
    {
        UserEntity user = userService.findByEmail(recoveryOTP.getEmail()).get();
        if (recoveryOTP.getOtp().equals(user.getOtp()))
        {
            if (recoveryOTP.getNewPassword().equals(recoveryOTP.getRePassword()))
            {
                user.setPassword(passwordEncoder.encode(recoveryOTP.getNewPassword()));
                userService.save(user);
            }
            else {
                return "Repassword is incorrect!";
            }
        }
        else {
            return "OTP is incorrect!";
        }
        return "Chang password success!";
    }


    @PutMapping(path = "/recovery/{email}")
    public SuccessResponseDTO recoveryPassword(@PathVariable String email, @RequestBody RecoveryPasswordDTO userChangePassword) {
        //Check otp

        //Change password
        userService.changeUserPasswordByEmail(userChangePassword.getPassword(), email);
        return new SuccessResponseDTO(HttpStatus.OK, "Change password success");
    }
}
