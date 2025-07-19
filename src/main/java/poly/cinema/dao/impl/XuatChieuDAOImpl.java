/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import poly.cinema.dao.XuatChieuDAO;
import poly.cinema.entity.XuatChieu;
import poly.cinema.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class XuatChieuDAOImpl implements XuatChieuDAO {
// Ghi dòng này để Git nhận ra file là mới
    @Override
    public XuatChieu create(XuatChieu entity) {
        String sql = "INSERT INTO XuatChieu (ma_phim, ma_phong, ngay_chieu, gio_chieu, gia_ve) VALUES (?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
            entity.getMaPhim(), entity.getMaPhong(),
            new java.sql.Date(entity.getNgayChieu().getTime()), entity.getGioChieu(), entity.getGiaVe()
        );
        return entity;
    }

    @Override
    public void update(XuatChieu entity) {
        String sql = "UPDATE XuatChieu SET ma_phim=?, ma_phong=?, ngay_chieu=?, gio_chieu=?, gia_ve=? WHERE ma_xuat=?";
        XJdbc.executeUpdate(sql,
            entity.getMaPhim(), entity.getMaPhong(),
            new java.sql.Date(entity.getNgayChieu().getTime()), entity.getGioChieu(), entity.getGiaVe(),
            entity.getMaXuat()
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM XuatChieu WHERE ma_xuat=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<XuatChieu> findAll() {
        String sql = "SELECT * FROM XuatChieu";
        return selectBySql(sql);
    }

    @Override
    public XuatChieu findById(Integer id) {
        String sql = "SELECT * FROM XuatChieu WHERE ma_xuat=?";
        List<XuatChieu> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<XuatChieu> selectByPhimAndPhongAndNgay(Integer maPhim, String maPhong, Date ngayChieu) {
        String sql = "SELECT * FROM XuatChieu WHERE ma_phim = ? AND ma_phong = ? AND ngay_chieu = ?";
        return selectBySql(sql, maPhim, maPhong, new java.sql.Date(ngayChieu.getTime()));
    }

    private List<XuatChieu> selectBySql(String sql, Object... args) {
        List<XuatChieu> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                XuatChieu xc = new XuatChieu();
                xc.setMaXuat(rs.getInt("ma_xuat"));
                xc.setMaPhim(rs.getInt("ma_phim"));
                xc.setMaPhong(rs.getString("ma_phong"));
                xc.setNgayChieu(rs.getDate("ngay_chieu"));
                xc.setGioChieu(rs.getTime("gio_chieu")); // Lấy giờ là java.sql.Time
                xc.setGiaVe(rs.getDouble("gia_ve"));
                list.add(xc);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
