package com.example.cnpmbe.repository;

import com.example.cnpmbe.model.entity.nhankhau.TamTru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TamTruRepository extends JpaRepository<TamTru, Long> {
    List<TamTru> getAllByHoVaTenContainsOrCccdContains(String hoVaTen, String cccd, Pageable pageable);

    Optional<TamTru> findById(Long id);

    List<TamTru> getAllByDenNgayIsNull();

    List<TamTru> getAllByDenNgayIsAfter(Instant time);
}
