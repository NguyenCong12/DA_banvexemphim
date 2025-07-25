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
import poly.cinema.dao.QuanLyPhongChieuDao;
import poly.cinema.entity.PhongChieu;
import poly.cinema.entity.QuanLyGhe;
import poly.cinema.util.XJdbc;

/**
 *
 * @author Admin
 */
public class QuanLyPhongChieuDaoImpl implements QuanLyPhongChieuDao {

    private final String INSERT_SQL = "INSERT INTO PhongChieu (ma_phong, ten_phong, so_hang, so_cot) VALUES (?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE PhongChieu SET ten_phong = ?, so_hang = ?, so_cot = ? WHERE ma_phong = ?";
    private final String DELETE_SQL = "DELETE FROM PhongChieu WHERE ma_phong = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM PhongChieu";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM PhongChieu WHERE ma_phong = ?";

    @Override
    public PhongChieu create(PhongChieu entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaPhong(),
                entity.getTenPhong(),
                entity.getSoHang(),
                entity.getSoCot()
        );
        return entity;
    }

    @Override
    public void update(PhongChieu entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenPhong(),
                entity.getSoHang(),
                entity.getSoCot(),
                entity.getMaPhong()
        );
    }

    @Override
    public List<PhongChieu> findAll() {
        List<PhongChieu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                PhongChieu pc = new PhongChieu();
                pc.setMaPhong(rs.getString("ma_phong"));
                pc.setTenPhong(rs.getString("ten_phong"));
                pc.setSoHang(rs.getInt("so_hang"));
                pc.setSoCot(rs.getInt("so_cot"));
                list.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteById(Integer id) {
        // Không phù hợp vì ma_phong là String, nên dùng deleteByMaPhong()
        throw new UnsupportedOperationException("Không dùng deleteById với kiểu Integer cho ma_phong");
    }

    @Override
    public PhongChieu findById(Integer id) {
        // Không phù hợp vì ma_phong là String
        throw new UnsupportedOperationException("Không dùng findById với kiểu Integer cho ma_phong");
    }

    // ✅ Đúng
    public void deleteByMaPhong(String maPhong) {
        XJdbc.executeUpdate(DELETE_SQL, maPhong);
    }

    // ✅ Thêm nếu muốn tìm bằng String
    public PhongChieu findByMaPhong(String maPhong) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, maPhong)) {
            if (rs.next()) {
                return PhongChieu.builder()
                        .maPhong(rs.getString("ma_phong"))
                        .tenPhong(rs.getString("ten_phong"))
                        .soHang(rs.getInt("so_hang"))
                        .soCot(rs.getInt("so_cot"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private final String SELECT_BY_ID_SQL1 = "SELECT * FROM PhongChieu WHERE ma_phong = ?";

    @Override
    public PhongChieu findById(String maPhong) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL1, maPhong)) {
            if (rs.next()) {
                return new PhongChieu(
                        rs.getString("ma_phong"),
                        rs.getString("ten_phong"),
                        rs.getInt("so_hang"),
                        rs.getInt("so_cot")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String maPhong) {
        String sql = "DELETE FROM PhongChieu WHERE ma_phong = ?";
        XJdbc.executeUpdate(sql, maPhong);
    }

}
