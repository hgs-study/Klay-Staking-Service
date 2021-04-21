# 클레이 스테이킹 웹 서비스

## 요구사항
```
  - 회원가입 시 자동 지갑 생성 및 생성 직후 0.1 Klay 지급
  - 회원 간 / 외부 지갑에 클레이, 토큰 전송 기능
  - 사용한 API, 트랜잭션 확인
  - 스테이킹 상품 제공 (30일 동안 00:00시마다 일정 수량 Klay 지급)
```

## 개발 & 배포환경
```
  - Spring Boot
  - Spring Security
  - Data JPA
  - Thymeleaf
  - H2 (Local)
  - MariaDB (Production)
  - Redis
  - AWS
  - Docker
  - Jenkins
  - Klaytn API
  - Klaytn Baobab Network
```
## POC 프로젝트
github : [Klaytn-API](https://github.com/hgs-study/Klaytn-API)

※ 이더리움 노드는 Rest API로 직접 접근할 수 없으나, KAS는 이를 제공해주기 때문에 메인 기능인 Klaytn API가 프로젝트에 실현 가능한지, 구체적인 기능을 검증하며 불확실한 요소를 지우는 것이 목표로 Klaytn-API 선행한 프로젝트. 


## ERD
![ERD-03](https://user-images.githubusercontent.com/76584547/115574476-a0502f80-a2fc-11eb-8ba4-3d5b7d2654b6.png)
※ ERD는 초기 모델링을 위해 미리 잡아놓았기 때문에 데이터 타입, 도메인 등 추후에 계속 업데이트 할 예정


