# spring-gift-product

## 1단계 (step1)
선물하기 서비스에서 상품을 조회, 추가, 수정, 삭제할 수 있는 HTTP API를 구현한다.

1. 상품정보에 대한 내용을 담는다: Product 클래스
2. HTTP요청받고 JSON형식으로 응답 반환하는 ProductController
- 상품추가 : Post
- 상품조회 : Get
- 상품수정 : Put
- 상품삭제 : Delete
3. 주요 기능 4개에 대한 test코드 : ProductControllerTest.java

## 2단계 (step2)
선물하기 서비스에서 상품을 조회, 추가, 수정, 삭제할 수 있는 관리자 화면을 구현한다.

1. WebController.java
2. 웹페이지 구현 - Thymeleaf 템플릿
- 상품목록: index.html
- 상품추가: new.html
- 상품수정: edit.html
3. Thymeleaf 템플릿에 대한 test코드: WebControllerTest.java

## 3단계 (step3)
### 상품옵션 추가하기
- 상품옵션 추가, 수정, 삭제 구현
- Option.java 및 Thymeleaf 수정

### jdbcTemplate 이용한 데이터베이스 적용하기
1. application.properties로 H2 데이터베이스 설정
2. schema.sql, data.sql 작성
3. JdbcTemplate 사용해 데이터베이스와 연동
- 데이터베이스와 상호작용 : ProductRepository.java
- ProductRepository 인터페이스 구현 : JdbcProductRepository
- 메모리 저장소 대신 JdbcProductRepository 사용하도록 ProductController.java 수정
