/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.NguoiDungDAO;
import poly.cinema.entity.NguoiDung;
import poly.cinema.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class NguoiDungDAOImpl implements NguoiDungDAO {

    @Override
    public NguoiDung findByEmail(String email) {
        String sql = "SELECT * FROM NguoiDung WHERE email=?";
        List<NguoiDung> list = selectBySql(sql, email);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public NguoiDung create(NguoiDung entity) {
        String sql = "INSERT INTO NguoiDung (ten_nd, email, mat_khau, vai_tro, sdt, hoat_dong, anh_dai_dien) VALUES (?, ?, ?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
                entity.getTenNd(), entity.getEmail(), entity.getMatKhau(),
                entity.getVaiTro(), entity.getSdt(), entity.getHoatDong(), entity.getAnhDaiDien()
        );
        return entity;
    }

    @Override
    public void update(NguoiDung entity) {
        String sql = "UPDATE NguoiDung SET ten_nd=?, email=?, mat_khau=?, vai_tro=?, sdt=?, hoat_dong=?, anh_dai_dien=? WHERE ma_nd=?";
        XJdbc.executeUpdate(sql,
                entity.getTenNd(), entity.getEmail(), entity.getMatKhau(),
                entity.getVaiTro(), entity.getSdt(), entity.getHoatDong(), entity.getAnhDaiDien(),
                entity.getMaNd()
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM NguoiDung WHERE ma_nd=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<NguoiDung> findAll() {
        String sql = "SELECT * FROM NguoiDung";
        return selectBySql(sql);
    }

    @Override
    public NguoiDung findById(Integer id) {
        String sql = "SELECT * FROM NguoiDung WHERE ma_nd=?";
        List<NguoiDung> list = selectBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    private List<NguoiDung> selectBySql(String sql, Object... args) {
        List<NguoiDung> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNd(rs.getInt("ma_nd"));
                nd.setTenNd(rs.getString("ten_nd"));
                nd.setEmail(rs.getString("email"));
                nd.setMatKhau(rs.getString("mat_khau"));
                nd.setVaiTro(rs.getBoolean("vai_tro"));
                nd.setSdt(rs.getString("sdt"));
                nd.setHoatDong(rs.getBoolean("hoat_dong"));
                nd.setAnhDaiDien(rs.getString("anh_dai_dien"));
                list.add(nd);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
