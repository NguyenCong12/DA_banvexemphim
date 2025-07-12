/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.QuanLyPhongChieuDao;
import poly.cinema.entity.PhongChieu;
import poly.cinema.util.XJdbc;

/**
 *
 * @author Admin
 */
public class QuanLyPhongChieuDaoImpl implements QuanLyPhongChieuDao {

    private final String INSERT_SQL = "INSERT INTO PhongChieu (ma_phong, ten_phong) VALUES (?, ?)";
    private final String UPDATE_SQL = "UPDATE PhongChieu SET ten_phong = ? WHERE ma_phong = ?";
    private final String DELETE_SQL = "DELETE FROM PhongChieu WHERE ma_phong = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM PhongChieu";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM PhongChieu WHERE ma_phong = ?";

    @Override
    public PhongChieu create(PhongChieu entity) {
        XJdbc.executeUpdate(INSERT_SQL, entity.getMaPhong(), entity.getTenPhong());
        return entity;
    }

    @Override
    public void update(PhongChieu entity) {
        XJdbc.executeUpdate(UPDATE_SQL, entity.getTenPhong(), entity.getMaPhong());
    }

    @Override
    public List<PhongChieu> findAll() {
        List<PhongChieu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                PhongChieu pc = new PhongChieu();
                pc.setMaPhong(rs.getString("ma_phong"));
                pc.setTenPhong(rs.getString("ten_phong"));
                list.add(pc);
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
                        rs.getString("ma_phong"),
                        rs.getString("ten_phong")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void deleteByMaPhong(String maPhong) {
    String sql = "DELETE FROM PhongChieu WHERE ma_phong = ?";
    XJdbc.executeUpdate(sql, maPhong);
}

}
