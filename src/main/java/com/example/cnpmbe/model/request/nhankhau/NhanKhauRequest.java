package com.example.cnpmbe.model.request.nhankhau;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class NhanKhauRequest {
    private String hoVaTen = "";

    private String tenKhac = "";

    private Instant ngaySinh;

    private String gioiTinh = "";

    private String cccd = "";

    private String soHoChieu = "";

    @Size(max = 65536)
    private String nguyenQuan = "";

    private String danToc = "";

    private String tonGiao = "";

    private String quocTich = "";

    @Size(max = 65536)
    private String noiThuongTru = "";

    @Size(max = 65536)
    private String diaChiHienTai = "";

    private String trinhDoHocVan = "";

    private String ngheNghiep = "";

    @Size(max = 65536)
    private String noiLamViec = "";

    private String quanHeVoiChuHo = "";

    private Boolean isChuHo = false;
}
