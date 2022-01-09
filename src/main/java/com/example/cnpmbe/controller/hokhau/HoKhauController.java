package com.example.cnpmbe.controller.hokhau;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.model.request.hokhau.HoKhauRequest;
import com.example.cnpmbe.service.HoKhauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "api/v1/hokhau")
public class HoKhauController {
    @Autowired
    private HoKhauService hoKhauService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllHoKhau(@PathParam("keyword") String keyword, Pageable pageable) {
        if (keyword == null)
            keyword = "";
        ResponseEntity<APIResponse> response = hoKhauService.getAllHoKhau(keyword, pageable);
        return response;
    }

    @GetMapping("{id}")
    public ResponseEntity<APIResponse> getHoKhauById(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = hoKhauService.getHoKhauById(id);
        return response;
    }

    @PostMapping
    public ResponseEntity<APIResponse> addNewHoKhau(@RequestBody @Valid HoKhauRequest hoKhauRequest) {
        ResponseEntity<APIResponse> response = hoKhauService.addNewHoKhau(hoKhauRequest);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateHK(@PathVariable Long id, @RequestBody @Valid HoKhauRequest hoKhauRequest) {
        ResponseEntity<APIResponse> response = hoKhauService.updateHoKhau(id, hoKhauRequest);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteHk(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = hoKhauService.deleteHoKhau(id);
        return response;
    }

}
