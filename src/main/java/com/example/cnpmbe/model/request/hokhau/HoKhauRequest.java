package com.example.cnpmbe.model.request.hokhau;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HoKhauRequest {
    @Size(min = 1)
    private String hoTenChuHo;

    @Size(min = 1)
    private String cccdChuHo;

    @Size(min = 1, max = 65536)
    private String diaChi;

    private List<Long> nhanKhaus = new ArrayList<>();
}
