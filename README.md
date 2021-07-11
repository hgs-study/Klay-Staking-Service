## 클레이 스테이킹 웹 서비스

### 요구사항
```
  - 회원가입 시 자동 지갑 생성 및 생성 직후 0.1 Klay 지급
  - 회원 간 / 외부 지갑에 클레이, 토큰 전송 기능
  - 사용한 API, 트랜잭션 확인
  - 스테이킹 상품 제공 (30일 동안 00:00시마다 일정 수량 Klay 지급)
```


### 개발 & 배포환경
```
  - Spring Boot
  - Spring Security + JWT
  - Spring Batch
  - Data JPA
  - Thymeleaf
  - H2 (Local)
  - MariaDB (Production)
  - Redis
  - AWS
  - Docker
  - Jenkins
  - Swagger
  - Klaytn API
```

<br/>

### 프로젝트 일정체크
----
github : [Schedule.md](https://github.com/hgs-study/DailyStudy/blob/main/Schedule/Schedule.md)

<br/>

### POC 프로젝트
----
github : [Klaytn-API](https://github.com/hgs-study/Klaytn-API)

※ 이더리움 노드는 Rest API로 직접 접근할 수 없으나, KAS는 이를 제공해주기 때문에 메인 기능인 Klaytn API가 프로젝트에 실현 가능한지, 구체적인 기능을 검증하며 불확실한 요소를 지우는 것이 목표로 Klaytn-API 선행한 프로젝트.  

<br/>

### Pilot 프로젝트
------
github : [Batch & Scheduler](https://github.com/hgs-study/Batch-Scheduler-Basic)

※ 스테이킹 상품을 구매한 회원에게 매일 0:00시에 Klay를 지급하기 위해서 Spring Batch와 Scheduler를 선행한 프로젝트. 실제 운용하기 전에 오류와 검증을 거치기 위해 시험 가동한 프로젝트

<br/>

### ERD
------
![ERD-08](https://user-images.githubusercontent.com/76584547/117684495-00e1d680-b1f0-11eb-968e-fe91d6fce9ca.png)
※ ERD는 초기 모델링을 위해 미리 잡아놓았기 때문에 데이터 타입, 도메인 등 추후에 계속 업데이트 할 예정

<br/>

### To-Do List 
------
- Wallet (지갑)
- [x] 지갑 생성
- [x] 지갑 조회

- Transaction
- [x] 토큰 전송
- [ ] 토큰 전송 조회
- [x] 토큰 조회

- User
- [x] 유저 등록
- [x] 유저 삭제
- [x] 유저 조회
- [x] 유저 수정

- Staking
- [X] 상품 등록
- [X] 상품 삭제
- [X] 상품 조회
- [X] 상품 수정
- [X] 30일 동안 00:00시 0.01 Klay 지급

- Order
- [X] 주문 등록
- [X] 주문 조회

- Ordered Product
- [X] 주문 상품 등록
- [ ] 주문 상품 삭제
- [X] 주문 상품 조회
- [ ] 주문 상품 수정

- Klay API
- [x] API 등록
- [ ] API 조회
- [ ] API 상세 조회

