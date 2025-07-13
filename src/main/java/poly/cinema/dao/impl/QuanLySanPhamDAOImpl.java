/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.QuanLySanPhamDAO;
import poly.cinema.entity.SanPham;
import poly.cinema.util.XJdbc;

/**
 *
 * @author NITRO
 */
public class QuanLySanPhamDAOImpl implements QuanLySanPhamDAO {

    private final String INSERT_SQL = "INSERT INTO MatHang (ten_hang, loai, gia, trang_thai, anh_hang) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE MatHang SET ten_hang=?, loai=?, gia=?, trang_thai=?, anh_hang=? WHERE ma_hang=?";
    private final String DELETE_SQL = "DELETE FROM MatHang WHERE ma_hang=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM MatHang";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM MatHang WHERE ma_hang=?";
    private final String SELECT_BY_LOAI_SQL = "SELECT * FROM MatHang WHERE loai=?";
    private final String SEARCH_BY_TEN_SQL = "SELECT * FROM MatHang WHERE ten_hang LIKE ?";

    @Override
    public SanPham create(SanPham sp) {
        // Thêm mới không cần truyền mã
        int rows = XJdbc.executeUpdate(INSERT_SQL,
                sp.getTenSanPham(),
                sp.getLoai(),
                sp.getGia(),
                sp.isTrangThai(),
                sp.getAnh()
        );

        // Lấy lại ID mới chèn (nếu cần)
        if (rows > 0) {
            Integer idMoi = XJdbc.getValue("SELECT SCOPE_IDENTITY()");
            sp.setMaSanPham(idMoi.toString()); // gán lại cho entity
            return sp;
        }
        return null;
    }

    @Override
    public void update(SanPham sp) {
        XJdbc.executeUpdate(UPDATE_SQL,
                sp.getTenSanPham(),
                sp.getLoai(),
                sp.getGia(),
                sp.isTrangThai(),
                sp.getAnh(),
                sp.getMaSanPham()
        );
    }

    @Override
    public void deleteById(String maSanPham) {
        XJdbc.executeUpdate(DELETE_SQL, maSanPham);
    }

    @Override
    public List<SanPham> findAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public SanPham findById(String maSanPham) {
        List<SanPham> list = selectBySql(SELECT_BY_ID_SQL, maSanPham);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<SanPham> findByLoai(String loai) {
        return selectBySql(SELECT_BY_LOAI_SQL, loai);
    }

    @Override
    public List<SanPham> searchByTen(String keyword) {
        return selectBySql(SEARCH_BY_TEN_SQL, "%" + keyword + "%");
    }

    private List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, args)) {
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getString("ma_hang"));
                sp.setTenSanPham(rs.getString("ten_hang"));
                sp.setLoai(rs.getString("loai"));
                sp.setGia(rs.getDouble("gia"));
                sp.setTrangThai(rs.getBoolean("trang_thai"));
                sp.setAnh(rs.getString("anh_hang"));
                list.add(sp);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn SanPham: " + e.getMessage(), e);
        }
        return list;
    }
}
