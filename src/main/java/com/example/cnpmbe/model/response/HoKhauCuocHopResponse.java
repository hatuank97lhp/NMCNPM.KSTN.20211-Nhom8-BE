package com.example.cnpmbe.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HoKhauCuocHopResponse {
    private Long id;

    private String hoTenChuHo;

    private Boolean invited;
}
