/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.List;
import poly.cinema.entity.SanPham;

/**
 *
 * @author NITRO
 */
public interface QuanLySanPhamDAO extends CrudDAO<SanPham, String> {

    List<SanPham> findByLoai(String loai);

    List<SanPham> searchByTen(String keyword);
    
}
