package com.pos.service;

import com.pos.entity.*;
import com.pos.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleService {

    private final SaleCartRepository saleCartRepository;
    private final ProductRepository productRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final SaleRepository saleRepository;

    public List<SaleCart> findByCartByHid(int hid){
        return saleCartRepository.findByHid(hid);
    }


    @Transactional
    public void addCart(int hid, Long pid, int count){
        SaleCart saleCart = saleCartRepository.findByHidAndPid(hid,pid);
        if(saleCart==null){
        saleCartRepository.save(saleCart.builder()
                        .hid(hid)
                        .pid(pid)
                        .count(count)
                .build());
        }
        else{
            SaleCart update = saleCart;
            int _count = saleCart.getCount();
            update.setCount(_count+count);
            saleCartRepository.save(saleCart);
        }

        Product product = productRepository.findById(pid).orElse(null);
        product.setCount(product.getCount()-count);
        productRepository.save(product);
    }

    @Transactional
    public void deleteSaleCart(int hid, Long pid){
        // get
        SaleCart saleCart = saleCartRepository.findByHidAndPid(hid,pid);
        // product add
        Product product = productRepository.findById(pid).orElse(null);
        product.setCount(product.getCount()+saleCart.getCount());
        productRepository.save(product);
        //delete
        saleCartRepository.deleteByHidAndPid(hid,pid);
    }

    @Transactional
    public void deleteEASaleCart(int hid, Long pid) {
        SaleCart saleCart = saleCartRepository.findByHidAndPid(hid,pid);

        Product product = productRepository.findById(pid).orElse(null);

        if(saleCart.getCount() == 0){
            saleCartRepository.deleteByHidAndPid(hid,pid);
        }else{
            product.setCount(product.getCount()+1);
            productRepository.save(product);

            SaleCart update = saleCart;
            update.removeCount(saleCart.getCount());
            log.info("count{}",saleCart.getCount());
            saleCartRepository.save(update);
        }
    }

    @Transactional
    public void addEASaleCart(int hid, Long pid) {
        SaleCart saleCart = saleCartRepository.findByHidAndPid(hid,pid);
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
        LocalDateTime currentDateTime = LocalDateTime.now();
        int totalCost = 0;
        List<SaleCart> saleCartList = saleCartRepository.findByHid(hid);
        System.out.println(saleCartList);

        for(int i=0;i<saleCartList.size();i++){
            Product product = productRepository.findById(saleCartList.get(i).getPid()).orElseThrow(null);
            totalCost += (product.getPrice()*saleCartList.get(i).getCount());
        }

        Sale sale = new Sale(hid, totalCost, currentDateTime);
        saleRepository.save(sale);

        for(int i=0;i<saleCartList.size();i++) {
            Product product = productRepository.findById(saleCartList.get(i).getPid()).orElseThrow(null);
            SaleDetail saleDetail = new SaleDetail(sale.getId(), product.getId(), product.getName(),
                    product.getPrice(), saleCartList.get(i).getCount());
            saleDetailRepository.save(saleDetail);
        }
        saleCartRepository.deleteByHid(hid);
    }
}
