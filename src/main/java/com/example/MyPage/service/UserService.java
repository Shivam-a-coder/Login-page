package com.example.MyPage.service;

import com.example.MyPage.DTO.LoginRequest;
import com.example.MyPage.DTO.SignUpRequest;
import com.example.MyPage.model.User;
import com.example.MyPage.repository.UserRepository;
import com.example.MyPage.security.JwtResponse;
import com.example.MyPage.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

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
    @Autowired
    PasswordEncoder passwordEncoder;


    public ResponseEntity<?> registerUser(SignUpRequest req)
    {
        if(userRepo.existsByEmail(req.getEmail()))
            return ResponseEntity.badRequest().body("Email already registered");

        String hashedPwd =  passwordEncoder.encode(req.getPassword());
        User user = new User(req.getName(), req.getEmail(), hashedPwd, req.getPhone(), false);
        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> authenticateUser(LoginRequest req)
    {
        try{
            User user = userRepo.findByEmail(req.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            System.out.println("Stored Password in DB: " + user.getPassword());
            System.out.println("Entered Password: " + req.getPassword());


            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        catch (Exception e)
        {
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }
}
