package com.example.cnpmbe.service;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.enums.ExceptionMessages;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.model.entity.cuochop.CuocHop;
import com.example.cnpmbe.model.entity.cuochop.DiemDanh;
import com.example.cnpmbe.model.entity.hokhau.HoKhau;
import com.example.cnpmbe.model.entity.nhankhau.NhanKhau;
import com.example.cnpmbe.model.request.hokhau.HoKhauRequest;
import com.example.cnpmbe.repository.CuocHopRepository;
import com.example.cnpmbe.repository.DiemDanhRepository;
import com.example.cnpmbe.repository.HoKhauRepository;
import com.example.cnpmbe.repository.NhanKhauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class HoKhauService {
    @Autowired
    private HoKhauRepository hoKhauRepository;

    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private HoatDongService hoatDongService;

    @Autowired
    private CuocHopRepository cuocHopRepository;

    @Autowired
    private DiemDanhRepository diemDanhRepository;

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getAllHoKhau(String keyword, Pageable pageable) {
        Page<HoKhau> hoKhaus = hoKhauRepository.getAllByHoTenChuHoContainsOrCccdChuHoContains(keyword, keyword, pageable);

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, hoKhaus));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getHoKhauById(Long id) {
        Optional<HoKhau> hoKhauOptional = hoKhauRepository.findById(id);
        if (!hoKhauOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.HO_KHAU_ID_NOT_FOUND));

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, hoKhauOptional.get()));
    }

    @Transactional
    public ResponseEntity<APIResponse> addNewHoKhau(HoKhauRequest hoKhauRequest) {
        HoKhau hoKhau = new HoKhau();
        hoKhau.setHoTenChuHo(hoKhauRequest.getHoTenChuHo());
        hoKhau.setCccdChuHo(hoKhauRequest.getCccdChuHo());
        hoKhau.setDiaChi(hoKhauRequest.getDiaChi());

        for (Long nhanKhauId: hoKhauRequest.getNhanKhaus()) {
            Optional<NhanKhau> nhanKhau = nhanKhauRepository.findById(nhanKhauId);
            if (nhanKhau.isPresent()) {
                hoKhau.getNhanKhaus().add(nhanKhau.get());
            }
        }

        hoKhauRepository.save(hoKhau);

        hoatDongService.createrNew("Thêm mới hộ khẩu: " + hoKhau.getHoTenChuHo());
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, hoKhau));
    }

    @Transactional
    public ResponseEntity<APIResponse> updateHoKhau(Long id, HoKhauRequest hoKhauRequest) {
        Optional<HoKhau> hoKhauOptional = hoKhauRepository.findById(id);
        if (!hoKhauOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.HO_KHAU_ID_NOT_FOUND));

        HoKhau hoKhau = hoKhauOptional.get();
        hoKhau.setHoTenChuHo(hoKhauRequest.getHoTenChuHo());
        hoKhau.setCccdChuHo(hoKhauRequest.getCccdChuHo());
        hoKhau.setDiaChi(hoKhauRequest.getDiaChi());

        hoKhau.getNhanKhaus().clear();

        for (Long nhanKhauId: hoKhauRequest.getNhanKhaus()) {
            Optional<NhanKhau> nhanKhau = nhanKhauRepository.findById(nhanKhauId);
            if (nhanKhau.isPresent()) {
                hoKhau.getNhanKhaus().add(nhanKhau.get());
            }
        }

        hoKhauRepository.save(hoKhau);

        hoatDongService.createrNew("Cập nhật hộ khẩu: " + hoKhau.getHoTenChuHo());
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, hoKhau));
    }

    @Transactional
    public ResponseEntity<APIResponse> deleteHoKhau(Long id) {
        Optional<HoKhau> hoKhauOptional = hoKhauRepository.findById(id);
        if (!hoKhauOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.HO_KHAU_ID_NOT_FOUND));

        HoKhau hoKhau = hoKhauOptional.get();
        for (NhanKhau nhanKhau: hoKhau.getNhanKhaus()) {
            nhanKhauRepository.delete(nhanKhau);
        }
        hoKhau.getNhanKhaus().clear();

        List<CuocHop> cuocHops = cuocHopRepository.findAllByHoKhausId(id);
        for (CuocHop cuocHop: cuocHops) {
            cuocHop.getHoKhaus().remove(hoKhau);
        }
        cuocHopRepository.saveAll(cuocHops);

        hoatDongService.createrNew("Xóa hộ khẩu: " + hoKhau.getHoTenChuHo());
        hoKhauRepository.delete(hoKhau);

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS));
    }

    @ReadOnlyProperty
    public long getSoLuongHoKhau() {
        return hoKhauRepository.count();
    }


}
