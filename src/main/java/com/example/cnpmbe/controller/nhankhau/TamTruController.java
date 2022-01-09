package com.example.cnpmbe.controller.nhankhau;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.model.request.nhankhau.TamTruRequest;
import com.example.cnpmbe.service.TamTruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "api/v1/tamtru")
public class TamTruController {
    @Autowired
    private TamTruService tamTruService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllTamTru(@PathParam("keyword") String keyword, Pageable pageable) {
        if (keyword == null)
            keyword = "";

        ResponseEntity<APIResponse> response = tamTruService.getAllTamTru(keyword, pageable);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getTamTru(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = tamTruService.getTamTruById(id);
        return response;
    }

    @PostMapping
    public ResponseEntity<APIResponse> addNewTamTru(@RequestBody @Valid TamTruRequest tamTruRequest) {
        ResponseEntity<APIResponse> response = tamTruService.addNewTamTru(tamTruRequest);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateTamTru(@PathVariable Long id, @RequestBody @Valid TamTruRequest tamTruRequest) {
        ResponseEntity<APIResponse> response = tamTruService.updateTamTru(id, tamTruRequest);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteTamTru(@PathVariable Long id) {
        ResponseEntity<APIResponse> response = tamTruService.deleteTamTru(id);
        return response;
    }
}
