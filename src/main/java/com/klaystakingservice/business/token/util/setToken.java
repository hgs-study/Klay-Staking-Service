//package com.klaystakingservice.business.token.util;
//
//import com.klaystakingservice.business.token.application.TokenRepository;
//import com.klaystakingservice.business.token.entity.Token;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class setToken implements CommandLineRunner {
//
//    private final TokenRepository tokenRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        tokenRepository.save(Token.builder()
//                                  .name("KLAY")
//                                  .type("KLAY")
//                                  .contractAddress("")
//                                  .build());
//
//        tokenRepository.save(Token.builder()
//                                  .name("TestCoin")
//                                  .type("ERC-20")
//                                  .contractAddress("0x1234567894564654561561651")
//                                  .build());
//    }
//}
