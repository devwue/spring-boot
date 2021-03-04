package com.devwue.spring.api.controller.service.blog;

import com.devwue.spring.api.service.MemberService;
import com.devwue.spring.dto.MemberRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MainController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    public Object signup(@RequestBody MemberRequest memberRequest) {
        return memberService.signup(memberRequest);
    }

    @PostMapping("/login")
    public Object login(@RequestBody MemberRequest memberRequest) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", memberService.login(memberRequest));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
