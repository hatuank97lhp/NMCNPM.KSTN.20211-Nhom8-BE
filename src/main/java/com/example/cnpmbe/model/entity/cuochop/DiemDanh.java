package com.example.cnpmbe.model.entity.cuochop;

import com.example.cnpmbe.model.entity.hokhau.HoKhau;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "diem_danh")
public class DiemDanh {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diem_danh_id_seq")
    @SequenceGenerator(
            name = "diem_danh_id_seq",
            sequenceName = "diem_danh_id_seq",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "diemdanh_hokhau",
            joinColumns = @JoinColumn(name = "diem_danh_id"),
            inverseJoinColumns = @JoinColumn(name = "ho_khau_id")
    )
    private HoKhau hoKhau;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "diemdanh_cuochop",
            joinColumns = @JoinColumn(name = "diem_danh_id"),
            inverseJoinColumns = @JoinColumn(name = "cuoc_hop_id")
    )
    private CuocHop cuocHop;

    private Boolean diemDanh = true;

    private String lyDo = "";
}
