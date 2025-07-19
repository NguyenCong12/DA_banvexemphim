/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.MatHangDAO;
import poly.cinema.entity.MatHang;
import poly.cinema.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class MatHangDAOImpl implements MatHangDAO {
// Ghi dòng này để Git nhận ra file là mới
    @Override
    public MatHang create(MatHang entity) {
        String sql = "INSERT INTO MatHang (ten_hang, loai, gia, anh_hang, trang_thai) VALUES (?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
                entity.getTenHang(), entity.getLoai(), entity.getGia(),
                entity.getAnhHang(), entity.getTrangThai()
        );
        return entity;
    }

    @Override
    public void update(MatHang entity) {
        String sql = "UPDATE MatHang SET ten_hang=?, loai=?, gia=?, anh_hang=?, trang_thai=? WHERE ma_hang=?";
        XJdbc.executeUpdate(sql,
                entity.getTenHang(), entity.getLoai(), entity.getGia(),
                entity.getAnhHang(), entity.getTrangThai(), entity.getMaHang()
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM MatHang WHERE ma_hang=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<MatHang> findAll() {
        String sql = "SELECT * FROM MatHang";
        return selectBySql(sql);
    }

    @Override
    public MatHang findById(Integer id) {
        String sql = "SELECT * FROM MatHang WHERE ma_hang=?";
        List<MatHang> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    private List<MatHang> selectBySql(String sql, Object... args) {
        List<MatHang> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                MatHang mh = new MatHang();
                mh.setMaHang(rs.getInt("ma_hang"));
                mh.setTenHang(rs.getString("ten_hang"));
                mh.setLoai(rs.getString("loai"));
                mh.setGia(rs.getDouble("gia"));
                mh.setAnhHang(rs.getString("anh_hang"));
                mh.setTrangThai(rs.getBoolean("trang_thai"));
                list.add(mh);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
