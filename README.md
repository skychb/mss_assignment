# Kyler Assignment MSS

## 구현 범위에 대한 설명

패션 상품의 가격을 카테고리별로 비교하고, 최저가를 제공하는 API

1. **카테고리별 최저가격 브랜드와 상품 가격, 총액 조회 API**
   - Endpoint: /api/products/lowestPrice
   - Method: GET
   - Description: 각 카테고리에서 최저가격인 브랜드와 가격을 조회하고 총액을 확인합니다.


2. **단일 브랜드로 전체 카테고리 상품을 구매할 때 최저가격 조회 API**
   - Endpoint: /api/brands/lowestPrice
   - Method: GET
   - Description: 단일 브랜드로 모든 카테고리의 상품을 구매할 때 최저가격과 총액을 확인합니다.


3. **특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드 조회 API**
   - Endpoint: /api/categories/price
   - Method: GET
   - Params: category
   - Description: 특정 카테고리에서 최저가격과 최고가격인 브랜드를 조회합니다.


4. **브랜드 및 상품 추가 / 업데이트 / 삭제 API**

   4.1 **상품 추가 API**
   - Endpoint: /api/admin/product
   - Method: POST
   - Description: 새로운 상품을 추가합니다.

   4.2 **상품 업데이트 API**
   - Endpoint: /api/admin/product/{id}
   - Method: PUT
   - Description: 상품 정보를 업데이트합니다.

   4.3 **상품 삭제 API**
   - Endpoint: /api/admin/product/{id}
   - Method: DELETE
   - Description: 상품을 삭제합니다.

## 코드 빌드, 테스트, 실행 방법

### 요구 사항

- IntelliJ IDEA 설치
- Java 17 이상
- Kotlin 1.9 이상
- Gradle 8.8 이상

### 사용 방법

- ./gradlew build
- ./gradlew test
- ./gradlew bootRun