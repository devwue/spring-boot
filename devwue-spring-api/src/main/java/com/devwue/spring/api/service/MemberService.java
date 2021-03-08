package com.devwue.spring.api.service;

import com.devwue.spring.api.event.devwue.KafkaEvent;
import com.devwue.spring.api.event.devwue.MemberPoint;
import com.devwue.spring.api.model.MemberUser;
import com.devwue.spring.api.repository.UserRepository;
import com.devwue.spring.api.support.JwtUtil;
import com.devwue.spring.dto.MemberRequest;
import com.devwue.spring.dto.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class MemberService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private KafkaService kafkaService;

    public User signup(MemberRequest memberRequest) {
        User user = new User();
        user.setEmail(memberRequest.getEmail());
        user.setPassword(passwordEncoder.encode(memberRequest.getPassword()));
        user = userRepository.save(user);
        return user;
    }

    public HashMap<String, Object> login(MemberRequest memberRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(memberRequest.getEmail(), memberRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            KafkaEvent kafkaEvent = KafkaEvent.builder()
                    .email(memberRequest.getEmail())
                    .createdAt(LocalDateTime.now().toString())
                    .build();

            kafkaService.produceMessage("test", kafkaEvent);
            MemberUser user = (MemberUser) authentication.getPrincipal();

            MemberPoint memberPoint = MemberPoint.builder()
                    .userId(user.getUserId())
                    .pointType("login")
                    .point(100)
                    .build();
            kafkaService.produceMessage("point", memberPoint);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", jwtUtil.generateToken(memberRequest.getEmail()));

        return map;
    }
}
