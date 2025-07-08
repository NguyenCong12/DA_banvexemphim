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

    String createSql = "INSERT INTO NhanVien(ten_nv, email, mat_khau, vai_tro, sdt, hoat_dong, anh_nv) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE NhanVien SET ten_nv=?, email=?, mat_khau=?, vai_tro=?, sdt=?, hoat_dong=?, anh_nv=? WHERE ma_nv=?";
    String deleteSql = "DELETE FROM NhanVien WHERE ma_nv=?";
    String findAllSql = "SELECT * FROM NhanVien";
    String findByIdSql = "SELECT * FROM NhanVien WHERE ma_nv=?";

    @Override
    public User create(User entity) {
        XJdbc.executeUpdate(createSql,
            entity.getTenNv(),
            entity.getEmail(),
            entity.getMatKhau(),
            entity.isVaiTro(),
            entity.getSdt(),
            entity.isHoatDong(),
            entity.getAnhNv()
        );
        return entity;
    }

    @Override
    public void update(User entity) {
        XJdbc.executeUpdate(updateSql,
            entity.getTenNv(),
            entity.getEmail(),
            entity.getMatKhau(),
            entity.isVaiTro(),
            entity.getSdt(),
            entity.isHoatDong(),
            entity.getAnhNv(),
            entity.getMaNv()
        );
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteSql, Integer.parseInt(id));
    }

    @Override
    public List<User> findAll() {
        return XQuery.getEntityList(User.class, findAllSql);
    }

    @Override
    public User findById(String id) {
        return XQuery.getSingleBean(User.class, findByIdSql, Integer.parseInt(id));
    }

    @Override
    public User findByUsername(String tenNv) {
        String sql = "SELECT * FROM NhanVien WHERE ten_nv = ?";
        return XQuery.getSingleBean(User.class, sql, tenNv);
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM NhanVien WHERE email = ?";
        return XQuery.getSingleBean(User.class, sql, email);
    }
}




