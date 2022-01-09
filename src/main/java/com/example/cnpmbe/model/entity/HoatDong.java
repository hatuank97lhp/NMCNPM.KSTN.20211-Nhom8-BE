package com.example.cnpmbe.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "hoat_dong")
@Entity
public class HoatDong {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hoat_dong_id_seq")
    @SequenceGenerator(
            name = "hoat_dong_id_seq",
            sequenceName = "hoat_dong_id_seq",
            allocationSize = 1
    )
    private Long id;

    private Instant time;

    private String mess;

    public HoatDong(String mes) {
        time = Instant.now();
        mess = mes;
    }
}
