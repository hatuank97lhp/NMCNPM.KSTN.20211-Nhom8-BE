package com.example.cnpmbe.model.response;

import com.example.cnpmbe.model.entity.cuochop.CuocHop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ThongKeCuocHopResponse {
    private long thamGia = 0L;

    private long vangCoLyDo = 0L;

    private long vangKhongLyDo = 0L;

    private List<CuocHopEzResponse> cuocHops = new ArrayList<>();
}
