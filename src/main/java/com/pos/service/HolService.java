package com.pos.service;

import com.pos.entity.Hol;
import com.pos.entity.SaleCart;
import com.pos.repository.HolRepository;
import com.pos.repository.SaleCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HolService {

    private final HolRepository holRepository;
    private final SaleCartRepository saleCartRepository;

    public List<Hol> holList() {
        return holRepository.findAll();
    }

    public void addHol() {
        int size = holList().size();
        Hol hol = new Hol();
        hol.setId(size+1);
        holRepository.save(hol);
    }

    @Transactional
    public void deleteHol(int id){
        List<SaleCart> saleCarts = saleCartRepository.findByHid(id);

        if (saleCarts.isEmpty()) {
            holRepository.deleteById(id);
        } else {

        }
    }
}