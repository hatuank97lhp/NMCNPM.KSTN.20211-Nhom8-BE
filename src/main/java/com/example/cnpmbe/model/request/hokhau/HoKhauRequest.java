package com.example.cnpmbe.model.request.hokhau;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HoKhauRequest {
    private String hoTenChuHo = "";

    private String cccdChuHo = "";

    @Size(max = 65536)
    private String diaChi;

    private List<Long> nhanKhaus = new ArrayList<>();
}
