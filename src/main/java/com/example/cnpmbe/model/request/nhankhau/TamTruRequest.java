package com.example.cnpmbe.model.request.nhankhau;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class TamTruRequest {
    private String hoVaTen = "";

    private String cccd = "";

    @Size(max = 65536)
    private String diaChi = "";

    private Instant tuNgay;

    private Instant denNgay;

    @Size(max = 65536)
    private String lyDo = "";
}
