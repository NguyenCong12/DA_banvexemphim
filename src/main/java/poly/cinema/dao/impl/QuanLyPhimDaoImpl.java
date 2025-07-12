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
import java.util.List;
import poly.cinema.dao.QuanLyPhimDao;
import poly.cinema.entity.Phim;
import poly.cinema.util.XJdbc;
import poly.cinema.util.XQuery;

/**
 *
 * @author Admin
 */

public class QuanLyPhimDaoImpl implements QuanLyPhimDao {

    private final String INSERT_SQL = """
        INSERT INTO Phim (ten_phim, the_loai, thoi_luong, mo_ta, ngay_khoi_chieu, trang_thai, hinh_anh)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

    private final String UPDATE_SQL = """
        UPDATE Phim
        SET ten_phim = ?, the_loai = ?, thoi_luong = ?, mo_ta = ?, ngay_khoi_chieu = ?, trang_thai = ?, hinh_anh = ?
        WHERE ma_phim = ?
    """;

    private final String DELETE_SQL = "DELETE FROM Phim WHERE ma_phim = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM Phim";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM Phim WHERE ma_phim = ?";
    private final String SELECT_BY_TENPHIM_SQL = "SELECT * FROM Phim WHERE ten_phim LIKE ?";
    private final String SELECT_BY_TRANGTHAI_SQL = "SELECT * FROM Phim WHERE trang_thai = ?";

    @Override
    public Phim create(Phim entity) {
        try (
            Connection conn = XJdbc.openConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, entity.getTenPhim());
            stmt.setString(2, entity.getTheLoai());
            stmt.setInt(3, entity.getThoiLuong());
            stmt.setString(4, entity.getMoTa());
            stmt.setDate(5, new java.sql.Date(entity.getNgayKhoiChieu().getTime()));
            stmt.setString(6, entity.getTrangThai());
            stmt.setString(7, entity.getHinhAnh());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setMaPhim(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void update(Phim entity) {
        Object[] values = {
            entity.getTenPhim(),
            entity.getTheLoai(),
            entity.getThoiLuong(),
            entity.getMoTa(),
            new java.sql.Date(entity.getNgayKhoiChieu().getTime()),
            entity.getTrangThai(),
            entity.getHinhAnh(),
            entity.getMaPhim()
        };
        XJdbc.executeUpdate(UPDATE_SQL, values);
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<Phim> findAll() {
        return XQuery.getEntityList(Phim.class, SELECT_ALL_SQL);
    }

    @Override
    public Phim findById(Integer id) {
        return XQuery.getSingleBean(Phim.class, SELECT_BY_ID_SQL, id);
    }

    @Override
    public List<Phim> findByTenPhim(String keyword) {
        return XQuery.getEntityList(Phim.class, SELECT_BY_TENPHIM_SQL, "%" + keyword + "%");
    }

    @Override
    public List<Phim> findByTrangThai(String trangThai) {
        return XQuery.getEntityList(Phim.class, SELECT_BY_TRANGTHAI_SQL, trangThai);
    }
}


