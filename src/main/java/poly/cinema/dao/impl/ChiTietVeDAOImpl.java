/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.ChiTietVeDAO;
import poly.cinema.entity.ChiTietVe;
import poly.cinema.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class ChiTietVeDAOImpl implements ChiTietVeDAO {
// Ghi dòng này để Git nhận ra file là mới

    @Override
    public List<String> getGheDaDat(String maXuat) {
        List<String> list = new ArrayList<>();
        try {
            String sql = "SELECT ma_ghe FROM ChiTietVe WHERE ma_xuat = ?";
            Connection conn = XJdbc.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maXuat);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("ma_ghe"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ChiTietVe> selectByMaHD(Integer maHD) {
        List<ChiTietVe> list = new ArrayList<>();
        // SQL: Chọn từ ChiTietVe nơi ma_hd khớp
        String sql = "SELECT * FROM ChiTietVe WHERE ma_hd = ?";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, maHD);
            while (rs.next()) {
                ChiTietVe ct = new ChiTietVe(
                        rs.getInt("ma_ctv"),
                        rs.getInt("ma_hd"),
                        rs.getInt("ma_xuat"),
                        rs.getInt("ma_ghe"),
                        rs.getDouble("gia_ve")
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
    public ChiTietVe create(ChiTietVe entity) {
        String sql = "INSERT INTO ChiTietVe (ma_hd, ma_xuat, ma_ghe, gia_ve) VALUES (?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
                entity.getMaHD(),
                entity.getMaXuat(),
                entity.getMaGhe(),
                entity.getGiaVe()
        );
        return entity;
    }

    @Override
    public void update(ChiTietVe entity) {
        String sql = "UPDATE ChiTietVe SET ma_hd=?, ma_xuat=?, ma_ghe=?, gia_ve=? WHERE ma_ctv=?";
        XJdbc.executeUpdate(sql,
                entity.getMaHD(),
                entity.getMaXuat(),
                entity.getMaGhe(),
                entity.getGiaVe(),
                entity.getMaCTV()
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM ChiTietVe WHERE ma_ctv=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<ChiTietVe> findAll() {
        String sql = "SELECT * FROM ChiTietVe";
        return selectBySql(sql);
    }

    @Override
    public ChiTietVe findById(Integer id) {
        String sql = "SELECT * FROM ChiTietVe WHERE ma_ctv=?";
        List<ChiTietVe> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    private List<ChiTietVe> selectBySql(String sql, Object... args) {
        List<ChiTietVe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                ChiTietVe ct = new ChiTietVe();
                ct.setMaCTV(rs.getInt("ma_ctv"));
                ct.setMaHD(rs.getInt("ma_hd"));
                ct.setMaXuat(rs.getInt("ma_xuat"));
                ct.setMaGhe(rs.getInt("ma_ghe"));
                ct.setGiaVe(rs.getDouble("gia_ve"));
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
}
