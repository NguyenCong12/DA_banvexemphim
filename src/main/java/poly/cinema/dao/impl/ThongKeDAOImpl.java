package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import poly.cinema.dao.ThongKeDAO;
import poly.cinema.entity.ThongKe;
import poly.cinema.util.XJdbc;

public class ThongKeDAOImpl implements ThongKeDAO {

    @Override
    public List<ThongKe.DoanhThuPhim> getDoanhThuPhim(Date begin, Date end) {
        String sql = """
    SELECT p.ten_phim, SUM(xc.gia_ve) AS doanh_thu
    FROM HoaDon hd
    JOIN ChiTietVe ct ON hd.ma_hd = ct.ma_hd
    JOIN XuatChieu xc ON ct.ma_xuat = xc.ma_xuat
    JOIN Phim p ON xc.ma_phim = p.ma_phim
    WHERE hd.ngay_lap BETWEEN ? AND ?
    GROUP BY p.ten_phim
""";

        List<ThongKe.DoanhThuPhim> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, begin, end)) {
            while (rs.next()) {
                ThongKe.DoanhThuPhim tk = new ThongKe.DoanhThuPhim();
                tk.setTenPhim(rs.getString(1));
                tk.setDoanhThu(rs.getDouble(2));
                list.add(tk);
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn doanh thu phim", e);
        }
        return list;
    }

    @Override
    public List<ThongKe.DoanhThuSanPham> getDoanhThuSanPham(Date begin, Date end) {
        String sql = """
    SELECT mh.loai, SUM(ct.so_luong * ct.gia) AS doanh_thu
    FROM HoaDon hd
    JOIN ChiTietHang ct ON hd.ma_hd = ct.ma_hd
    JOIN MatHang mh ON ct.ma_hang = mh.ma_hang
    WHERE hd.ngay_lap BETWEEN ? AND ?
    GROUP BY mh.loai
""";

        List<ThongKe.DoanhThuSanPham> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, begin, end)) {
            while (rs.next()) {
                ThongKe.DoanhThuSanPham tk = new ThongKe.DoanhThuSanPham();
                tk.setLoai(rs.getString(1));
                tk.setDoanhThu(rs.getDouble(2));
                list.add(tk);
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn doanh thu sản phẩm", e);
        }
        return list;
    }
}
