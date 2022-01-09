package com.example.cnpmbe.service;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.model.entity.HoatDong;
import com.example.cnpmbe.repository.HoatDongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HoatDongService {
    @Autowired
    private HoatDongRepository hoatDongRepository;

    @Transactional
    public void createrNew(String mess) {
        HoatDong hoatDong = new HoatDong(mess);
        hoatDongRepository.save(hoatDong);
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getAll(Pageable pageable) {
        List<HoatDong> hoatDongs = hoatDongRepository.findAllByOrderByTimeDesc(pageable);
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, hoatDongs));
    }

}

