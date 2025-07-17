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

    private final String SELECT_ALL_SQL = """
        SELECT 
            hd.ma_hd,
            hd.ngay_lap,
            nd.ten_nd AS tenNhanVien,
            hd.tong_tien
        FROM HoaDon hd
        JOIN NguoiDung nd ON nd.ma_nd = hd.ma_nd
    """;

    private final String SELECT_BY_DATE_SQL = SELECT_ALL_SQL + " WHERE hd.ngay_lap BETWEEN ? AND ?";

    @Override
    public List<LichSu> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public List<LichSu> getByDate(Date begin, Date end) {
        return selectBySql(SELECT_BY_DATE_SQL, begin, end);
    }

    private List<LichSu> selectBySql(String sql, Object... args) {
        List<LichSu> list = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                LichSu ls = new LichSu();
                ls.setMaHd(rs.getInt("ma_hd"));
                ls.setNgayLap(rs.getTimestamp("ngay_lap"));
                ls.setTenNhanVien(rs.getString("tenNhanVien"));
                ls.setTongTien(rs.getDouble("tong_tien"));

                list.add(ls);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi truy vấn LichSu: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.getStatement().getConnection().close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return list;
    }
}
