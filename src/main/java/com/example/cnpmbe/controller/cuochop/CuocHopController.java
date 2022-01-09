package com.example.cnpmbe.controller.cuochop;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.model.request.cuochop.CuocHopRequest;
import com.example.cnpmbe.model.request.cuochop.DiemDanhRequest;
import com.example.cnpmbe.service.CuocHopService;
import com.example.cnpmbe.service.DiemDanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping(value = "api/v1/cuochop")
public class CuocHopController {
    @Autowired
    private CuocHopService cuocHopService;

    @Autowired
    private DiemDanhService diemDanhService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllCuocHop(@PathParam("keyword") String keyword, Pageable pageable) {
        if (keyword == null)
            keyword = "";

        ResponseEntity<APIResponse> response = cuocHopService.getAllCuocHop(keyword, pageable);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getCuocHopById(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = cuocHopService.getCuocHopById(id);
        return response;
    }

    @PostMapping
    public ResponseEntity<APIResponse> addNewCuocHop(@RequestBody @Valid CuocHopRequest cuocHopRequest) {
        ResponseEntity<APIResponse> response = cuocHopService.addNewCuocHop(cuocHopRequest);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateCuocHop(@PathVariable Long id, @RequestBody @Valid CuocHopRequest cuocHopRequest) {
        ResponseEntity<APIResponse> response = cuocHopService.updateCuocHop(id, cuocHopRequest);
        return response;
    }

    @PostMapping("/{id}/{idHoKhau}")
    public ResponseEntity<APIResponse> themMoiHoKhauVaoCuocHop(@PathVariable Long id, @PathVariable Long idHoKhau) {
        ResponseEntity<APIResponse> response = cuocHopService.themMoiHoKhauVaoCuocHop(id, idHoKhau);
        return response;
    }

    @DeleteMapping("/{id}/{idHoKhau}")
    public ResponseEntity<APIResponse> xoaHoKhauKhoiCuocHop(@PathVariable Long id, @PathVariable Long idHoKhau) {
        ResponseEntity<APIResponse> response = cuocHopService.xoaHoKhauKhoiCuocHop(id, idHoKhau);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteCuocHop(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = cuocHopService.deleteCuocHop(id);
        return response;
    }

    @GetMapping("/thongkecuochop")
    public ResponseEntity<APIResponse> thongKeCuocHop(@PathParam("months") Long months, @PathParam("years") Long years) {
        Instant to = Instant.now();
        Instant from;

        int day = 30;
        if (months != null && months != 0) {
            day = (int)(30*months);
        }
        if (years != null && years != 0) {
            day = (int)(365*years);
        }

        from = to.minus(day, ChronoUnit.DAYS);

        ResponseEntity<APIResponse> response = cuocHopService.thongKeCuocHop(from, to);
        return response;
    }

    @GetMapping("/thongkenguoithamgia")
    public ResponseEntity<APIResponse> thongKeNguoiThamGia(@PathParam("months") Long months, @PathParam("years") Long years) {
        Instant to = Instant.now();
        Instant from;

        int day = 30;
        if (months != null && months != 0) {
            day = (int)(30*months);
        }
        if (years != null && years != 0) {
            day = (int)(365*years);
        }

        from = to.minus(day, ChronoUnit.DAYS);

        ResponseEntity<APIResponse> response = cuocHopService.thongKeNguoiThamGia(from, to);
        return response;
    }

    @GetMapping("/thongkenguoithamgia/{id}")
    public ResponseEntity<APIResponse> thongKeNguoiThamGiaTheoCuocHop(@PathVariable Long id, @PathParam("months") Long months, @PathParam("years") Long years) {
        Instant to = Instant.now();
        Instant from;

        int day = 30;
        if (months != null && months != 0) {
            day = (int)(30*months);
        }
        if (years != null && years != 0) {
            day = (int)(365*years);
        }

        from = to.minus(day, ChronoUnit.DAYS);

        ResponseEntity<APIResponse> response = cuocHopService.getHoKhauThamGiaTheoId(id, from, to);
        return response;
    }

    @PostMapping("/diemdanh")
    public ResponseEntity<APIResponse> diemDanhCuocHop(@RequestBody @Valid DiemDanhRequest diemDanhRequest) {
        ResponseEntity<APIResponse> response = diemDanhService.diemDanhCuocHop(diemDanhRequest);
        return response;
    }

    @GetMapping("/{id}/diemdanh")
    public ResponseEntity<APIResponse> getAllDiemDanhTheoCocHop(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = diemDanhService.getAllDiemDanhByCuocHop(id);
        return response;
    }





}
