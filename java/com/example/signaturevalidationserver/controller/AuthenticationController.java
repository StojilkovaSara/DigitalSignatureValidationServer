package com.example.signaturevalidationserver.controller;

import com.example.signaturevalidationserver.model.User;
import com.example.signaturevalidationserver.model.exceptions.DuplicateEmailException;
import com.example.signaturevalidationserver.model.exceptions.DuplicateUsernameException;
import com.example.signaturevalidationserver.model.exceptions.UserNotFoundException;
import com.example.signaturevalidationserver.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String viewHomePage() {
        return "login";
    }

    @GetMapping("/register")
    public String getSignup(){
        return "register";
    }

    @GetMapping("/verify_email")
    public  String verify(){
        return "verify_email";
    }

    @GetMapping("/passwordReset")
    public String getPasswordResetPage(){
        return "passwordReset";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String username,
                           @RequestParam String password,
                           Model model)  {

        User user;
        try {
            user = userService.saveUser(email, username, password);
        } catch (DuplicateEmailException e) {
            model.addAttribute("duplicateEmail", true);
            return "/register";
        } catch (DuplicateUsernameException e) {
            model.addAttribute("duplicateUsername", true);
            return "/register";
        }

        userService.sendVerificationCode(user);

        return "redirect:/verify_email";
    }

    @PostMapping("/verify_email")
    public String verifyEmailWithCode(@RequestParam String code){
        try {
            userService.setIsValidated(userService.findByCode(code));
            return "redirect:/login";
        } catch (UserNotFoundException e){
            return "redirect:/verify_email";
        }
    }

    @PostMapping("/sendPasswordResetToken")
    public String sendPasswordResetToken(@RequestParam String username) {
        userService.sendPasswordResetToken(username);
        return "redirect:/passwordReset";
    }

    @PostMapping("/passwordReset")
    public String passwordReset(@RequestParam String username, @RequestParam String token, @RequestParam String password, Model model) {
        if (userService.validateUsernameAndPasswordResetToken(username, token)) {
            userService.changePassword(username, password);
            userService.cleanPasswordToken(username);
            return "login";
        } else {
            model.addAttribute("invalidToken", "invalid");
            return "passwordReset";
        }
    }



}
