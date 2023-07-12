package com.pos.controller;

import com.pos.entity.Hol;
import com.pos.entity.SaleCart;
import com.pos.repository.HolRepository;
import com.pos.service.HolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HolController {
    private final HolService holService;

    @GetMapping(value = "/")
    public String main(Model model){
        List<Hol> hols = holService.holList();
        int size = hols.size();
        model.addAttribute("hols",hols);
        model.addAttribute("size",size);
        return "main";
    }

    @PostMapping("/addHol")
    public String AddHol(){
        holService.addHol();
        return "redirect:/";
    }
    @PostMapping("/deleteHol/{hid}")
    public String deleteHol(@PathVariable Long hid){
        holService.deleteHol(hid);
        return "redirect:/";
    }
}
