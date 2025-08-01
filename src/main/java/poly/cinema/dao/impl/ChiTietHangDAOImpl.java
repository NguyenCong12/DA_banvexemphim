/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.ChiTietHangDAO;
import poly.cinema.entity.ChiTietHang;
import poly.cinema.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class ChiTietHangDAOImpl implements ChiTietHangDAO {
// Ghi dòng này để Git nhận ra file là mới

    @Override
    public List<ChiTietHang> selectByMaHD(Integer maHD) {
        List<ChiTietHang> list = new ArrayList<>();

        String sql = "SELECT * FROM ChiTietHang WHERE ma_hd = ?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, maHD);
            while (rs.next()) {
                ChiTietHang ct = new ChiTietHang(
                        rs.getInt("ma_cth"),
                        rs.getInt("ma_hd"),
                        rs.getInt("ma_hang"),
                        rs.getInt("so_luong"),
                        rs.getDouble("gia"),
                        rs.getDouble("thanh_tien") // Lấy cột được duy trì
                );
                list.add(ct);
            }
            if (rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ChiTietHang create(ChiTietHang entity) {
        String sql = "INSERT INTO ChiTietHang (ma_hd, ma_hang, so_luong, gia) VALUES (?, ?, ?, ?)";
        int rows = XJdbc.executeUpdate(sql,
                entity.getMaHD(),
                entity.getMaHang(),
                entity.getSoLuong(),
                entity.getGia()
        );

        if (rows > 0) {
            Integer idMoi = XJdbc.getValue("SELECT SCOPE_IDENTITY()");
            entity.setMaCTH(idMoi);
        }
        return entity;
    }

    @Override
    public void update(ChiTietHang entity) {
        String sql = "UPDATE ChiTietHang SET ma_hd=?, ma_hang=?, so_luong=?, gia=? WHERE ma_cth=?";
        XJdbc.executeUpdate(sql,
                entity.getMaHD(),
                entity.getMaHang(),
                entity.getSoLuong(),
                entity.getGia(),
                entity.getMaCTH()
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM ChiTietHang WHERE ma_cth=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<ChiTietHang> findAll() {
        String sql = "SELECT * FROM ChiTietHang";
        return selectBySql(sql);
    }

    @Override
    public ChiTietHang findById(Integer id) {
        String sql = "SELECT * FROM ChiTietHang WHERE ma_cth=?";
        List<ChiTietHang> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    private List<ChiTietHang> selectBySql(String sql, Object... args) {
        List<ChiTietHang> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                ChiTietHang ct = new ChiTietHang();
                ct.setMaCTH(rs.getInt("ma_cth"));
                ct.setMaHD(rs.getInt("ma_hd"));
                ct.setMaHang(rs.getInt("ma_hang"));
                ct.setSoLuong(rs.getInt("so_luong"));
                ct.setGia(rs.getDouble("gia"));
                ct.setThanhTien(rs.getDouble("thanh_tien"));
                list.add(ct);
            }
            if (rs.getStatement() != null && rs.getStatement().getConnection() != null) {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void themChiTietSanPham(int maHoaDon, int maHang, double gia, int soLuong) {
    String sql = "INSERT INTO ChiTietHang (ma_hd, ma_hang, so_luong, gia) VALUES (?, ?, ?, ?)";
    XJdbc.executeUpdate(sql, maHoaDon, maHang, soLuong, gia); // đúng thứ tự
}


}
