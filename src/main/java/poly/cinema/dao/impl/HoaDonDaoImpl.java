/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.HoaDonDAO;
import poly.cinema.entity.HoaDon;
import poly.cinema.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class HoaDonDaoImpl implements HoaDonDAO {
// Ghi dòng này để Git nhận ra file là mới
    @Override
    public HoaDon create(HoaDon entity) {
        String sql = "INSERT INTO HoaDon (ma_nd, ngay_lap, tong_tien) VALUES (?, ?, ?)";
        XJdbc.executeUpdate(sql,
                entity.getMaNguoiDung(), // Sử dụng maNguoiDung từ thực thể
                new java.sql.Timestamp(entity.getNgayLap().getTime()), // Sử dụng Timestamp cho DATETIME
                entity.getTongTien()
        );
        return entity;
    }

    @Override
    public void update(HoaDon entity) {
        String sql = "UPDATE HoaDon SET ma_nd=?, ngay_lap=?, tong_tien=? WHERE ma_hd=?";
        XJdbc.executeUpdate(sql,
                entity.getMaNguoiDung(), // Sử dụng maNguoiDung
                new java.sql.Timestamp(entity.getNgayLap().getTime()), // Sử dụng Timestamp cho DATETIME
                entity.getTongTien(),
                entity.getMaHD() // Sử dụng maHd cho mệnh đề WHERE
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM HoaDon WHERE ma_hd=?"; // Tên cột SQL ma_hd
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<HoaDon> findAll() {
        String sql = "SELECT * FROM HoaDon";
        return selectBySql(sql);
    }

    @Override
    public HoaDon findById(Integer id) {
        String sql = "SELECT * FROM HoaDon WHERE ma_hd=?"; // Tên cột SQL ma_hd
        List<HoaDon> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    private List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt("ma_hd"));         // Tên cột SQL ma_hd
                hd.setMaNguoiDung(rs.getInt("ma_nd"));   // Tên cột SQL ma_nd
                hd.setNgayLap(rs.getTimestamp("ngay_lap")); // Sử dụng getTimestamp cho DATETIME
                hd.setTongTien(rs.getDouble("tong_tien")); // Tên cột SQL tong_tien
                list.add(hd);
            }
            if (rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
