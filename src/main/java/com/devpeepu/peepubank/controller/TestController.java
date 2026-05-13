package com.devpeepu.peepubank.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.devpeepu.peepubank.dto.LoginRequest;
import com.devpeepu.peepubank.dto.SignupRequest;
import com.devpeepu.peepubank.entity.User;
import com.devpeepu.peepubank.service.UserService;



@Controller
public class TestController {

    @Autowired
    UserService authService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session){
        if (session.getAttribute("user")==null){
            return "redirect:/login";
        }
        return "dashboard";
    }
    

    @PostMapping("/login")
    public String ppp(LoginRequest user, HttpSession session) {
        if(authService.loginUser(user.getUsername(), user.getPassword())){
            session.setAttribute("user", user.getUsername());
            return "redirect:/dashboard";
        }
        return "login";
        
       
    }

   @PostMapping("/signup")
    public String signup(SignupRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        boolean success = authService.registerUser(
            request,
            request.getConfirmPassword()
        );

        if (success) {
            return "redirect:/login";
        }

        return "signup";
    }
    
    
}