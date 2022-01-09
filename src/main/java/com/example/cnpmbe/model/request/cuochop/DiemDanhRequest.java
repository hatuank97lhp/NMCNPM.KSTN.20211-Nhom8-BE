package com.example.cnpmbe.model.request.cuochop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiemDanhRequest {
    private Long hoKhau;

    private Long cuocHop;

    private Boolean diemDanh = true;

    private String lyDo = "";
}
