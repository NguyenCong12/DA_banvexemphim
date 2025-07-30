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
import poly.cinema.util.XQuery;

/**
 *
 * @author Admin
 */
public class QuanLySuatChieuDaoImpl implements QuanLySuatChieuDao {

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
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public SuatChieu findById(Integer id) {
        List<SuatChieu> list = selectBySql(SELECT_BY_ID_SQL, id);
        return list.isEmpty() ? null : list.get(0);
    }

    private List<SuatChieu> selectBySql(String sql, Object... args) {
        List<SuatChieu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, args)) {
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private SuatChieu readFromResultSet(ResultSet rs) throws SQLException {
        return SuatChieu.builder()
                .maXuat(rs.getInt("ma_xuat"))
                .maPhim(rs.getInt("ma_phim"))
                .maPhong(rs.getString("ma_phong"))
                .ngayChieu(rs.getDate("ngay_chieu").toLocalDate()) // LocalDate
                .gioChieu(rs.getTime("gio_chieu").toLocalTime()) // LocalTime
                .giaVe(rs.getInt("gia_ve"))
                .build();
    }

    @Override
    public List<SuatChieu> findByNgayVaPhim(Date ngay, int maPhim) {
        String sql = "SELECT * FROM XuatChieu WHERE ma_phim = ? AND CAST(ngay_chieu AS DATE) = ?";
        return selectBySql(sql, maPhim, new java.sql.Date(ngay.getTime()));
    }

    @Override
    public List<SuatChieu> findByMaPhim(int maPhim) {
        String sql = "SELECT * FROM XuatChieu WHERE ma_phim = ?";
        return selectBySql(sql, maPhim);
    }

}
