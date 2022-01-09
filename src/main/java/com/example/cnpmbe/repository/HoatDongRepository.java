package com.example.cnpmbe.repository;

import com.example.cnpmbe.model.entity.HoatDong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoatDongRepository extends JpaRepository<HoatDong, Long> {
    Page<HoatDong> findAllByOrderByTimeDesc(Pageable pageable);
}
