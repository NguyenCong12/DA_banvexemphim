/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.Date;
import java.util.List;
import poly.cinema.entity.ThongKe;

/**
 *
 * @author KhanhLinh
 */
public interface ThongKeDAO {

    List<ThongKe.DoanhThuPhim> getDoanhThuPhim(Date begin, Date end);
    List<ThongKe.DoanhThuSanPham> getDoanhThuSanPham(Date begin, Date end);

}
