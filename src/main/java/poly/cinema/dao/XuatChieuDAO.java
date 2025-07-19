/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.Date;
import java.util.List;
import poly.cinema.entity.XuatChieu;

/**
 *
 * @author ADMIN
 */
public interface XuatChieuDAO extends CrudDAO<XuatChieu, Integer> {
    List<XuatChieu> selectByPhimAndPhongAndNgay(Integer maPhim, String maPhong, Date ngayChieu);
    // Ghi dòng này để Git nhận ra file là mới
}
