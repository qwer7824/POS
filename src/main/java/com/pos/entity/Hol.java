package com.pos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Hol {
    @Id
    @Column(name = "hol_id")
    int id;

    private LocalDateTime firstTime;
 }

