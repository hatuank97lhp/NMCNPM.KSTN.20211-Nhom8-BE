package com.example.cnpmbe.model.request.cuochop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CuocHopRequest {
    @Size(max = 65536)
    private String tieuDe = "";

    private Instant thoiGian;

    @Size(max = 65536)
    private String diaDiem = "";

    @Size(max = 65536)
    private String noiDung = "";

    @Size(max = 65536)
    private String banBaoCao = "";


    private String nguoiTao = "";

    private List<Long> hoKhaus = new ArrayList<>();
}
