package com.example.cnpmbe.model.entity.nhankhau;

import com.example.cnpmbe.model.request.nhankhau.TamTruRequest;
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
public class TamTru {
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

    public TamTru(TamTruRequest tamTruRequest) {
        hoVaTen = tamTruRequest.getHoVaTen();
        cccd = tamTruRequest.getCccd();
        diaChi = tamTruRequest.getDiaChi();
        tuNgay = tamTruRequest.getTuNgay();
        denNgay = tamTruRequest.getDenNgay();
        lyDo = tamTruRequest.getLyDo();
    }

    public void update(TamTruRequest tamTruRequest) {
        hoVaTen = tamTruRequest.getHoVaTen();
        cccd = tamTruRequest.getCccd();
        diaChi = tamTruRequest.getDiaChi();
        tuNgay = tamTruRequest.getTuNgay();
        denNgay = tamTruRequest.getDenNgay();
        lyDo = tamTruRequest.getLyDo();
    }
}
