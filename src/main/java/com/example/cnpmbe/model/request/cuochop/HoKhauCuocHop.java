package com.example.cnpmbe.model.request.cuochop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HoKhauCuocHop {
    private List<Long> hoKhaus = new ArrayList<>();
}
