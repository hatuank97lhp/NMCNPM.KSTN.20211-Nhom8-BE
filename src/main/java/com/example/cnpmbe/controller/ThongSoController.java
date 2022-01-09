package com.example.cnpmbe.controller;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.model.response.ThongSoQuanLy;
import com.example.cnpmbe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1")
public class ThongSoController {
    @Autowired
    private HoatDongService hoatDongService;

    @Autowired
    private NhanKhauService nhanKhauService;

    @Autowired
    private HoKhauService hoKhauService;

    @Autowired
    private TamTruService tamTruService;

    @Autowired
    private TamVangService tamVangService;

    @GetMapping("/hoatdong")
    public ResponseEntity<APIResponse> getHoatDong(Pageable pageable) {
        ResponseEntity<APIResponse> response = hoatDongService.getAll(pageable);
        return response;
    }

    @GetMapping("/thongso")
    public ResponseEntity<APIResponse> getThongSo() {
        long soHoKhau = hoKhauService.getSoLuongHoKhau();
        long soNhanKhau = nhanKhauService.getSoLuongNhanKhau();
        long soTamTru = tamTruService.getSoLuongTamTru();
        long soTamVang = tamVangService.getSoLuongTamVang();

        ThongSoQuanLy thongSoQuanLy = new ThongSoQuanLy();
        thongSoQuanLy.setSoHoKhau(soHoKhau);
        thongSoQuanLy.setSoTamTru(soTamTru);
        thongSoQuanLy.setSoNhanKhau(soNhanKhau);
        thongSoQuanLy.setSoTamVang(soTamVang);

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, thongSoQuanLy));
    }
}
