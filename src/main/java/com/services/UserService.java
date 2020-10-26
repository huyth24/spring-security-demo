package com.services;

import com.config.jwt.JwtProvider;
import com.dto.LoginDto;
import com.entities.CustomUserDetail;
import com.entities.Role;
import com.entities.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private JwtProvider jwtProvider = new JwtProvider();

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public ResponseData login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername());
        try {
            if (user != null) {
                CustomUserDetail userDetail = new CustomUserDetail(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = jwtProvider.generateToken(user.getUserId());
                return ResponseData.success(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.fail();
    }

    public ResponseData register(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        Role role = roleRepository.findByRoleName("USER");
        if(role == null){
            role = new Role();
            role.setRoleName("USER");
            roleRepository.save(role);
        }
        user.setRoles(Arrays.asList(role));
        return ResponseData.success(userRepository.saveAndFlush(user));
    }
}
