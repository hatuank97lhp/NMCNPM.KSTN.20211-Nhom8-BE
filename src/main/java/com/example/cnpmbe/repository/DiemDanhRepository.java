package com.example.cnpmbe.repository;

import com.example.cnpmbe.model.entity.cuochop.DiemDanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiemDanhRepository extends JpaRepository<DiemDanh, Long> {
    Optional<DiemDanh> getByCuocHopIdAndHoKhauId(Long cuocHopId, Long hoKhauId);

    List<DiemDanh> getAllByCuocHopId(Long id);

    List<DiemDanh> getAllByHoKhauId(Long id);

    List<DiemDanh> getAllByCuocHopIdAndHoKhauId(Long cuocHop, Long hoKhau);

    List<DiemDanh> getAllByCuocHopIdIn(List<Long> ids);

    List<DiemDanh> getAllByCuocHopIdInAndHoKhauId(List<Long> ids, Long idHk);

    List<DiemDanh> getAllByCuocHopIdAndDiemDanh(Long id, boolean diemDanh);

    List<DiemDanh> getAllByCuocHopIdAndHoKhauIdIn(Long cuocHop, List<Long> hoKhaus);
}
