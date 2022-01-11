package com.example.cnpmbe.model.request.nhankhau;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class TamVangRequest {

    @Size(min = 1)
    private String hoVaTen = "";

    private String cccd = "";

    @Size(min = 1, max = 65536)
    private String diaChi = "";

    @NotNull
    private Instant tuNgay;

    private Instant denNgay;

    @Size(min = 1, max = 65536)
    private String lyDo = "";
}
