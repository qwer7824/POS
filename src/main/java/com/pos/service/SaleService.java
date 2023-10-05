package com.pos.service;

import com.pos.entity.*;
import com.pos.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        int count = 1;
        Product product = productRepository.findById(pid).orElseThrow(null);
        SaleCart saleCart = saleCartRepository.findByHolIdAndProductId(hid,pid);
        Hol hol = holRepository.findById(hid).orElseThrow(null);
        LocalDateTime firstTime = hol.getFirstTime();

        if(saleCart == null){
            if (firstTime == null) {
                hol.setFirstTime(LocalDateTime.now());
            }
            saleCart = SaleCart.createSaleCart(hol ,product,count);
            product.setCount(product.getCount() - 1);
            saleCartRepository.save(saleCart);
        }else {
            saleCart.addCount(count);
            product.setCount(product.getCount() - 1);
        }
    }

    @Transactional
    public void deleteSaleCart(int hid, Long pid){
        // get
        SaleCart saleCart = saleCartRepository.findByHolIdAndProductId(hid,pid);
        // product add
        Product product = productRepository.findById(pid).orElse(null);
        product.setCount(product.getCount()+saleCart.getCount());
        productRepository.save(product);
        //delete
        saleCartRepository.deleteByHolIdAndProductId(hid,pid);
    }

    @Transactional
    public void deleteEASaleCart(int hid, Long pid) {
        SaleCart saleCart = saleCartRepository.findByHolIdAndProductId(hid,pid);

        Product product = productRepository.findById(pid).orElse(null);

        if(saleCart.getCount() == 0){
            saleCartRepository.deleteByHolIdAndProductId(hid,pid);
        }else{
            product.setCount(product.getCount()+1);
            productRepository.save(product);

            SaleCart update = saleCart;
            update.removeCount(saleCart.getCount());
            saleCartRepository.save(update);
        }
    }

    @Transactional
    public void addEASaleCart(int hid, Long pid) {
        SaleCart saleCart = saleCartRepository.findByHolIdAndProductId(hid,pid);
        Product product = productRepository.findById(pid).orElseThrow(null);

        if(product.getCount() == 0){

        }else{
            product.setCount(product.getCount()-1);
            productRepository.save(product);

            SaleCart update = saleCart;
            update.addCount(saleCart.getCount());
            saleCartRepository.save(update);
        }
    }

    @Transactional
    public void sale(int hid){
        int totalCost = 0;
        Hol hol = holRepository.findById(hid).orElseThrow(null);
        List<SaleCart> saleCartList = saleCartRepository.findByHolId(hid);

        for(int i=0;i<saleCartList.size();i++){
            Product product = productRepository.findById(saleCartList.get(i).getProduct().getId()).orElseThrow(null);
            totalCost += (product.getPrice()*saleCartList.get(i).getCount());
        }

        Sale sale = new Sale(hol, totalCost);
        saleRepository.save(sale);

        for(int i=0;i<saleCartList.size();i++) {
            Product product = productRepository.findById(saleCartList.get(i).getProduct().getId()).orElseThrow(null);
            SaleDetail saleDetail = new SaleDetail(sale, product,
                    product.getPrice(), saleCartList.get(i).getCount());
            saleDetailRepository.save(saleDetail);
        }
        saleCartRepository.deleteByHolId(hid);
        hol.setFirstTime(null);
    }

    public int calculateTotalCost(List<SaleCart> saleCart) {
        int totalCost = 0;

        for (SaleCart item : saleCart) {
            totalCost += item.getProduct().getPrice() * item.getCount();
        }

        return totalCost;
    }
}
