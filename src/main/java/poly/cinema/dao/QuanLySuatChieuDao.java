/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.sql.Date;
import java.util.List;
import poly.cinema.entity.SuatChieu;
import poly.cinema.entity.XuatChieu;

/**
 *
 * @author Admin
 */
public interface QuanLySuatChieuDao extends CrudDAO<SuatChieu, Integer> {
    List<SuatChieu> findByNgayVaPhim(Date ngay, int maPhim);
    List<SuatChieu> findByMaPhim(int maPhim);
}

