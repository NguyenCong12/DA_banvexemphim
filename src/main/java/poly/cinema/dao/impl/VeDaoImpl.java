/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import poly.cinema.util.XJdbc;

/**
 *
 * @author Admin
 */
public class VeDaoImpl {

    public Object[] getThongTinVe(int maXuat, String maGhe) {
        try (Connection conn = XJdbc.openConnection()) {
            String sql = """
    SELECT 
        g.ma_ghe,
        p.ten_phim,
        sc.ngay_chieu,
        sc.gio_chieu,
        pc.ma_phong,
        g.so_ghe AS ten_ghe,
        ISNULL(lg.phu_phi + sc.gia_ve, sc.gia_ve) AS gia
    FROM XuatChieu sc
    JOIN Phim p ON sc.ma_phim = p.ma_phim
    JOIN PhongChieu pc ON sc.ma_phong = pc.ma_phong
    JOIN Ghe g ON g.ma_phong = pc.ma_phong AND g.ma_ghe = ?
    LEFT JOIN LoaiGhe lg ON g.loai_ghe = lg.loai_ghe
    WHERE sc.ma_xuat = ?
""";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maGhe);
            ps.setInt(2, maXuat);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Object[]{
    rs.getInt(1),       // ma_ghe
    rs.getString(2),    // tên phim
    rs.getString(3),    // ngày chiếu
    rs.getString(4),    // giờ chiếu
    rs.getString(5),    // mã phòng
    rs.getString(6),    // tên ghế (I9 ← chính nó gây lỗi)
    rs.getDouble(7)     // giá vé ← bạn cần lấy cái này!
};


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
