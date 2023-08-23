package com.pos.service;

import com.pos.entity.Sale;
import com.pos.entity.SaleDetail;
import com.pos.repository.SaleDetailRepository;
import com.pos.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleDetailService {

    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;

    public List<Sale> getAllSales(){
        return saleRepository.findAll();
    }

    public List<SaleDetail> getSaleDetailsById(Long sid){
        return saleDetailRepository.findBySid(sid);
    }

    public Sale getSaleById(Long id){
        return saleRepository.findById(id).orElseThrow(null);
    }


    public String totalPrice(int totalPrice){
        DecimalFormat df = new DecimalFormat("###,###");
        String money = df.format(totalPrice);
        return money;
    }
}
