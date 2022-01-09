package com.example.cnpmbe.model.response;

import com.example.cnpmbe.model.entity.cuochop.CuocHop;
import com.example.cnpmbe.model.entity.hokhau.HoKhau;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CuocHopSimpleResponse {
    private Long id;

    private String tieuDe;

    private Instant thoiGian;

    private String diaDiem;

    private String noiDung;

    private String banBaoCao;

    private String nguoiTao;

    private List<HoKhauSimpleResponse> hoKhaus = new ArrayList<>();

    public CuocHopSimpleResponse(CuocHop cuocHop) {
        id = cuocHop.getId();
        tieuDe = cuocHop.getTieuDe();
        thoiGian = cuocHop.getThoiGian();
        diaDiem = cuocHop.getDiaDiem();
        noiDung = cuocHop.getNoiDung();
        banBaoCao = cuocHop.getBanBaoCao();
        nguoiTao = cuocHop.getNguoiTao();

        for (HoKhau hoKhau: cuocHop.getHoKhaus()) {
            hoKhaus.add(new HoKhauSimpleResponse(hoKhau));
        }
    }
}
