package com.pos.service;

import com.pos.entity.Product;
import com.pos.entity.SaleCart;
import com.pos.repository.ProductRepository;
import com.pos.repository.SaleCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleService {

    private final SaleCartRepository saleCartRepository;
    private final ProductRepository productRepository;

    public List<SaleCart> findByCartByHid(Long hid){
        return saleCartRepository.findByHid(hid);
    }


    @Transactional
    public void addCart(Long hid, Long pid, int count){
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
    public void deleteSaleCart(Long hid, Long pid){
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
    public void deleteEASaleCart(Long hid, Long pid) {
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
    public void addEASaleCart(Long hid, Long pid) {
        SaleCart saleCart = saleCartRepository.findByHidAndPid(hid,pid);
        Product product = productRepository.findById(pid).orElse(null);

        if(product.getCount() == 0){

        }else{
            product.setCount(product.getCount()-1);
            productRepository.save(product);

            SaleCart update = saleCart;
            update.addCount(saleCart.getCount());
            saleCartRepository.save(update);
        }
    }
}
