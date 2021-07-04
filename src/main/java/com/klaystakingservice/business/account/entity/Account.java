package com.klaystakingservice.business.account.entity;

import com.klaystakingservice.business.account.domain.Address;
import com.klaystakingservice.business.account.enumerated.Role;
import com.klaystakingservice.common.domain.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Account implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long Id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(length = 200, nullable = false)
    private String userKey;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "zipCode", column = @Column(name="zip_code")),
        @AttributeOverride(name = "city", column = @Column(name="city")),
        @AttributeOverride(name = "street", column = @Column(name="street")),
        @AttributeOverride(name = "subStreet", column = @Column(name="sub_street"))
    })
    private Address address;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Account toUpdate(String email, String password, Address address){
        this.email = email;
        this.password = password;
        this.address = address;
        return this;
    }

}
