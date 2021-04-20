package com.klaystakingservice.business.account.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.enumerated.Role;
import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.common.response.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    //loadUserByUsernae 메소드는 입력한 account를 이용해 회원을 조회합니다.
    // 그리고 회원 정보와 권한 정보가 담긴 User 클래스를 반환합니다.
    // (User 클래스는 UserDetails 인터페이스를 구현하고 있습니다.)
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Member> memberEntityWrapper = memberRespository.findByEmail(email);
//        Member memberEntity = memberEntityWrapper.orElseThrow(() -> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
//
//        return new User(memberEntity.getEmail(), memberEntity.getPassword(), authorities);
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountWrapper = accountRepository.findByEmail(email);
        Account account = accountWrapper.orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
//        Account account = accountWrapper.orElseThrow(() -> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return User.builder()
                   .username(account.getEmail())
                   .password(account.getPassword())
                   .authorities(authorities)
                   .build();
    }

    @Transactional
    public ResponseEntity<?> save(AccountForm.Request.AccountDTO accountDTO) {

        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = Account.builder()
                .email(accountDTO.getEmail())
                .password(passwordEncoder.encode(accountDTO.getPassword()))
                .address(accountDTO.getAddress())
                .role(Role.USER)
                .build();

        accountRepository.save(account);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Location", "/")
                .body(Response.message("정상적으로 회원가입 되셨습니다."));
    }
}
