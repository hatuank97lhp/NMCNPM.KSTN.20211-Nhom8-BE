package com.example.cnpmbe.service;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.enums.ExceptionMessages;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.model.entity.cuochop.CuocHop;
import com.example.cnpmbe.model.entity.cuochop.DiemDanh;
import com.example.cnpmbe.model.entity.hokhau.HoKhau;
import com.example.cnpmbe.model.request.cuochop.DiemDanhRequest;
import com.example.cnpmbe.model.response.DiemDanhSimpleResponse;
import com.example.cnpmbe.repository.CuocHopRepository;
import com.example.cnpmbe.repository.DiemDanhRepository;
import com.example.cnpmbe.repository.HoKhauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiemDanhService {
    @Autowired
    private DiemDanhRepository diemDanhRepository;

    @Autowired
    private CuocHopRepository cuocHopRepository;

    @Autowired
    private HoKhauRepository hoKhauRepository;

    @Transactional
    public ResponseEntity<APIResponse> diemDanhCuocHop(DiemDanhRequest diemDanhRequest) {
        Optional<DiemDanh> diemDanhOptional = diemDanhRepository.getByCuocHopIdAndHoKhauId(diemDanhRequest.getCuocHop(), diemDanhRequest.getHoKhau());
        if (diemDanhOptional.isPresent()) {
            diemDanhOptional.get().setDiemDanh(diemDanhRequest.getDiemDanh());
            diemDanhOptional.get().setLyDo(diemDanhRequest.getLyDo());
            diemDanhRepository.save(diemDanhOptional.get());

            DiemDanhSimpleResponse diemDanhSimpleResponse = new DiemDanhSimpleResponse(diemDanhOptional.get());
            return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, diemDanhSimpleResponse));
        }

        Optional<HoKhau> hoKhau = hoKhauRepository.findById(diemDanhRequest.getHoKhau());
        if (!hoKhau.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.HO_KHAU_ID_NOT_FOUND));

        Optional<CuocHop> cuocHop = cuocHopRepository.findById(diemDanhRequest.getCuocHop());
        if (!cuocHop.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.CUOC_HOP_ID_NOT_FOUND));

        DiemDanh diemDanh = new DiemDanh();
        diemDanh.setCuocHop(cuocHop.get());
        diemDanh.setHoKhau(hoKhau.get());
        diemDanh.setDiemDanh(diemDanh.getDiemDanh());
        diemDanh.setLyDo(diemDanh.getLyDo());

        diemDanhRepository.save(diemDanh);
        DiemDanhSimpleResponse diemDanhSimpleResponse = new DiemDanhSimpleResponse(diemDanh);
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, diemDanhSimpleResponse));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getAllDiemDanhByCuocHop(Long cuocHop) {
        List<DiemDanhSimpleResponse> diemDanhSimpleResponses = new ArrayList<>();
        Optional<CuocHop> cuocHopOptional = cuocHopRepository.findById(cuocHop);
        if (!cuocHopOptional.isPresent())
            return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, diemDanhSimpleResponses));

        List<Long> ids = new ArrayList<>();
        for (HoKhau hoKhau: cuocHopOptional.get().getHoKhaus())
            ids.add(hoKhau.getId());

        List<DiemDanh> diemDanhs = diemDanhRepository.getAllByCuocHopIdAndHoKhauIdIn(cuocHop, ids);

        for (DiemDanh diemDanh: diemDanhs) {
            diemDanhSimpleResponses.add(new DiemDanhSimpleResponse(diemDanh));
        }
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, diemDanhSimpleResponses));
    }
}
