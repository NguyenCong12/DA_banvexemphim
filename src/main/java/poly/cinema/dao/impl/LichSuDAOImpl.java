/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import poly.cinema.dao.LichSuDAO;
import poly.cinema.entity.LichSu;
import poly.cinema.util.XJdbc;

/**
 *
 * @author NITRO
 */
public class LichSuDAOImpl implements LichSuDAO {

    @Override
    public List<LichSu> selectAll() {
        String sql = """
            SELECT hd.ma_hd, hd.ngay_lap, nv.ten_nv, p.ten_phim,
                   xc.ngay_chieu, xc.gio_chieu,
                   g.so_ghe, g.loai_ghe, xc.gia_ve,
                   mh.ten_hang, cthd.so_luong, mh.gia,
                   hd.tong_tien
            FROM HoaDon hd
            JOIN NhanVien nv ON hd.ma_nv = nv.ma_nv
            LEFT JOIN ChiTietHoaDon cthd ON hd.ma_hd = cthd.ma_hd
            LEFT JOIN XuatChieu xc ON cthd.ma_xuat = xc.ma_xuat
            LEFT JOIN Phim p ON xc.ma_phim = p.ma_phim
            LEFT JOIN Ghe g ON cthd.ma_ghe = g.ma_ghe
            LEFT JOIN MatHang mh ON cthd.ma_hang = mh.ma_hang
            ORDER BY hd.ngay_lap DESC
        """;

        return selectBySQL(sql);
    }

    @Override
    public List<LichSu> getByDate(Date begin, Date end) {
        String sql = """
            SELECT hd.ma_hd, hd.ngay_lap, nv.ten_nv, p.ten_phim,
                   xc.ngay_chieu, xc.gio_chieu,
                   g.so_ghe, g.loai_ghe, xc.gia_ve,
                   mh.ten_hang, cthd.so_luong, mh.gia,
                   hd.tong_tien
            FROM HoaDon hd
            JOIN NhanVien nv ON hd.ma_nv = nv.ma_nv
            LEFT JOIN ChiTietHoaDon cthd ON hd.ma_hd = cthd.ma_hd
            LEFT JOIN XuatChieu xc ON cthd.ma_xuat = xc.ma_xuat
            LEFT JOIN Phim p ON xc.ma_phim = p.ma_phim
            LEFT JOIN Ghe g ON cthd.ma_ghe = g.ma_ghe
            LEFT JOIN MatHang mh ON cthd.ma_hang = mh.ma_hang
            WHERE hd.ngay_lap BETWEEN ? AND ?
            ORDER BY hd.ngay_lap DESC
        """;

        return selectBySQL(sql, begin, end);
    }

    private List<LichSu> selectBySQL(String sql, Object... args) {
        List<LichSu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, args)) {
            while (rs.next()) {
                LichSu ls = new LichSu(
                        rs.getInt("ma_hd"),
                        rs.getTimestamp("ngay_lap"),
                        rs.getString("ten_nv"),
                        rs.getString("ten_phim"),
                        rs.getDate("ngay_chieu"),
                        rs.getTime("gio_chieu"),
                        rs.getString("so_ghe"),
                        rs.getString("loai_ghe"),
                        rs.getDouble("gia_ve"),
                        rs.getString("ten_hang"),
                        rs.getObject("so_luong") != null ? rs.getInt("so_luong") : null,
                        rs.getObject("gia") != null ? rs.getDouble("gia") : null,
                        rs.getDouble("tong_tien")
                );
                list.add(ls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
