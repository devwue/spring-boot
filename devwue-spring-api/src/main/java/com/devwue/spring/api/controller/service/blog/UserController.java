package com.devwue.spring.api.controller.service.blog;

import com.devwue.spring.api.service.MemberService;
import com.devwue.spring.api.support.JwtUtil;
import com.devwue.spring.dto.AuthRequest;
import com.devwue.spring.dto.MemberRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/authenticate")
    public Object generateToken(@RequestBody MemberRequest memberRequest) throws Exception {
        return new ResponseEntity<>(memberService.login(memberRequest), HttpStatus.OK);
    }
}
