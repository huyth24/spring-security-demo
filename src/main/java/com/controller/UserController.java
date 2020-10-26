package com.controller;

import com.dto.LoginDto;
import com.entities.User;
import com.response.ResponseData;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/find-all")
    private ResponseData findAll() {
        return ResponseData.success(userService.findAll());
    }

    @GetMapping("/user-info")
    private ResponseData getUserInfo() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseData.success(o);
    }

    @PostMapping("/register")
    private ResponseData register(@RequestBody LoginDto loginDto) {
        User user = new User(loginDto.getUsername(), loginDto.getPassword());
        try {
            return userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData("Username already exists", null);
        }
    }

}
