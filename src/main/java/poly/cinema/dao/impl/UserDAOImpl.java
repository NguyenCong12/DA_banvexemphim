/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.util.List;
import poly.cinema.dao.UserDAO;
import poly.cinema.entity.User;
import poly.cinema.util.XJdbc;
import poly.cinema.util.XQuery;

/**
 *
 * @author KhanhLinh
 */
public class UserDAOImpl implements UserDAO {

    String createSql = "INSERT INTO NguoiDung(ten_nd, email, mat_khau, vai_tro, sdt, hoat_dong, anh_dai_dien) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE NguoiDung SET ten_nd=?, email=?, mat_khau=?, vai_tro=?, sdt=?, hoat_dong=?, anh_dai_dien=? WHERE ma_nd=?";
    String findAllSql = "SELECT * FROM NguoiDung";

    @Override
    public User create(User entity) {
        XJdbc.executeUpdate(createSql,
            entity.getTen_nd(),
            entity.getEmail(),
            entity.getMat_khau(),
            entity.isVai_tro(),
            entity.getSdt(),
            entity.isHoat_dong(),
            entity.getAnh_dai_dien()
        );
        return entity;
    }

    @Override
    public void update(User entity) {
        XJdbc.executeUpdate(updateSql,
            entity.getTen_nd(),
            entity.getEmail(),
            entity.getMat_khau(),
            entity.isVai_tro(),
            entity.getSdt(),
            entity.isHoat_dong(),
            entity.getAnh_dai_dien(),
            entity.getMa_nd()
        );
    }

    @Override
    public void deleteById(String email) {
        String sql = "DELETE FROM NguoiDung WHERE email = ?";
        XJdbc.executeUpdate(sql, email);
    }

    @Override
    public List<User> findAll() {
        return XQuery.getEntityList(User.class, findAllSql);
    }

    @Override
    public User findById(String email) {
        String sql = "SELECT * FROM NguoiDung WHERE email = ?";
        return XQuery.getSingleBean(User.class, sql, email);
    }

    @Override
    public User findByUsername(String tenNd) {
        String sql = "SELECT * FROM NguoiDung WHERE ten_nd = ?";
        return XQuery.getSingleBean(User.class, sql, tenNd);
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM NguoiDung WHERE email = ?";
        return XQuery.getSingleBean(User.class, sql, email);
    }
}

