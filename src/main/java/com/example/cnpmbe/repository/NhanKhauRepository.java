package com.example.cnpmbe.repository;

import com.example.cnpmbe.model.entity.nhankhau.NhanKhau;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhanKhauRepository extends JpaRepository<NhanKhau, Long> {
    Page<NhanKhau> getAllByHoVaTenContainsAndIdhkIsNotOrCccdContainsAndIdhkIsNot(String hoVaTen, int idhk2, String cccd, int idhk, Pageable pageable);

    Optional<NhanKhau> findById(Long id);

    boolean existsByCccd(String cccd);

    Optional<NhanKhau> findByCccd(String cccd);
}
