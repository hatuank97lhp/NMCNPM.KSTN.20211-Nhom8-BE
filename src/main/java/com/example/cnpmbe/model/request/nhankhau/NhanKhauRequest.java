package com.example.cnpmbe.model.request.nhankhau;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class NhanKhauRequest {
    @Size(min = 1)
    private String hoVaTen;

    private String tenKhac = "";

    @NotNull
    private Instant ngaySinh;

    @Size(min = 1)
    private String gioiTinh = "";

    private String cccd = "";

    private String soHoChieu = "";

    @Size(min = 1, max = 65536)
    private String nguyenQuan = "";

    @Size(min = 1)
    private String danToc = "";

    @Size(min = 1)
    private String tonGiao = "";

    @Size(min = 1)
    private String quocTich = "";

    @Size(min = 1, max = 65536)
    private String noiThuongTru = "";

    @Size(min = 1, max = 65536)
    private String diaChiHienTai = "";

    private String trinhDoHocVan = "";

    private String ngheNghiep = "";

    @Size(max = 65536)
    private String noiLamViec = "";

    @Size(min = 1)
    private String quanHeVoiChuHo = "";

    private Boolean isChuHo = false;
}
