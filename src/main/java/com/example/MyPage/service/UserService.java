package com.example.MyPage.service;

import com.example.MyPage.DTO.LoginRequest;
import com.example.MyPage.DTO.SignUpRequest;
import com.example.MyPage.model.User;
import com.example.MyPage.repository.UserRepository;
import com.example.MyPage.security.JwtResponse;
import com.example.MyPage.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    public ResponseEntity<?> registerUser(SignUpRequest req)
    {
        if(userRepo.existsByEmail(req.getEmail()))
            return ResponseEntity.badRequest().body("Email already registered");

        String hashedPwd = new BCryptPasswordEncoder().encode(req.getPassword());
        User user = new User(req.getName(), req.getEmail(), hashedPwd, req.getPhone(), false);
        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> authenticateUser(LoginRequest req)
    {
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            UserDetails user = userDetailsService.loadUserByUsername(req.getEmail());
            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }
}
