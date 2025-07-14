/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.QuanLySuatChieuDao;
import poly.cinema.entity.SuatChieu;
import poly.cinema.util.XJdbc;


/**
 *
 * @author Admin
 */
public class QuanLySuatChieuDaoImpl  implements QuanLySuatChieuDao{
private final String INSERT_SQL = "INSERT INTO XuatChieu (ma_phim, ma_phong, ngay_chieu, gio_chieu, gia_ve) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE XuatChieu SET ma_phim = ?, ma_phong = ?, ngay_chieu = ?, gio_chieu = ?, gia_ve = ? WHERE ma_xuat = ?";
    private final String DELETE_SQL = "DELETE FROM XuatChieu WHERE ma_xuat = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM XuatChieu";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM XuatChieu WHERE ma_xuat = ?";

    @Override
    public SuatChieu create(SuatChieu entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaPhim(),
                entity.getMaPhong(),
                Date.valueOf(entity.getNgayChieu()),
                Time.valueOf(entity.getGioChieu()),
                entity.getGiaVe());
        return entity;
    }

    @Override
    public void update(SuatChieu entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getMaPhim(),
                entity.getMaPhong(),
                Date.valueOf(entity.getNgayChieu()),
                Time.valueOf(entity.getGioChieu()),
                entity.getGiaVe(),
                entity.getMaXuat());
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<SuatChieu> findAll() {
        List<SuatChieu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SuatChieu findById(Integer id) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, id)) {
            if (rs.next()) {
                return readFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SuatChieu readFromResultSet(ResultSet rs) throws SQLException {
        return SuatChieu.builder()
                .maXuat(rs.getInt("ma_xuat"))
                .maPhim(rs.getInt("ma_phim"))
                .maPhong(rs.getString("ma_phong"))
                .ngayChieu(rs.getDate("ngay_chieu").toLocalDate())  // LocalDate
                .gioChieu(rs.getTime("gio_chieu").toLocalTime())    // LocalTime
                .giaVe(rs.getBigDecimal("gia_ve"))
                .build();
    }
}
