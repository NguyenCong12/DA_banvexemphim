/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.List;
import poly.cinema.entity.Phim;

/**
 *
 * @author Admin
 */
public interface QuanLyPhimDao extends CrudDAO<Phim, Integer> {
    // Tìm theo trạng thái (nếu có)
    List<Phim> findByTrangThai(String trangThai);
    
    // Tìm các phim có suất chiếu trong hôm nay
    List<Phim> findPhimChieuHomNay();
    
    // Tìm theo tên phim
    List<Phim> findByTenPhim(String keyword);
}

