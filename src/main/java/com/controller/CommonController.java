package com.controller;

import com.dto.LoginDto;
import com.response.ResponseData;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/login")
    public ResponseData login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @GetMapping("/users/logout")
    public ResponseData logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseData.success(null);
    }

    @GetMapping(value = "/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/page-admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String adminPage() {
        return "ADMIN";
    }
}
