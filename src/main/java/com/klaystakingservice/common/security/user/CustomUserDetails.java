package com.klaystakingservice.common.security.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Getter
@Setter
@Component
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String id;
    private String pwd;
    private String auth;

    private Collection<? extends GrantedAuthority> authorities;

    //사용자에게 부여된 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    //사용자를 인증하는 데 사용하는 암호를 반환
    @Override
    public String getPassword() {
        return null;
    }
    //사용자를 인증하는 데 사용되는 사용자 이름을 반환
    @Override
    public String getUsername() {
        return null;
    }
    //계정이 만료되었는지 여부를 나타낸다
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    //사용자가 잠겨있는지 여부를 나타낸다
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    //사용자의 자격 증명(암호)이 만료되었는지 나타낸다.
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    //사용자의 사용 가능 여부를 나타낸다.
    @Override
    public boolean isEnabled() {
        return false;
    }
}