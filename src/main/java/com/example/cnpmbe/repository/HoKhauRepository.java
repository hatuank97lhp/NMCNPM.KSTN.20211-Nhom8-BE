package com.example.cnpmbe.repository;

import com.example.cnpmbe.model.entity.hokhau.HoKhau;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoKhauRepository extends JpaRepository<HoKhau, Long> {
    Optional<HoKhau> findByNhanKhaus_Id(Long id);

    Page<HoKhau> getAllByHoTenChuHoContainsOrCccdChuHoContains(String hoTenChuHo, String cccdChuHo, Pageable pageable);

    Optional<HoKhau> findById(Long id);
}
