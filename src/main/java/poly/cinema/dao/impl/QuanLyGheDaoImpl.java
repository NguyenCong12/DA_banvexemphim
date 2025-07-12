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
import poly.cinema.util.XJdbc;
import poly.cinema.util.XQuery;

public class QuanLyGheDaoImpl implements QuanLyGheDao {

    private final String INSERT_SQL = """
    INSERT INTO Ghe (ma_phong, so_ghe, hang, cot, loai_ghe, trang_thai)
    VALUES (?, ?, ?, ?, ?, ?)
""";

    private final String UPDATE_SQL = """
    UPDATE Ghe
    SET ma_phong = ?, so_ghe = ?, hang = ?, cot = ?, loai_ghe = ?, trang_thai = ?
    WHERE ma_ghe = ?
""";

    private final String DELETE_SQL = "DELETE FROM Ghe WHERE ma_ghe = ?";

    private final String SELECT_ALL_SQL = """
SELECT 
    ma_ghe AS maGhe,
    ma_phong AS maPhong,
    so_ghe AS soGhe,
    hang AS hang,
    cot AS cot,
    loai_ghe AS loaiGhe,
    trang_thai AS trangThai
FROM Ghe
""";

// ✅ Phải dùng tên cột gốc trong WHERE, không dùng alias!
    private final String SELECT_BY_ID_SQL = SELECT_ALL_SQL + " WHERE ma_ghe = ?";
    private final String SELECT_BY_PHONG_SQL = SELECT_ALL_SQL + " WHERE ma_phong = ?";
    private final String SELECT_BY_SOGHE_SQL = SELECT_ALL_SQL + " WHERE so_ghe = ?";

// Câu này vẫn đúng vì không dùng alias:
    private final String FIND_BY_SOGHE_AND_MAPHONG_SQL = "SELECT * FROM Ghe WHERE so_ghe = ? AND ma_phong = ?";

    @Override
    public QuanLyGhe findBySoGheAndPhong(String soGhe, String maPhong) {
        try (
                Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(FIND_BY_SOGHE_AND_MAPHONG_SQL)) {
            ps.setString(1, soGhe);
            ps.setString(2, maPhong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return QuanLyGhe.builder()
                            .maGhe(rs.getInt("ma_ghe"))
                            .soGhe(rs.getString("so_ghe"))
                            .hang(rs.getString("hang"))
                            .cot(rs.getInt("cot"))
                            .loaiGhe(rs.getString("loai_ghe"))
                            .trangThai(rs.getString("trang_thai"))
                            .maPhong(rs.getString("ma_phong"))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QuanLyGhe create(QuanLyGhe ghe) {
        try (
                Connection conn = XJdbc.openConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ghe.getMaPhong());
            stmt.setString(2, ghe.getSoGhe());
            stmt.setString(3, ghe.getHang());
            stmt.setInt(4, ghe.getCot());
            stmt.setString(5, ghe.getLoaiGhe());
            stmt.setString(6, ghe.getTrangThai());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ghe.setMaGhe(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ghe;
    }

    @Override
    public void update(QuanLyGhe ghe) {
        Object[] args = {
            ghe.getMaPhong(),
            ghe.getSoGhe(),
            ghe.getHang(),
            ghe.getCot(),
            ghe.getLoaiGhe(),
            ghe.getTrangThai(),
            ghe.getMaGhe()
        };
        XJdbc.executeUpdate(UPDATE_SQL, args);
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<QuanLyGhe> findAll() {
        return XQuery.getEntityList(QuanLyGhe.class, SELECT_ALL_SQL);
    }

    @Override
    public QuanLyGhe findById(Integer id) {
        return XQuery.getSingleBean(QuanLyGhe.class, SELECT_BY_ID_SQL, id);
    }

    @Override
    public List<QuanLyGhe> findByPhong(String maPhong) {
        return XQuery.getEntityList(QuanLyGhe.class, SELECT_BY_PHONG_SQL, maPhong);
    }

    @Override
    public QuanLyGhe findBySoGhe(String soGhe) {
        return XQuery.getSingleBean(QuanLyGhe.class, SELECT_BY_SOGHE_SQL, soGhe);
    }

    @Override
    public boolean updateTrangThai(int maGhe, String trangThai) {
        String sql = "UPDATE Ghe SET trang_thai = ? WHERE ma_ghe = ?";
        return XJdbc.executeUpdate(sql, trangThai, maGhe) > 0;
    }

    @Override
    public List<String> getAllMaPhong() {
        List<String> result = new ArrayList<>();
        String sql = "SELECT DISTINCT ma_phong FROM Ghe";
        try (
                Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                result.add(rs.getString("ma_phong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
