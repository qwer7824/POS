package com.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SaleCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int hid;
    private Long pid;
    private int count;

    public void removeCount(int count){
        this.count = count - 1;
    }
    public void addCount(int count){
        this.count = count + 1;
    }
}
