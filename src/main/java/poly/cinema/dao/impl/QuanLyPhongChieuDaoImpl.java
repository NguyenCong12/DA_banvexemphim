package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.QuanLyPhongChieuDao;
import poly.cinema.entity.PhongChieu;
import poly.cinema.util.XJdbc;

public class QuanLyPhongChieuDaoImpl implements QuanLyPhongChieuDao {

    private final String INSERT_SQL = "INSERT INTO PhongChieu (ten_phong, so_hang, so_cot) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE PhongChieu SET ten_phong = ?, so_hang = ?, so_cot = ? WHERE ma_phong = ?";
    private final String DELETE_SQL = "DELETE FROM PhongChieu WHERE ma_phong = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM PhongChieu";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM PhongChieu WHERE ma_phong = ?";

    @Override
    public PhongChieu create(PhongChieu entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getTenPhong(),
                entity.getSoHang(),
                entity.getSoCot()
        );
        return entity;
    }

    @Override
    public void update(PhongChieu entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenPhong(),
                entity.getSoHang(),
                entity.getSoCot(),
                entity.getMaPhong()
        );
    }

    @Override
    public List<PhongChieu> findAll() {
        List<PhongChieu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                list.add(new PhongChieu(
                        rs.getInt("ma_phong"),
                        rs.getString("ten_phong"),
                        rs.getInt("so_hang"),
                        rs.getInt("so_cot")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public PhongChieu findById(Integer id) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, id)) {
            if (rs.next()) {
                return new PhongChieu(
                        rs.getInt("ma_phong"),
                        rs.getString("ten_phong"),
                        rs.getInt("so_hang"),
                        rs.getInt("so_cot")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm hàm tìm kiếm theo String (nếu cần dùng chuỗi làm ID)
    @Override
    public PhongChieu findById(String maPhong) {
        try {
            int id = Integer.parseInt(maPhong);
            return findById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Xóa theo String (ép về int)
    @Override
    public void delete(String maPhong) {
        try {
            int id = Integer.parseInt(maPhong);
            deleteById(id);
        } catch (NumberFormatException e) {
            // Không xóa được nếu không phải số
        }
    }

    // Hàm lấy ID vừa insert (nếu cần)
    public int getLastInsertedId() {
        String sql = "SELECT IDENT_CURRENT('PhongChieu') AS last_id";
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("last_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
