package com.example.cnpmbe.model.entity.nhankhau;

import com.example.cnpmbe.model.request.nhankhau.NhanKhauRequest;
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
@Table(name = "nhan_khau")
public class NhanKhau {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nhan_khau_id_seq")
    @SequenceGenerator(
            name = "nhan_khau_id_seq",
            sequenceName = "nhan_khau_id_seq",
            allocationSize = 1
    )
    private Long id;

    private String hoVaTen;

    private String tenKhac;

    private Instant ngaySinh;

    private String gioiTinh;

    private String cccd;

    private String soHoChieu;

    @Size(max = 65536)
    private String nguyenQuan;

    private String danToc;

    private String tonGiao;

    private String quocTich;

    @Size(max = 65536)
    private String noiThuongTru;

    @Size(max = 65536)
    private String diaChiHienTai;

    private String trinhDoHocVan;

    private String ngheNghiep;

    @Size(max = 65536)
    private String noiLamViec;

    private String quanHeVoiChuHo;

    private Boolean isChuHo = false;

    public NhanKhau(NhanKhauRequest nhanKhauRequest) {
        hoVaTen = nhanKhauRequest.getHoVaTen();
        tenKhac = nhanKhauRequest.getTenKhac();
        ngaySinh = nhanKhauRequest.getNgaySinh();
        gioiTinh = nhanKhauRequest.getGioiTinh();
        cccd = nhanKhauRequest.getCccd();
        soHoChieu = nhanKhauRequest.getSoHoChieu();
        nguyenQuan = nhanKhauRequest.getNguyenQuan();
        danToc = nhanKhauRequest.getDanToc();
        tonGiao = nhanKhauRequest.getTonGiao();
        quocTich = nhanKhauRequest.getQuocTich();
        noiThuongTru = nhanKhauRequest.getNoiThuongTru();
        diaChiHienTai = nhanKhauRequest.getDiaChiHienTai();
        trinhDoHocVan = nhanKhauRequest.getTrinhDoHocVan();
        ngheNghiep = nhanKhauRequest.getNgheNghiep();
        noiLamViec = nhanKhauRequest.getNoiLamViec();
        quanHeVoiChuHo = nhanKhauRequest.getQuanHeVoiChuHo();
        isChuHo = nhanKhauRequest.getIsChuHo();
    }

    public void update(NhanKhauRequest nhanKhauRequest) {
        hoVaTen = nhanKhauRequest.getHoVaTen();
        tenKhac = nhanKhauRequest.getTenKhac();
        ngaySinh = nhanKhauRequest.getNgaySinh();
        gioiTinh = nhanKhauRequest.getGioiTinh();
        cccd = nhanKhauRequest.getCccd();
        soHoChieu = nhanKhauRequest.getSoHoChieu();
        nguyenQuan = nhanKhauRequest.getNguyenQuan();
        danToc = nhanKhauRequest.getDanToc();
        tonGiao = nhanKhauRequest.getTonGiao();
        quocTich = nhanKhauRequest.getQuocTich();
        noiThuongTru = nhanKhauRequest.getNoiThuongTru();
        diaChiHienTai = nhanKhauRequest.getDiaChiHienTai();
        trinhDoHocVan = nhanKhauRequest.getTrinhDoHocVan();
        ngheNghiep = nhanKhauRequest.getNgheNghiep();
        noiLamViec = nhanKhauRequest.getNoiLamViec();
        quanHeVoiChuHo = nhanKhauRequest.getQuanHeVoiChuHo();
        isChuHo = nhanKhauRequest.getIsChuHo();
    }
}
