package com.example.cnpmbe.model.response;

import com.example.cnpmbe.model.entity.cuochop.CuocHop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class CuocHopEzResponse {
    private Long id;

    private Instant thoiGian;

    private String tieuDe;

    private String diaDiem;

    public CuocHopEzResponse(CuocHop cuocHop) {
        id = cuocHop.getId();
        thoiGian = cuocHop.getThoiGian();
        tieuDe = cuocHop.getTieuDe();
        diaDiem = cuocHop.getDiaDiem();
    }
}
