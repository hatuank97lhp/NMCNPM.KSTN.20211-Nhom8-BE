package com.example.cnpmbe.repository;

import com.example.cnpmbe.model.entity.cuochop.CuocHop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuocHopRepository extends JpaRepository<CuocHop, Long> {
    List<CuocHop> findAllByHoKhausId(Long id);

    List<CuocHop> findAllByTieuDeContains(String tieuDe, Pageable pageable);

    List<CuocHop> findAllByTieuDeContains(String tieuDe);

    Optional<CuocHop> findById(Long id);

    List<CuocHop> findAllByThoiGianBetweenOrderByThoiGianAsc(Instant from, Instant to);

    Optional<CuocHop> findByIdAndHoKhausId(Long id, Long hkId);
}
