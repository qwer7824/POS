package com.pos.controller;

import com.pos.service.HolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestHolController {

    private final HolService holService;

    @DeleteMapping("/deleteHol/{hid}")
    public ResponseEntity deleteHol(@PathVariable int hid){
        holService.deleteHol(hid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/addHol")
    public ResponseEntity AddHol(){
        holService.addHol();
        return new ResponseEntity(HttpStatus.OK);
    }
}
