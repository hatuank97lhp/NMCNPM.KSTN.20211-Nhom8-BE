package com.example.cnpmbe.model.response;

import com.example.cnpmbe.model.entity.hokhau.HoKhau;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HoKhauSimpleResponse {
    private Long id;

    private String hoTenChuHo;

    public HoKhauSimpleResponse(HoKhau hoKhau) {
        id = hoKhau.getId();
        hoTenChuHo = hoKhau.getHoTenChuHo();
    }
}
