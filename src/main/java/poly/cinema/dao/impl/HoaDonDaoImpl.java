/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import poly.cinema.dao.HoaDonDAO;
import poly.cinema.entity.HoaDon;
import poly.cinema.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class HoaDonDaoImpl implements HoaDonDAO {

    private static final String INSERT_SQL = """
        INSERT INTO HoaDon (ma_nd, ngay_lap, tong_tien, trang_thai)
        VALUES (?, ?, ?, ?)
        """;

    private static final String UPDATE_SQL = """
        UPDATE HoaDon
        SET ma_nd = ?, ngay_lap = ?, tong_tien = ?, trang_thai = ?
        WHERE ma_hd = ?
        """;

    private static final String DELETE_SQL = "DELETE FROM HoaDon WHERE ma_hd = ?";
    private static final String SELECT_ALL_SQL = """
        SELECT ma_hd, ma_nd, ngay_lap, tong_tien, trang_thai
        FROM HoaDon
        ORDER BY ma_hd DESC
        """;
    private static final String SELECT_BY_ID_SQL = SELECT_ALL_SQL + " WHERE ma_hd = ?";
    private static final String SELECT_BY_USER_SQL = SELECT_ALL_SQL + " WHERE ma_nd = ?";
    private static final String SELECT_BY_DATERANGE_SQL = """
        SELECT ma_hd, ma_nd, ngay_lap, tong_tien, trang_thai
        FROM HoaDon
        WHERE ngay_lap BETWEEN ? AND ?
        ORDER BY ngay_lap
        """;
    private static final String SUM_BY_DATERANGE_SQL = """
        SELECT SUM(tong_tien) AS total
        FROM HoaDon
        WHERE ngay_lap BETWEEN ? AND ?
        """;
    private static final String UPDATE_TRANGTHAI_SQL = """
        UPDATE HoaDon SET trang_thai = ? WHERE ma_hd = ?
        """;

    @Override
    public HoaDon create(HoaDon entity) {
        if (entity.getNgayLap() == null) {
            entity.setNgayLap(new Date()); // đảm bảo không null
        }
        if (entity.getTrangThai() == null || entity.getTrangThai().isBlank()) {
            entity.setTrangThai("Đã thanh toán"); // hoặc để DB default
        }

        try (Connection cn = XJdbc.openConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, entity.getMaNguoiDung());
            ps.setTimestamp(2, new java.sql.Timestamp(entity.getNgayLap().getTime()));
            ps.setDouble(3, entity.getTongTien());
            ps.setString(4, entity.getTrangThai());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setMaHD(rs.getInt(1));
                }
            }
            return entity;
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi thêm hóa đơn", ex);
        }
    }

    @Override
    public void update(HoaDon entity) {
        if (entity.getNgayLap() == null) {
            entity.setNgayLap(new Date());
        }
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getMaNguoiDung(),
                new java.sql.Timestamp(entity.getNgayLap().getTime()),
                entity.getTongTien(),
                entity.getTrangThai(),
                entity.getMaHD()
        );
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<HoaDon> findAll() {
        return select(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon findById(Integer id) {
        List<HoaDon> list = select(SELECT_BY_ID_SQL, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<HoaDon> findByUser(Integer maNguoiDung) {
        return select(SELECT_BY_USER_SQL, maNguoiDung);
    }

    public List<HoaDon> findByDateRange(Date from, Date to) {
        // Chuẩn hóa: từ 00:00 đến 23:59:59
        Date fromNorm = from;
        Date toNorm = to;
        return select(SELECT_BY_DATERANGE_SQL,
                new java.sql.Timestamp(fromNorm.getTime()),
                new java.sql.Timestamp(toNorm.getTime()));
    }

    public double sumByDateRange(Date from, Date to) {
        try (ResultSet rs = XJdbc.executeQuery(SUM_BY_DATERANGE_SQL,
                new java.sql.Timestamp(from.getTime()),
                new java.sql.Timestamp(to.getTime()))) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tính tổng doanh thu", e);
        }
        return 0;
    }

    public void updateTrangThai(int maHD, String trangThai) {
        XJdbc.executeUpdate(UPDATE_TRANGTHAI_SQL, trangThai, maHD);
    }

    /* ================== CORE SELECT MAPPER ================== */
    private List<HoaDon> select(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, args)) {
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt("ma_hd"));
                hd.setMaNguoiDung(rs.getInt("ma_nd"));
                hd.setNgayLap(rs.getTimestamp("ngay_lap"));
                hd.setTongTien(rs.getDouble("tong_tien"));
                hd.setTrangThai(rs.getString("trang_thai"));
                list.add(hd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi truy vấn hóa đơn", e);
        }
        return list;
    }
}

