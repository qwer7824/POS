package com.pos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
public class Hol {
    @Id
    @Column(name = "hol_id")
    int id;

    private LocalDateTime firstTime;

    public void addHol(int size){
        this.id = size+1;
    }
    public void timeSet(){
        this.firstTime = LocalDateTime.now();
    }
    public void timeClear(){
        this.firstTime = null;
    }

 }

