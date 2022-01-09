package com.example.cnpmbe.service;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.enums.ExceptionMessages;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.model.entity.nhankhau.TamVang;
import com.example.cnpmbe.model.request.nhankhau.TamVangRequest;
import com.example.cnpmbe.repository.TamVangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TamVangService {
    @Autowired
    private TamVangRepository tamVangRepository;

    @Autowired
    private HoatDongService hoatDongService;

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getAllTamVang(String keyword, Pageable pageable) {
        Page<TamVang> tamVangs = tamVangRepository.getAllByHoVaTenContainsOrCccdContains(keyword, keyword, pageable);
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, tamVangs));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getTamVangById(Long id) {
        Optional<TamVang> tamVang = tamVangRepository.findById(id);
        if (!tamVang.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.TAM_VANG_ID_NOT_FOUND));

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, tamVang.get()));
    }

    @Transactional
    public ResponseEntity<APIResponse> addNewTamVang(TamVangRequest tamVangRequest) {
        TamVang tamVang = new TamVang(tamVangRequest);
        tamVangRepository.save(tamVang);

        hoatDongService.createrNew("Thêm mới nhân khẩu tạm vắng: " + tamVang.getHoVaTen());
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, tamVang));
    }

    @Transactional
    public ResponseEntity<APIResponse> updateTamVang(Long id, TamVangRequest tamVangRequest) {
        Optional<TamVang> tamVangOpt = tamVangRepository.findById(id);
        if (!tamVangOpt.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.TAM_VANG_ID_NOT_FOUND));

        TamVang tamVang = tamVangOpt.get();
        tamVang.update(tamVangRequest);
        tamVangRepository.save(tamVang);

        hoatDongService.createrNew("Cập nhật nhân khẩu tạm vắng: " + tamVang.getHoVaTen());
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, tamVang));
    }

    @Transactional
    public ResponseEntity<APIResponse> deleteTamVang(Long id) {
        Optional<TamVang> tamVangOpt = tamVangRepository.findById(id);
        if (!tamVangOpt.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.TAM_VANG_ID_NOT_FOUND));

        hoatDongService.createrNew("Xóa nhân khẩu tạm vắng: " + tamVangOpt.get().getHoVaTen());
        tamVangRepository.delete(tamVangOpt.get());
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS));
    }

    @ReadOnlyProperty
    public long getSoLuongTamVang() {
        return (tamVangRepository.getAllByDenNgayIsNull().size() + tamVangRepository.getAllByDenNgayIsAfter(Instant.now()).size());
    }

}
