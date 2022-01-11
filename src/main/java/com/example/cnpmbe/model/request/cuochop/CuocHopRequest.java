package com.example.cnpmbe.model.request.cuochop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CuocHopRequest {
    @Size(min = 1, max = 65536)
    private String tieuDe = "";

    @NotNull
    private Instant thoiGian;

    @Size(min = 1, max = 65536)
    private String diaDiem = "";

    @Size(min = 1, max = 65536)
    private String noiDung = "";

    @Size(max = 65536)
    private String banBaoCao = "";

    private String nguoiTao = "";

    private List<Long> hoKhaus = new ArrayList<>();
}
