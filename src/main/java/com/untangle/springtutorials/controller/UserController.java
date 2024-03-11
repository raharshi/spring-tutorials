package com.untangle.springtutorials.controller;

import org.springframework.web.bind.annotation.RestController;

import com.untangle.springtutorials.dto.AuthRequest;
import com.untangle.springtutorials.dto.User;
import com.untangle.springtutorials.service.JwtService;
import com.untangle.springtutorials.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("getUser")
    @PreAuthorize("hasAuthority('ROLE_USER')") // method security
    public User getUser(@RequestParam(name = "id") long id) {
        return userService.getUser(id);
    }
    
    @GetMapping("getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("welcome")
    public String welcome() {
        return "Welcome to tutorials..!!!";
    }

    @PostMapping("authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(auth.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }else{
            new UsernameNotFoundException("Invalid user credentials!");
        }
        return "Invalid user credentials!";

    }
    
    
    

}
