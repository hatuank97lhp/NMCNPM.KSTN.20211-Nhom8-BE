package com.example.cnpmbe.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HoKhauThamGia {
    private Long id;

    private String hoTenChuHo;

    private String diaChi;

    private long thamGia = 0L;

    private long vangCoLyDo = 0L;

    private long vangKhongLyDo = 0L;

}
