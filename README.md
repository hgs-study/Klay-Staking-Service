# 클레이 스테이킹 웹 서비스

## 요구사항
```
  - 회원가입 시 자동 지갑 생성 및 생성 직후 0.1 Klay 지급
  - 회원 간 / 외부 지갑에 클레이, 토큰 전송 기능
  - 사용한 API, 트랜잭션 확인
  - 스테이킹 상품 제공 (하루 00:00시마다 일정 수량 Klay 지급)
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

## ERD
![ERD-01](https://user-images.githubusercontent.com/76584547/115104865-554db980-9f96-11eb-9076-4dfd9fa908af.png)
※ ERD는 초기 모델링을 위해 미리 잡아놓았기 때문에 데이터 타입, 도메인 등 추후에 계속 업데이트 할 예정

