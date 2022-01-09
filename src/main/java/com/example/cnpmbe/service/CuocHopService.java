package com.example.cnpmbe.service;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.enums.ExceptionMessages;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.model.entity.cuochop.CuocHop;
import com.example.cnpmbe.model.entity.cuochop.DiemDanh;
import com.example.cnpmbe.model.entity.hokhau.HoKhau;
import com.example.cnpmbe.model.request.cuochop.CuocHopRequest;
import com.example.cnpmbe.model.request.cuochop.DiemDanhRequest;
import com.example.cnpmbe.model.request.cuochop.HoKhauCuocHop;
import com.example.cnpmbe.model.response.*;
import com.example.cnpmbe.repository.CuocHopRepository;
import com.example.cnpmbe.repository.DiemDanhRepository;
import com.example.cnpmbe.repository.HoKhauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Service
public class CuocHopService {
    @Autowired
    private CuocHopRepository cuocHopRepository;

    @Autowired
    private DiemDanhRepository diemDanhRepository;

    @Autowired
    private HoKhauRepository hoKhauRepository;

    @Autowired
    private HoatDongService hoatDongService;

    @Autowired
    private DiemDanhService diemDanhService;

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getAllCuocHop(String keyword, Pageable pageable) {
        Page<CuocHop> cuocHops = cuocHopRepository.findAllByTieuDeContains(keyword, pageable);

        List<CuocHopSimpleResponse> cuocHopSimpleResponses = new ArrayList<>();
        for (CuocHop cuocHop: cuocHops) {
            CuocHopSimpleResponse cuocHopSimpleResponse = new CuocHopSimpleResponse(cuocHop);
            List<DiemDanh> diemDanhs = diemDanhRepository.getAllByCuocHopIdAndDiemDanh(cuocHop.getId(), true);
            List<DiemDanh> vangMat = diemDanhRepository.getAllByCuocHopIdAndDiemDanh(cuocHop.getId(), false);

            cuocHopSimpleResponse.setVangMat((long)vangMat.size());
            cuocHopSimpleResponse.setThamGia((long)diemDanhs.size());

            cuocHopSimpleResponses.add(cuocHopSimpleResponse);
        }

        Page<CuocHopSimpleResponse> page = new PageImpl<>(cuocHopSimpleResponses, pageable, cuocHopSimpleResponses.size());

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, page));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getCuocHopById(Long id) {
        Optional<CuocHop> cuocHop = cuocHopRepository.findById(id);
        if (!cuocHop.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.CUOC_HOP_ID_NOT_FOUND));

        CuocHopSimpleResponse cuocHopSimpleResponse = new CuocHopSimpleResponse(cuocHop.get());

        List<DiemDanh> diemDanhs = diemDanhRepository.getAllByCuocHopIdAndDiemDanh(cuocHop.get().getId(), true);
        List<DiemDanh> vangMat = diemDanhRepository.getAllByCuocHopIdAndDiemDanh(cuocHop.get().getId(), false);

        cuocHopSimpleResponse.setVangMat((long)vangMat.size());
        cuocHopSimpleResponse.setThamGia((long)diemDanhs.size());

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, cuocHopSimpleResponse));
    }

    @Transactional
    public ResponseEntity<APIResponse> addNewCuocHop(CuocHopRequest cuocHopRequest) {
        CuocHop cuocHop = new CuocHop(cuocHopRequest);
        cuocHop.setHoKhaus(new ArrayList<>());

        for (Long idHoKhau: cuocHopRequest.getHoKhaus()) {
            Optional<HoKhau> hoKhau = hoKhauRepository.findById(idHoKhau);
            if (hoKhau.isPresent()) {
                cuocHop.getHoKhaus().add(hoKhau.get());
            }
        }

        cuocHopRepository.save(cuocHop);

        for (HoKhau hoKhau: cuocHop.getHoKhaus()) {
            DiemDanh diemDanh = new DiemDanh();
            diemDanh.setDiemDanh(true);
            diemDanh.setCuocHop(cuocHop);
            diemDanh.setHoKhau(hoKhau);
            diemDanh.setLyDo("");
            diemDanhRepository.save(diemDanh);
        }

        hoatDongService.createrNew("Tạo mới cuộc họp: " + cuocHop.getTieuDe());

        CuocHopSimpleResponse cuocHopSimpleResponse = new CuocHopSimpleResponse(cuocHop);
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, cuocHopSimpleResponse));
    }

    @Transactional
    public ResponseEntity<APIResponse> updateCuocHop(Long id, CuocHopRequest cuocHopRequest) {
        Optional<CuocHop> cuocHopOpt = cuocHopRepository.findById(id);
        if (!cuocHopOpt.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.CUOC_HOP_ID_NOT_FOUND));

        CuocHop cuocHop = cuocHopOpt.get();
        cuocHop.update(cuocHopRequest);

        cuocHopRepository.save(cuocHop);

        hoatDongService.createrNew("Chỉnh sửa cuộc họp: " + cuocHop.getTieuDe());

        CuocHopSimpleResponse cuocHopSimpleResponse = new CuocHopSimpleResponse(cuocHop);
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, cuocHopSimpleResponse));
    }

    @Transactional
    public ResponseEntity<APIResponse> themMoiHoKhauVaoCuocHop(Long id, Long idHoKhau) {
        Optional<CuocHop> cuocHopOptional = cuocHopRepository.findByIdAndHoKhausId(id, idHoKhau);
        Optional<HoKhau> hoKhau = hoKhauRepository.findById(idHoKhau);
        if (cuocHopOptional.isPresent()) {
            DiemDanhRequest diemDanhRequest = new DiemDanhRequest();
            diemDanhRequest.setDiemDanh(true);
            diemDanhRequest.setCuocHop(id);
            diemDanhRequest.setHoKhau(idHoKhau);
            diemDanhService.diemDanhCuocHop(diemDanhRequest);
            return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS));
        }

        Optional<CuocHop> cuocHopOpt = cuocHopRepository.findById(id);
        if (!cuocHopOpt.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.CUOC_HOP_ID_NOT_FOUND));

        CuocHop cuocHop = cuocHopOpt.get();
        if (hoKhau.isPresent()) {
            cuocHop.getHoKhaus().add(hoKhau.get());
            DiemDanhRequest diemDanhRequest = new DiemDanhRequest();
            diemDanhRequest.setDiemDanh(true);
            diemDanhRequest.setCuocHop(id);
            diemDanhRequest.setHoKhau(idHoKhau);
            diemDanhService.diemDanhCuocHop(diemDanhRequest);
            cuocHopRepository.save(cuocHop);
        }
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS));
    }

    @Transactional
    public ResponseEntity<APIResponse> xoaHoKhauKhoiCuocHop(Long id, Long idHoKhau) {
        Optional<CuocHop> cuocHopOptional = cuocHopRepository.findByIdAndHoKhausId(id, idHoKhau);
        Optional<HoKhau> hoKhau = hoKhauRepository.findById(idHoKhau);
        if (cuocHopOptional.isPresent()) {
            CuocHop cuocHop = cuocHopOptional.get();
            if (hoKhau.isPresent()) {
                cuocHop.getHoKhaus().remove(hoKhau.get());
            }
        }

        List<DiemDanh> diemDanhs = diemDanhRepository.getAllByCuocHopIdAndHoKhauId(id, idHoKhau);
        for (DiemDanh diemDanh: diemDanhs) {
            diemDanhRepository.delete(diemDanh);
        }

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS));
    }

    @Transactional
    public ResponseEntity<APIResponse> deleteCuocHop(Long id) {
        Optional<CuocHop> cuocHopOpt = cuocHopRepository.findById(id);
        if (!cuocHopOpt.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.CUOC_HOP_ID_NOT_FOUND));

        CuocHop cuocHop = cuocHopOpt.get();

        List<DiemDanh> diemDanhs = diemDanhRepository.getAllByCuocHopId(id);
        for (DiemDanh diemDanh: diemDanhs) {
            diemDanhRepository.delete(diemDanh);
        }

        hoatDongService.createrNew("Xóa cuộc họp: " + cuocHop.getTieuDe());
        cuocHopRepository.delete(cuocHop);

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> thongKeCuocHop(Instant from, Instant to) {
        List<CuocHop> cuocHops = cuocHopRepository.findAllByThoiGianBetweenOrderByThoiGianAsc(from, to);
        List<Long> idCuocHops = new ArrayList<>();
        for (CuocHop cuocHop: cuocHops) {
            idCuocHops.add(cuocHop.getId());
        }

        List<DiemDanh> diemDanhs = diemDanhRepository.getAllByCuocHopIdIn(idCuocHops);

        long thamGia = 0L;
        long vangCoLyDo = 0L;
        long vangKhongLyDo = 0L;

        for (DiemDanh diemDanh: diemDanhs) {
            if (diemDanh.getDiemDanh()) {
                thamGia++;
            }
            else {
                if (diemDanh.getLyDo().equals(""))
                    vangCoLyDo++;
                else
                    vangCoLyDo++;
            }
        }

        ThongKeCuocHopResponse thongKeCuocHopResponse = new ThongKeCuocHopResponse();
        thongKeCuocHopResponse.setThamGia(thamGia);
        thongKeCuocHopResponse.setVangCoLyDo(vangCoLyDo);
        thongKeCuocHopResponse.setVangKhongLyDo(vangKhongLyDo);
        thongKeCuocHopResponse.setCuocHops(new ArrayList<>());
        for (CuocHop cuocHop: cuocHops) {
            thongKeCuocHopResponse.getCuocHops().add(new CuocHopEzResponse(cuocHop));
        }

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, thongKeCuocHopResponse));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> thongKeNguoiThamGia(Instant from, Instant to) {
        List<CuocHop> cuocHops = cuocHopRepository.findAllByThoiGianBetweenOrderByThoiGianAsc(from, to);
        List<Long> idCuocHops = new ArrayList<>();
        for (CuocHop cuocHop: cuocHops) {
            idCuocHops.add(cuocHop.getId());
        }

        List<DiemDanh> diemDanhs = diemDanhRepository.getAllByCuocHopIdIn(idCuocHops);

        Map<Long, Long> thamGia = new HashMap<>();
        Map<Long, Long> vangCoPhep = new HashMap<>();
        Map<Long, Long> vangKhongPhep = new HashMap<>();
        Map<Long, HoKhau> listHK = new HashMap<>();

        for (DiemDanh diemDanh: diemDanhs) {
            if (!listHK.containsKey(diemDanh.getHoKhau().getId())) {
                listHK.put(diemDanh.getHoKhau().getId(), diemDanh.getHoKhau());
            }

            if (diemDanh.getDiemDanh()) {
                Long tg = 0L;
                if (thamGia.containsKey(diemDanh.getHoKhau().getId())) {
                    tg = thamGia.get(diemDanh.getHoKhau().getId());
                    thamGia.remove(diemDanh.getHoKhau().getId());
                }
                tg = tg + 1;
                thamGia.put(diemDanh.getHoKhau().getId(), tg);

            }
            else {
                if (diemDanh.getLyDo().equals("")) {
                    Long tg = 0L;
                    if (vangKhongPhep.containsKey(diemDanh.getHoKhau().getId())) {
                        tg = vangKhongPhep.get(diemDanh.getHoKhau().getId());
                        vangKhongPhep.remove(diemDanh.getHoKhau().getId());
                    }
                    tg = tg + 1;
                    vangKhongPhep.put(diemDanh.getHoKhau().getId(), tg);

                }
                else {
                    Long tg = 0L;
                    if (vangCoPhep.containsKey(diemDanh.getHoKhau().getId())) {
                        tg = vangCoPhep.get(diemDanh.getHoKhau().getId());
                        vangCoPhep.remove(diemDanh.getHoKhau().getId());
                    }
                    tg = tg + 1;
                    vangCoPhep.put(diemDanh.getHoKhau().getId(), tg);
                }
            }
        }

        List<HoKhauThamGia> hoKhauThamGias = new ArrayList<>();
        Set<Long> keySet = listHK.keySet();
        for (Long key: keySet) {
            HoKhauThamGia hoKhauThamGia = new HoKhauThamGia();
            HoKhau hoKhau = listHK.get(key);

            hoKhauThamGia.setId(hoKhau.getId());
            hoKhauThamGia.setHoTenChuHo(hoKhau.getHoTenChuHo());
            hoKhauThamGia.setDiaChi(hoKhau.getDiaChi());
            if (thamGia.containsKey(key))
                hoKhauThamGia.setThamGia( thamGia.get(key) );
            else
                hoKhauThamGia.setThamGia(0);

            if (vangCoPhep.containsKey(key))
                hoKhauThamGia.setVangCoLyDo(vangCoPhep.get(key));
            else hoKhauThamGia.setVangCoLyDo(0);

            if (vangKhongPhep.containsKey(key))
                hoKhauThamGia.setVangKhongLyDo(vangKhongPhep.get(key));
            else
                hoKhauThamGia.setVangKhongLyDo(0);

            hoKhauThamGias.add(hoKhauThamGia);
        }

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, hoKhauThamGias));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getHoKhauThamGiaTheoId(Long id, Instant from, Instant to) {
        List<CuocHop> cuocHops = cuocHopRepository.findAllByThoiGianBetweenOrderByThoiGianAsc(from, to);
        List<Long> idCuocHops = new ArrayList<>();
        for (CuocHop cuocHop: cuocHops) {
            idCuocHops.add(cuocHop.getId());
        }

        List<DiemDanh> diemDanhs = diemDanhRepository.getAllByCuocHopIdInAndHoKhauId(idCuocHops, id);
        Optional<HoKhau> hoKhauOptional = hoKhauRepository.findById(id);
        if (!hoKhauOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.HO_KHAU_ID_NOT_FOUND));

        HoKhauThamGiaChiTiet chiTiet = new HoKhauThamGiaChiTiet();
        chiTiet.setId(hoKhauOptional.get().getId());
        chiTiet.setHoTenChuHo(hoKhauOptional.get().getHoTenChuHo());
        chiTiet.setDiaChi(hoKhauOptional.get().getDiaChi());

        for (DiemDanh diemDanh: diemDanhs) {
            CuocHopEzResponse cuocHopEzResponse = new CuocHopEzResponse(diemDanh.getCuocHop());
            if (diemDanh.getDiemDanh()) {
                chiTiet.getCuocHopThamGia().add(cuocHopEzResponse);
            }
            else {
                if (diemDanh.getLyDo().equals("")) {
                    chiTiet.getCuocHopVangKhongLyDo().add(cuocHopEzResponse);
                }
                else {
                    chiTiet.getCuocHopVangKhongLyDo().add(cuocHopEzResponse);
                }
            }
        }

        chiTiet.setThamGia( chiTiet.getCuocHopThamGia().size() );
        chiTiet.setVangCoLyDo( chiTiet.getCuocHopVangCoLyDo().size());
        chiTiet.setVangKhongLyDo(chiTiet.getCuocHopVangKhongLyDo().size());

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, chiTiet));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getAllNguoiThamGia(Long idCuochop) {
        Optional<CuocHop> cuocHopOptional = cuocHopRepository.findById(idCuochop);
        if (!cuocHopOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.CUOC_HOP_ID_NOT_FOUND));

        CuocHop cuocHop = cuocHopOptional.get();
        List<Long> idHoKhau = new ArrayList<>();
        for (HoKhau hoKhau: cuocHop.getHoKhaus()) {
            idHoKhau.add(hoKhau.getId());
        }

        List<HoKhau> invited = hoKhauRepository.getAllByIdIn(idHoKhau);
        List<HoKhau> notInvited = hoKhauRepository.getAllByIdNotIn(idHoKhau);

        List<HoKhauCuocHopResponse> response = new ArrayList<>();
        for (HoKhau hoKhau: invited) {
            HoKhauCuocHopResponse hoKhauCuocHopResponse = new HoKhauCuocHopResponse();
            hoKhauCuocHopResponse.setId( hoKhau.getId() );
            hoKhauCuocHopResponse.setHoTenChuHo( hoKhau.getHoTenChuHo() );
            hoKhauCuocHopResponse.setInvited(true);
            response.add(hoKhauCuocHopResponse);
        }

        for (HoKhau hoKhau: notInvited) {
            HoKhauCuocHopResponse hoKhauCuocHopResponse = new HoKhauCuocHopResponse();
            hoKhauCuocHopResponse.setId( hoKhau.getId() );
            hoKhauCuocHopResponse.setHoTenChuHo( hoKhau.getHoTenChuHo() );
            hoKhauCuocHopResponse.setInvited(false);
            response.add(hoKhauCuocHopResponse);
        }

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, response));
    }

    @Transactional
    public ResponseEntity<APIResponse> capNhatDanhSachThamGia(Long idCuochop, HoKhauCuocHop hoKhauCuocHop) {
        Optional<CuocHop> cuocHopOptional = cuocHopRepository.findById(idCuochop);
        if (!cuocHopOptional.isPresent())
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.CUOC_HOP_ID_NOT_FOUND));

        CuocHop cuocHop = cuocHopOptional.get();

        Set<Long> idNhanKhauCu = new HashSet<>();
        Set<Long> idNhanKhauMoi = new HashSet<>();

        for (HoKhau hoKhau: cuocHop.getHoKhaus()) {
            if (!idNhanKhauCu.contains(hoKhau.getId()))
                idNhanKhauCu.add(hoKhau.getId());
        }

        for (Long idMoi: hoKhauCuocHop.getHoKhaus()) {
            if (!idNhanKhauMoi.contains(idMoi)) {
                idNhanKhauMoi.add(idMoi);
            }
        }

        for (HoKhau hoKhau: cuocHop.getHoKhaus()) {
            if (!idNhanKhauMoi.contains(hoKhau.getId())) {
                Optional<DiemDanh> diemDanh = diemDanhRepository.getByCuocHopIdAndHoKhauId(cuocHop.getId(), hoKhau.getId());
                if (diemDanh.isPresent()) {
                    diemDanhRepository.delete(diemDanh.get());
                }
            }
        }

        for (int i = cuocHop.getHoKhaus().size() - 1; i >= 0; i--) {
            if (!idNhanKhauMoi.contains(cuocHop.getHoKhaus().get(i).getId())) {
                cuocHop.getHoKhaus().remove(i);
            }
        }

        for (Long idMoi: idNhanKhauMoi) {
            if (!idNhanKhauCu.contains(idMoi)) {
                Optional<HoKhau> hoKhauOptional = hoKhauRepository.findById(idMoi);
                if (hoKhauOptional.isPresent()) {
                    DiemDanh diemDanh = new DiemDanh();
                    diemDanh.setDiemDanh(true);
                    diemDanh.setCuocHop(cuocHop);
                    diemDanh.setHoKhau(hoKhauOptional.get());
                    diemDanh.setLyDo("");

                    diemDanhRepository.save(diemDanh);
                    cuocHop.getHoKhaus().add(hoKhauOptional.get());
                }
            }
        }

        cuocHopRepository.save(cuocHop);
        CuocHopSimpleResponse cuocHopSimpleResponse = new CuocHopSimpleResponse(cuocHop);
        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, cuocHopSimpleResponse));

    }

    @GetMapping
    public ResponseEntity<APIResponse> getDanhSachMoi() {
        List<HoKhau> notInvited = hoKhauRepository.findAll();

        List<HoKhauCuocHopResponse> response = new ArrayList<>();

        for (HoKhau hoKhau: notInvited) {
            HoKhauCuocHopResponse hoKhauCuocHopResponse = new HoKhauCuocHopResponse();
            hoKhauCuocHopResponse.setId( hoKhau.getId() );
            hoKhauCuocHopResponse.setHoTenChuHo( hoKhau.getHoTenChuHo() );
            hoKhauCuocHopResponse.setInvited(false);
            response.add(hoKhauCuocHopResponse);
        }

        return ResponseEntity.ok().body(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, response));
    }
}
