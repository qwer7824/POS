package com.pos.service;

import com.pos.dto.SearchForm;
import com.pos.entity.Sale;
import com.pos.entity.SaleDetail;
import com.pos.repository.SaleDetailRepository;
import com.pos.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleDetailService {

    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;

    public List<Sale> getSearchSales(SearchForm searchForm){
        LocalDate start = searchForm.getStart();
        LocalDate end = searchForm.getEnd();
        return saleRepository.findAllByCreatedDateBetween(start,end);
    }

    public List<Sale> getToDaySales(){
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        return saleRepository.findAllByCreatedDateBetween(start,end);
    }


    public List<SaleDetail> getSaleDetailsById(Long sid){
        return saleDetailRepository.findBySaleId(sid);
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
