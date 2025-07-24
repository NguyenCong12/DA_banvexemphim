package poly.cinema.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.QuanLyPhimDao;
import poly.cinema.entity.Phim;
import poly.cinema.util.XJdbc;
import poly.cinema.util.XQuery;

public class QuanLyPhimDaoImpl implements QuanLyPhimDao {

    /* =========================================================
       CRUD + FIND CƠ BẢN
       ========================================================= */
    private static final String INSERT_SQL = """
    INSERT INTO Phim (ten_phim, ma_loai, thoi_luong, mo_ta, ngay_khoi_chieu, trang_thai, hinh_anh)
    VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

    private static final String UPDATE_SQL = """
    UPDATE Phim
    SET ten_phim = ?, ma_loai = ?, thoi_luong = ?, mo_ta = ?, ngay_khoi_chieu = ?, trang_thai = ?, hinh_anh = ?
    WHERE ma_phim = ?
    """;

    private static final String DELETE_SQL = "DELETE FROM Phim WHERE ma_phim = ?";

    /**
     * SELECT chuẩn: alias cột snake_case -> camelCase tương ứng với field trong
     * entity (nếu dùng XQuery map theo tên cột). Nếu entity Phim vẫn dùng tên
     * snake_case, bạn có thể bỏ alias.
     */
    private static final String SELECT_ALL_SQL = """
    SELECT 
        p.ma_phim         AS maPhim,
        p.ten_phim        AS tenPhim,
        p.ma_loai         AS maLoai,
        p.thoi_luong      AS thoiLuong,
        p.mo_ta           AS moTa,
        p.ngay_khoi_chieu AS ngayKhoiChieu,
        p.trang_thai      AS trangThai,
        p.hinh_anh        AS hinhAnh,
        lp.ten_loai       AS tenLoai
    FROM Phim p
    JOIN LoaiPhim lp ON p.ma_loai = lp.ma_loai
    """;

    // ---- Các câu WHERE dùng tên bảng/cột thật, KHÔNG dùng alias ở trên ----
    private static final String SELECT_BY_ID_SQL
            = SELECT_ALL_SQL + " WHERE p.ma_phim = ?";

    private static final String SELECT_BY_TENPHIM_SQL
            = SELECT_ALL_SQL + " WHERE p.ten_phim LIKE ?";

    private static final String SELECT_BY_TRANGTHAI_SQL
            = SELECT_ALL_SQL + " WHERE p.trang_thai = ?";


    /* =========================================================
       IMPLEMENT DAO
       ========================================================= */
    @Override
    public Phim create(Phim entity) {
        try (Connection conn = XJdbc.openConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getTenPhim());
            stmt.setInt(2, entity.getMaLoai());
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
            throw new RuntimeException("Lỗi thêm phim.", e);
        }
        return entity;
    }

    @Override
    public void update(Phim entity) {
        Object[] values = {
            entity.getTenPhim(),
            entity.getMaLoai(),
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
        // *** FIX tên cột ***
        return XQuery.getEntityList(Phim.class, SELECT_BY_TRANGTHAI_SQL, trangThai);
    }


    /* =========================================================
       FIND PHIM CHIẾU HÔM NAY
       ========================================================= */
    @Override
    public List<Phim> findPhimChieuHomNay() {
        String sql = """
            SELECT DISTINCT p.*
            FROM Phim p
            JOIN XuatChieu x ON p.ma_phim = x.ma_phim
            WHERE CAST(x.ngay_chieu AS DATE) = CAST(GETDATE() AS DATE)
            """;
        return select(sql);
    }


    /* =========================================================
       FIND PHIM CHIẾU THEO NGÀY
       ========================================================= */
    @Override
    public List<Phim> findPhimChieuTheoNgay(LocalDate ngay) {
        String sql = """
            SELECT DISTINCT p.*
            FROM Phim p
            JOIN XuatChieu x ON p.ma_phim = x.ma_phim
            WHERE CAST(x.ngay_chieu AS DATE) = ?
            """;
        return select(sql, java.sql.Date.valueOf(ngay));
    }


    /* =========================================================
       HÀM SELECT CHUNG MAP -> Phim
       ========================================================= */
    private List<Phim> select(String sql, Object... args) {
        List<Phim> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, args)) {
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi truy vấn Phim.", e);
        }
        return list;
    }

    private Phim readFromResultSet(ResultSet rs) throws SQLException {
        Phim phim = new Phim();
        phim.setMaPhim(rs.getInt("ma_phim"));
        phim.setTenPhim(rs.getString("ten_phim"));
        phim.setMaLoai(rs.getInt("ma_loai"));
        phim.setThoiLuong(rs.getInt("thoi_luong"));
        phim.setMoTa(rs.getString("mo_ta"));
        phim.setNgayKhoiChieu(rs.getDate("ngay_khoi_chieu"));
        phim.setTrangThai(rs.getString("trang_thai"));
        phim.setHinhAnh(rs.getString("hinh_anh"));
        return phim;
    }

    public String getTenLoaiByMaLoai(int maLoai) {
        String sql = "SELECT ten_loai FROM LoaiPhim WHERE ma_loai = ?";
        try (
                Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maLoai);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("ten_loai");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isTenPhimTrung(String tenPhim) {
        String sql = "SELECT COUNT(*) FROM Phim WHERE ten_phim = ?";
        try (
                Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenPhim);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kiểm tra trùng tên phim.", e);
        }
        return false;
    }

}
