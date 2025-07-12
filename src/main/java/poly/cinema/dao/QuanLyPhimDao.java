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
    // Bạn có thể bổ sung các phương thức mở rộng tại đây nếu cần
    List<Phim> findByTrangThai(String trangThai);

    List<Phim> findByTenPhim(String keyword);
}

