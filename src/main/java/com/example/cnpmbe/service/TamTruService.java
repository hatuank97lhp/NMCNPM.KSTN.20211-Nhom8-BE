package com.example.cnpmbe.service;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.enums.ExceptionMessages;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.model.entity.nhankhau.TamTru;
import com.example.cnpmbe.model.request.nhankhau.TamTruRequest;
import com.example.cnpmbe.repository.TamTruRepository;
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
public class TamTruService {
    @Autowired
    private TamTruRepository tamTruRepository;

    @Autowired
    private HoatDongService hoatDongService;

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getAllTamTru(String keyword, Pageable pageable) {
        List<TamTru> tamTrus = tamTruRepository.getAllByHoVaTenContainsOrCccdContains(keyword, keyword, pageable);
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, tamTrus));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getTamTruById(Long id) {
        Optional<TamTru> tamTru = tamTruRepository.findById(id);
        if (!tamTru.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.TAM_TRU_ID_NOT_FOUND));

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, tamTru.get()));
    }

    @Transactional
    public ResponseEntity<APIResponse> addNewTamTru(TamTruRequest tamTruRequest) {
        TamTru tamTru = new TamTru(tamTruRequest);
        tamTruRepository.save(tamTru);

        hoatDongService.createrNew("Thêm mới nhân khẩu tạm trú: " + tamTru.getHoVaTen());
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, tamTru));
    }

    @Transactional
    public ResponseEntity<APIResponse> updateTamTru(Long id, TamTruRequest tamTruRequest) {
        Optional<TamTru> tamTruOpt = tamTruRepository.findById(id);
        if (!tamTruOpt.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.TAM_TRU_ID_NOT_FOUND));

        TamTru tamTru = tamTruOpt.get();
        tamTru.update(tamTruRequest);
        tamTruRepository.save(tamTru);

        hoatDongService.createrNew("Cập nhật nhân khẩu tạm trú: " + tamTru.getHoVaTen());
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, tamTru));
    }

    @Transactional
    public ResponseEntity<APIResponse> deleteTamTru(Long id) {
        Optional<TamTru> tamTruOpt = tamTruRepository.findById(id);
        if (!tamTruOpt.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.TAM_TRU_ID_NOT_FOUND));

        hoatDongService.createrNew("Xóa nhân khẩu tạm trú: " + tamTruOpt.get().getHoVaTen());
        tamTruRepository.delete(tamTruOpt.get());
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS));
    }

    @ReadOnlyProperty
    public long getSoLuongTamTru() {
        return (tamTruRepository.getAllByDenNgayIsNull().size() + tamTruRepository.getAllByDenNgayIsAfter(Instant.now()).size());
    }
}
