/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import poly.cinema.dao.QuanLyGheDao;
import poly.cinema.entity.QuanLyGhe;

/**
 *
 * @author Admin
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuanLyGheDaoImpl implements QuanLyGheDao {

    private final Connection conn;

    public QuanLyGheDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<QuanLyGhe> findByPhong(String maPhong) {
        List<QuanLyGhe> list = new ArrayList<>();
        String sql = "SELECT * FROM Ghe WHERE ma_phong = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhong);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                QuanLyGhe ghe = QuanLyGhe.builder()
                        .maPhong(rs.getString("ma_phong"))
                        .soGhe(rs.getString("so_ghe"))
                        .hang(rs.getString("hang"))
                        .cot(rs.getInt("cot"))
                        .trangThai(rs.getString("trang_thai"))
                        .build();
                list.add(ghe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateTrangThai(int maGhe, String trangThai) {
        String sql = "UPDATE Ghe SET trang_thai = ? WHERE ma_ghe = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ps.setInt(2, maGhe);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public QuanLyGhe create(QuanLyGhe ghe) {
        String sql = "INSERT INTO Ghe (ma_phong, so_ghe, hang, cot, trang_thai) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ghe.getMaPhong());
            ps.setString(2, ghe.getSoGhe());
            ps.setString(3, ghe.getHang());
            ps.setInt(4, ghe.getCot());
            ps.setString(5, ghe.getTrangThai());
            ps.executeUpdate();
            return ghe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(QuanLyGhe ghe) {
        String sql = "UPDATE Ghe SET ma_phong = ?, so_ghe = ?, hang = ?, cot = ?, trang_thai = ? WHERE so_ghe = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ghe.getMaPhong());
            ps.setString(2, ghe.getSoGhe());
            ps.setString(3, ghe.getHang());
            ps.setInt(4, ghe.getCot());
            ps.setString(5, ghe.getTrangThai());
            ps.setString(6, ghe.getSoGhe());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Ghe WHERE ma_ghe = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<QuanLyGhe> findAll() {
        List<QuanLyGhe> list = new ArrayList<>();
        String sql = "SELECT * FROM Ghe";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                QuanLyGhe ghe = QuanLyGhe.builder()
                        .maPhong(rs.getString("ma_phong"))
                        .soGhe(rs.getString("so_ghe"))
                        .hang(rs.getString("hang"))
                        .cot(rs.getInt("cot"))
                        .trangThai(rs.getString("trang_thai"))
                        .build();
                list.add(ghe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public QuanLyGhe findById(Integer id) {
        String sql = "SELECT * FROM Ghe WHERE ma_ghe = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return QuanLyGhe.builder()
                        .maPhong(rs.getString("ma_phong"))
                        .soGhe(rs.getString("so_ghe"))
                        .hang(rs.getString("hang"))
                        .cot(rs.getInt("cot"))
                        .trangThai(rs.getString("trang_thai"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
