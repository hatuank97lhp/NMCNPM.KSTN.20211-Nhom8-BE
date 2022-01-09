package com.example.cnpmbe.repository;

import com.example.cnpmbe.model.entity.nhankhau.TamVang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TamVangRepository extends JpaRepository<TamVang, Long> {
    List<TamVang> getAllByHoVaTenContainsOrCccdContains(String hoVaTen, String cccd, Pageable pageable);

    Optional<TamVang> findById(Long id);

    List<TamVang> getAllByDenNgayIsNull();

    List<TamVang> getAllByDenNgayIsAfter(Instant time);

}
