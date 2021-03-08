package com.devwue.spring.api.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class MemberUser extends User {
    private final Long userId;

    public MemberUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities,
                      Long userId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    public MemberUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(username, password, true, true, true, true, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
