package com.example.cnpmbe.model.response;

import com.example.cnpmbe.model.entity.cuochop.DiemDanh;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiemDanhSimpleResponse {
    private Long hoKhau;

    private Long cuocHop;

    private Boolean diemDanh = true;

    private String lyDo = "";

    private String hoTenChuHo = "";

    public DiemDanhSimpleResponse(DiemDanh diemDanhX) {
        hoKhau = diemDanhX.getHoKhau().getId();
        cuocHop = diemDanhX.getCuocHop().getId();
        diemDanh = diemDanhX.getDiemDanh();
        lyDo = diemDanhX.getLyDo();
        hoTenChuHo = diemDanhX.getHoKhau().getHoTenChuHo();
    }
}
