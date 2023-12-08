package com.pos.service;

import com.pos.exception.CustomException;
import com.pos.exception.CustomExceptionEnum;
import com.pos.entity.*;
import com.pos.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleService {

    private final SaleCartRepository saleCartRepository;
    private final ProductRepository productRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final SaleRepository saleRepository;
    private final HolRepository holRepository;

    public List<SaleCart> saleCartList(int hid) {
        return saleCartRepository.findByHolId(hid);
    }

    public List<SaleCart> findSaleCart(){
        return saleCartRepository.findAll();
    }


    @Transactional
    public void saleCartAdd(int hid,Long pid) {
        Product product = productRepository.findById(pid).orElseThrow(() -> new CustomException(CustomExceptionEnum.PRODUCT_NOT_FOUND));
        SaleCart saleCart = saleCartRepository.findByHolIdAndProductId(hid,pid);
        Hol hol = holRepository.findById(hid).orElseThrow(() -> new CustomException(CustomExceptionEnum.HOL_NOT_FOUND));
        LocalDateTime firstTime = hol.getFirstTime();

        if(saleCart == null){
            if (product.getCount() > 0){
                if (firstTime == null) {
                    hol.timeSet();
                }
                SaleCart newCart = new SaleCart();
                newCart.createSaleCart(hol ,product);
                    product.removeProductCount();
                    saleCartRepository.save(newCart);
            }
        }else {
            if (product.getCount() > 0){
                saleCart.addCount(saleCart.getCount());
                product.removeProductCount();
            }
        }
    }

    @Transactional
    public void deleteSaleCart(int hid, Long pid){
        // get
        SaleCart saleCart = saleCartRepository.findByHolIdAndProductId(hid,pid);
        // product add
        Product product = productRepository.findById(pid).orElseThrow(() -> new CustomException(CustomExceptionEnum.PRODUCT_NOT_FOUND));
        product.updateProduct(saleCart);
        productRepository.save(product);
        //delete
        saleCartRepository.deleteByHolIdAndProductId(hid,pid);
    }

    @Transactional
    public void deleteEASaleCart(int hid, Long pid) {
        SaleCart saleCart = saleCartRepository.findByHolIdAndProductId(hid, pid);
        Product product = productRepository.findById(pid).orElseThrow(() -> new CustomException(CustomExceptionEnum.PRODUCT_NOT_FOUND));

        if (saleCart.getCount() == 1) {
            product.addProductCount();
            saleCartRepository.deleteByHolIdAndProductId(hid, pid);
        } else {
            product.addProductCount();

            saleCart.removeCount(saleCart.getCount());
            saleCartRepository.save(saleCart);
            productRepository.save(product);
        }
    }

    @Transactional
    public void addEASaleCart(int hid, Long pid) {
        SaleCart saleCart = saleCartRepository.findByHolIdAndProductId(hid, pid);
        Product product = productRepository.findById(pid).orElseThrow(() -> new CustomException(CustomExceptionEnum.PRODUCT_NOT_FOUND));

        if (product.getCount() > 0) {
            product.removeProductCount();

            saleCart.addCount(saleCart.getCount());
            saleCartRepository.save(saleCart);
            productRepository.save(product);
        }
    }

    @Transactional
    public void sale(int hid) {
        Hol hol = holRepository.findById(hid)
                .orElseThrow(() -> new CustomException(CustomExceptionEnum.HOL_NOT_FOUND));

        List<SaleCart> saleCartList = saleCartRepository.findByHolId(hid);

        int totalCost = calculateTotalCost(saleCartList);

        Sale sale = new Sale(hid, totalCost);
        saleRepository.save(sale);

        saveSaleDetails(sale, saleCartList);

        saleCartRepository.deleteByHolId(hid);
        hol.timeClear();
    }

    private void saveSaleDetails(Sale sale, List<SaleCart> saleCartList) {
        for (SaleCart saleCart : saleCartList) {
            Product product = productRepository.findById(saleCart.getProduct().getId())
                    .orElseThrow(() -> new CustomException(CustomExceptionEnum.PRODUCT_NOT_FOUND));
            SaleDetail saleDetail = new SaleDetail(sale, product,
                    product.getPrice(), saleCart.getCount());
            saleDetailRepository.save(saleDetail);
        }
    }

    public Map<Integer, Integer> getTotalPrice() {
        List<SaleCart> saleCarts = saleCartRepository.findAll();

        Map<Integer, Integer> resultMap = new HashMap<>();
        for (SaleCart saleCart : saleCarts) {
            int totalPrice = saleCart.getCount() * saleCart.getProduct().getPrice();
            int tableId = saleCart.getHol().getId();

            if (resultMap.containsKey(tableId)) {
                int currentTotal = resultMap.get(tableId);
                resultMap.put(tableId, currentTotal + totalPrice);
            } else {
                resultMap.put(tableId, totalPrice);
            }
        }

        return resultMap;
    }

    public int calculateTotalCost(List<SaleCart> saleCartList) {
        int totalCost = 0;
        for (SaleCart saleCart : saleCartList) {
            Product product = productRepository.findById(saleCart.getProduct().getId())
                    .orElseThrow(() -> new CustomException(CustomExceptionEnum.PRODUCT_NOT_FOUND));
            totalCost += (product.getPrice() * saleCart.getCount());
        }
        return totalCost;
    }
}
