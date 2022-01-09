package com.example.cnpmbe.controller.nhankhau;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.model.request.nhankhau.NhanKhauRequest;
import com.example.cnpmbe.service.NhanKhauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "api/v1/nhankhau")
public class NhanKhauController {
    @Autowired
    private NhanKhauService nhanKhauService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllNhanKhau(@PathParam("keyword") String keyword, Pageable pageable) {
        if (keyword == null)
            keyword = "";

        ResponseEntity<APIResponse> response = nhanKhauService.getAllNhanKhau(pageable, keyword);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getNhanKhauById(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = nhanKhauService.getNhanKhauById(id);
        return response;
    }

    @PostMapping
    public ResponseEntity<APIResponse> addNewNhanKhau(@RequestBody @Valid NhanKhauRequest nhanKhauRequest) {
        ResponseEntity<APIResponse> response = nhanKhauService.addNewNhanKhau(nhanKhauRequest);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateNhanKhau(@PathVariable Long id, @RequestBody @Valid NhanKhauRequest nhanKhauRequest) {
        ResponseEntity<APIResponse> response = nhanKhauService.updateNhanKhau(id, nhanKhauRequest);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteNhanKhau(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = nhanKhauService.deleteNhanKhau(id);
        return response;
    }


}
