/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.List;
import poly.cinema.entity.ChiTietHang;

/**
 *
 * @author ADMIN
 */
public interface ChiTietHangDAO extends CrudDAO<ChiTietHang, Integer> {
    List<ChiTietHang> selectByMaHD(Integer maHD);
    // Ghi dòng này để Git nhận ra file là mới
}
