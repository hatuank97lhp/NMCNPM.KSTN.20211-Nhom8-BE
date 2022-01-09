package com.example.cnpmbe.model.entity.hokhau;

import com.example.cnpmbe.model.entity.nhankhau.NhanKhau;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ho_khau")
public class HoKhau {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ho_khau_id_seq")
    @SequenceGenerator(
            name = "ho_khau_id_seq",
            sequenceName = "ho_khau_id_seq",
            allocationSize = 1
    )
    private Long id;

    private String hoTenChuHo;

    private String cccdChuHo;

    @Size(max = 65536)
    private String diaChi;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "hokhau_nhankhau",
        joinColumns = @JoinColumn(name = "ho_khau_id"),
        inverseJoinColumns = @JoinColumn(name = "nhan_khau_id")
    )
    private List<NhanKhau> nhanKhaus = new ArrayList<>();
}
