package com.example.cnpmbe.model.entity.nhankhau;


import com.example.cnpmbe.model.request.nhankhau.TamVangRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tam_tru")
public class TamVang {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tam_tru_id_seq")
    @SequenceGenerator(
            name = "tam_tru_id_seq",
            sequenceName = "tam_tru_id_seq",
            allocationSize = 1
    )
    private Long id;

    private String hoVaTen;

    private String cccd;

    @Size(max = 65536)
    private String diaChi;

    private Instant tuNgay;

    private Instant denNgay;

    @Size(max = 65536)
    private String lyDo;

    public TamVang(TamVangRequest tamVangRequest) {
        hoVaTen = tamVangRequest.getHoVaTen();
        cccd = tamVangRequest.getCccd();
        diaChi = tamVangRequest.getDiaChi();
        tuNgay = tamVangRequest.getTuNgay();
        denNgay = tamVangRequest.getDenNgay();
        lyDo = tamVangRequest.getLyDo();
    }

    public void update(TamVangRequest tamVangRequest) {
        hoVaTen = tamVangRequest.getHoVaTen();
        cccd = tamVangRequest.getCccd();
        diaChi = tamVangRequest.getDiaChi();
        tuNgay = tamVangRequest.getTuNgay();
        denNgay = tamVangRequest.getDenNgay();
        lyDo = tamVangRequest.getLyDo();
    }
}
