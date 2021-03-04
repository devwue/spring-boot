package com.devwue.spring.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String passwordRepeat;
}
