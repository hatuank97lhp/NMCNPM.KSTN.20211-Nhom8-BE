package com.example.cnpmbe.controller.nhankhau;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.model.request.nhankhau.TamVangRequest;
import com.example.cnpmbe.service.TamVangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "api/v1/tamvang")
public class TamVangController {
    @Autowired
    private TamVangService tamVangService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllTamVang(@PathParam("keyword") String keyword, Pageable pageable) {
        if (keyword == null)
            keyword = "";

        ResponseEntity<APIResponse> response = tamVangService.getAllTamVang(keyword, pageable);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getTamVang(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = tamVangService.getTamVangById(id);
        return response;
    }

    @PostMapping
    public ResponseEntity<APIResponse> addNewTamVang(@RequestBody @Valid TamVangRequest tamVangRequest) {
        ResponseEntity<APIResponse> response = tamVangService.addNewTamVang(tamVangRequest);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateTamVang(@PathVariable Long id, @RequestBody @Valid TamVangRequest tamVangRequest) {
        ResponseEntity<APIResponse> response = tamVangService.updateTamVang(id, tamVangRequest);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteTamVang(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = tamVangService.deleteTamVang(id);
        return response;
    }
}
