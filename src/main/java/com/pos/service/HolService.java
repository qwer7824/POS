package com.pos.service;

import com.pos.entity.Hol;
import com.pos.entity.SaleCart;
import com.pos.repository.HolRepository;
import com.pos.repository.SaleCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        List<SaleCart> saleCarts = saleCartRepository.findByHolId(id);

        if (saleCarts.isEmpty()) {
            holRepository.deleteById(id);
        }
    }

    public LocalDateTime findTime(int hid) {
        Optional<Hol> hol = holRepository.findById(hid);
        return hol.get().getFirstTime();
    }
}