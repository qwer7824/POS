# POS
식당 POS

## 🧐 배경
 - 삼촌께서 식당을 개점하셨는데 포스기 소프트웨어가 아직 준비되지않은 상태였습니다.
 - 수기로 계산을 하셔서 주문 누락과 계산 실수가 있었습니다.
 - 그걸 보고 소프트웨어가 설치 될 때 까지 쓰실 수 있게 구현해드렸습니다.
 - 실제 사용 하였을 때는 테이블 CRD 기능 과 상품 추가 기능만 있었으며 테이블 주문 목록과 계산 기능 만 있어 보여주는 역할만 하였습니다.
 - 후에 판매 내역 과 통계 기능을 추가하였고 Heroku 를 통하여 자동배포를 구현하였습니다.

   1. 계산 기능
   2. 상품 추가
   3. 테이블 추가 , 삭제 기능
   4. 판매 내역
   5. 통계 기능

##  ⚙️ 프로젝트 개발 환경
1. 운영체제 : MAC
2. 통합개발환경(IDE) : IntelliJ IDEA
3. JDK 버전 : JDK 17
4. 스프링 부트 버전 : 3.1.0
5. 데이터 베이스 : MySql
6. 빌드 툴 : Gradle
7. 관리 툴 : GitKraken

## ⚒️ 프로젝트 기술 스택
-   Frontend : HTML, CSS, JavaScript, AJAX, Thymeleaf
-   Backend : Spring Boot, Spring Data JPA
-   Database : MySql
-   OpenAPI : BootStrap , chart.js , Mockaroo
-   Cloud : Heroku

## ⚒️ ERD

<img width="689" alt="스크린샷 2023-10-12 오후 12 00 53" src="https://github.com/qwer7824/POS/assets/8233989/7613a341-b1d0-40da-bccd-e4a67f618ab2">

## 🌍 배포

<p>JawsDB MySQL 무료버전으로 설정 하다보니 속도가 느립니다. 이해부탁드립니다.</p>
<p>DB Max Storage 가 0.005GB 으로 Heroku 잠자기 모드로 변하고 서버가 켜지면 자동으로 초기화가 되게 세팅해놨습니다.</p>

https://app-pos-d77ec85c94c6.herokuapp.com/

<hr>
<h3>테이블 수 조정</h3>

![1_AdobeExpress](https://github.com/qwer7824/POS/assets/8233989/a7e9d38e-33b9-4987-926f-cc516dcb7f09)
```
    public void addHol() {
        int size = holList().size();
        Hol hol = new Hol();
        hol.setId(size+1);
        holRepository.save(hol);
    }

    @Transactional
    public void deleteHol(int id){
        List<SaleCart> saleCarts = saleCartRepository.findByHolId(id);

        if (saleCarts.isEmpty()) {
            holRepository.deleteById(id);
        }
    }
```

<h3>테이블 상품 확인</h3>

<img width="1422" alt="스크린샷 2023-10-11 오후 5 35 38" src="https://github.com/qwer7824/POS/assets/8233989/750810f7-27fd-4091-a3a7-ff47c6385b94">
<p>HolController에서 saleCart 를 내려줌</p>

``` 
        List<SaleCart> saleCart = saleService.findSaleCart();
        model.addAttribute("saleCart", saleCart);
```

<p>main.html 에서 타임리프 if 문법 적용으로 데이터를 넣어줌</p>

```
            <div class="col-4" th:each="hol : ${hols}" th:onclick="|location.href='@{/sale/}'+${hol.getId()}|">
                <p th:text="${hol.getId()} + '번 테이블'"></p>
                <hr>
                <div style="overflow-y: scroll; max-height: calc(100% - 60px);">
                    <div th:each="sale : ${saleCart}" th:if="${sale.hol.id} == ${hol.getId()}">
                        <p th:text="${sale.product.name + ' ' + sale.count + '개 ' + ' ' + (sale.count * sale.product.price)} + '원'"></p>
                    </div>
                </div>
            </div>
```

<h3>테이블에 주문이 있으면 삭제 불가</h3>

![4_AdobeExpress](https://github.com/qwer7824/POS/assets/8233989/f5c51200-7fb6-48a1-b73d-f5cdf2d76b43)

<h3>상품 추가</h3>

![2](https://github.com/qwer7824/POS/assets/8233989/af35d304-0efe-4f6b-b04a-18bbae274939)

<h3>주문 하기</h3>

![3](https://github.com/qwer7824/POS/assets/8233989/75aa592d-b1ed-4d2b-9c5b-e54a044a1ada)

```
@Transactional
public void sale(int hid) {
    int totalCost = 0;

    // 주어진 hid에 해당하는 Hol 엔터니 조회
    Hol hol = holRepository.findById(hid).orElseThrow(null);

    // hid에 해당하는 SaleCart 엔터니들 조회
    List<SaleCart> saleCartList = saleCartRepository.findByHolId(hid);

    // SaleCart 목록을 순회하면서 총 비용 계산
    for (int i = 0; i < saleCartList.size(); i++) {
        Product product = productRepository.findById(saleCartList.get(i).getProduct().getId()).orElseThrow(null);
        totalCost += (product.getPrice() * saleCartList.get(i).getCount());
    }

    // Sale 객체 생성 및 저장
    Sale sale = new Sale(hid, totalCost);
    saleRepository.save(sale);

    // SaleDetail 객체 생성 및 저장
    for (int i = 0; i < saleCartList.size(); i++) {
        Product product = productRepository.findById(saleCartList.get(i).getProduct().getId()).orElseThrow(null);
        SaleDetail saleDetail = new SaleDetail(sale, product,
                product.getPrice(), saleCartList.get(i).getCount());
        saleDetailRepository.save(saleDetail);
    }

   // holId에 해당하는 SaleCarts 삭제
   saleCartRepository.deleteByHolId(hid);

   // hol의 firstTime 필드 초기화(null 설정)
   hol.setFirstTime(null);
}
```


<h3>판매 내역</h3>
<img width="1422" alt="스크린샷 2023-10-11 오후 5 43 42" src="https://github.com/qwer7824/POS/assets/8233989/2f4007c5-a09b-4b18-9c30-b26b9124fc61">
<img width="1383" alt="스크린샷 2023-08-24 오후 4 50 47" src="https://github.com/qwer7824/POS/assets/8233989/932966a0-9b52-4228-b5d3-d63ae2e67cf2">

<h3>매출 통계</h3>

<img width="1422" alt="스크린샷 2023-10-11 오후 5 44 44" src="https://github.com/qwer7824/POS/assets/8233989/4495ebc4-2e27-4200-a395-2515a3408b6c">

```
    @Query("SELECT MONTH(s.createdDate) AS month, SUM(s.price) AS totalPrice, COUNT(s) AS totalOrderCount FROM Sale s GROUP BY MONTH(s.createdDate)")
    List<Map<String, Object>> getMonthlyTotalPrice();

    @Query("SELECT DATE(s.createdDate) AS day, SUM(s.price) AS totalPrice, COUNT(s) AS totalOrderCount FROM Sale s WHERE s.createdDate >= :startDate AND s.createdDate <= :endDate  
    GROUP BY DATE(s.createdDate) ORDER BY DATE(s.createdDate)")
    List<Map<String, Object>> getDailyTotalPrice(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

```
<p>DTO를 활용하여 명확하고 안전한 데이터 전달 및 비즈니스 로직 처리를 하려고했지만 간단한 상황으로 Map을 사용하였습니다.</p>
<p>chart.js 과 ajax 를 이용하여 차트를 구현하였습니다.</p>
