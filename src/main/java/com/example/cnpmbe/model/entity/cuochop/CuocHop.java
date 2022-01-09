package com.example.cnpmbe.model.entity.cuochop;

import com.example.cnpmbe.model.entity.hokhau.HoKhau;
import com.example.cnpmbe.model.request.cuochop.CuocHopRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cuoc_hop")
public class CuocHop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuoc_hop_id_seq")
    @SequenceGenerator(
            name = "cuoc_hop_id_seq",
            sequenceName = "cuoc_hop_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Size(max = 65536)
    private String tieuDe;

    private Instant thoiGian;

    @Size(max = 65536)
    private String diaDiem;

    @Size(max = 65536)
    private String noiDung;

    @Size(max = 65536)
    private String banBaoCao;

    private String nguoiTao;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "cuochop_hokhau",
            joinColumns = @JoinColumn(name = "cuoc_hop_id"),
            inverseJoinColumns = @JoinColumn(name = "ho_khau_id")
    )
    private List<HoKhau> hoKhaus = new ArrayList<>();

    public CuocHop(CuocHopRequest cuocHopRequest) {
        tieuDe = cuocHopRequest.getTieuDe();
        thoiGian = cuocHopRequest.getThoiGian();
        diaDiem = cuocHopRequest.getDiaDiem();
        noiDung = cuocHopRequest.getNoiDung();
        banBaoCao = cuocHopRequest.getBanBaoCao();
        nguoiTao = cuocHopRequest.getNguoiTao();
    }

    public void update(CuocHopRequest cuocHopRequest) {
        tieuDe = cuocHopRequest.getTieuDe();
        thoiGian = cuocHopRequest.getThoiGian();
        diaDiem = cuocHopRequest.getDiaDiem();
        noiDung = cuocHopRequest.getNoiDung();
        banBaoCao = cuocHopRequest.getBanBaoCao();
        nguoiTao = cuocHopRequest.getNguoiTao();
    }
}
