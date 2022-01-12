package com.example.cnpmbe.service;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.enums.ExceptionMessages;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.model.entity.hokhau.HoKhau;
import com.example.cnpmbe.model.entity.nhankhau.NhanKhau;
import com.example.cnpmbe.model.request.nhankhau.NhanKhauRequest;
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
public class NhanKhauService {
    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private HoKhauRepository hoKhauRepository;

    @Autowired
    private HoatDongService hoatDongService;

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getAllNhanKhau(Pageable pageable, String keyword) {
        Page<NhanKhau> nhanKhaus = nhanKhauRepository.getAllByHoVaTenContainsAndIdhkIsNotOrCccdContainsAndIdhkIsNot(keyword, (int)(0), keyword, (int)(0), pageable);
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, nhanKhaus));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getNhanKhauById(Long id) {
        Optional<NhanKhau> nhanKhauOptional = nhanKhauRepository.findById(id);
        if (!nhanKhauOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.NHAN_KHAU_ID_NOT_FOUND));

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, nhanKhauOptional.get()));
    }

    @Transactional
    public ResponseEntity<APIResponse> addNewNhanKhau(NhanKhauRequest nhanKhauRequest) {
        if (!nhanKhauRequest.getCccd().equals("")) {
            if (nhanKhauRepository.existsByCccd(nhanKhauRequest.getCccd())) {
                return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.NHAN_KHAU_CCCD_DUPLICATE));
            }
        }

        NhanKhau nhanKhau = new NhanKhau(nhanKhauRequest);
        nhanKhauRepository.save(nhanKhau);

        hoatDongService.createrNew("Thêm mới nhân khẩu: " + nhanKhau.getHoVaTen());

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, nhanKhau));
    }

    @Transactional
    public ResponseEntity<APIResponse> updateNhanKhau(Long id, NhanKhauRequest nhanKhauRequest) {
        Optional<NhanKhau> nhanKhauOptional = nhanKhauRepository.findById(id);
        if (!nhanKhauOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.NHAN_KHAU_ID_NOT_FOUND));

        NhanKhau nhanKhau = nhanKhauOptional.get();

        if (!nhanKhauRequest.getCccd().equals("")) {
            Optional<NhanKhau> nhanKhauOptionalCccd = nhanKhauRepository.findByCccd(nhanKhauRequest.getCccd());
            if (nhanKhauOptionalCccd.isPresent()) {
                if (nhanKhauOptionalCccd.get().getId() != nhanKhau.getId()) {
                    return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.NHAN_KHAU_CCCD_DUPLICATE));
                }
            }
        }

        nhanKhau.update(nhanKhauRequest);
        nhanKhauRepository.save(nhanKhau);

        hoatDongService.createrNew("Sửa thông tin nhân khẩu: " + nhanKhau.getHoVaTen());

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, nhanKhau));
    }

    @Transactional
    public ResponseEntity<APIResponse> deleteNhanKhau(Long id) {
        Optional<NhanKhau> nhanKhauOptional = nhanKhauRepository.findById(id);
        if (!nhanKhauOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.NHAN_KHAU_ID_NOT_FOUND));

        hoatDongService.createrNew("Xóa nhân khẩu: " + nhanKhauOptional.get().getHoVaTen());

        Optional<HoKhau> hoKhauOpt = hoKhauRepository.findByNhanKhaus_Id(id);
        if (hoKhauOpt.isPresent()) {
            HoKhau hoKhau = hoKhauOpt.get();
            for (NhanKhau nhanKhauX: hoKhau.getNhanKhaus()) {
                if (nhanKhauX.getId().equals(id)) {
                    hoKhau.getNhanKhaus().remove(nhanKhauX);
                    break;
                }
            }
            hoKhauRepository.save(hoKhau);
        }

        nhanKhauRepository.delete(nhanKhauOptional.get());
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS));
    }

    @ReadOnlyProperty
    public long getSoLuongNhanKhau() {
        return nhanKhauRepository.count();
    }

}
