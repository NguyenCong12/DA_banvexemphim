/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import poly.cinema.dao.LoaiPhimDao;
import poly.cinema.entity.LoaiPhim;
import poly.cinema.util.XJdbc;

/**
 *
 * @author Admin
 */
public class LoaiPhimDaoImpl implements LoaiPhimDao {

    private static final Map<Integer, String> CACHE = new ConcurrentHashMap<>();

    public static List<String> findAllTenLoai() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ten_loai FROM LoaiPhim";
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("ten_loai"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static String getTenLoaiById(int maLoai) {
        String cached = CACHE.get(maLoai);
        if (cached != null) {
            return cached;
        }

        String sql = "SELECT ten_loai FROM LoaiPhim WHERE ma_loai = ?";
        try (ResultSet rs = XJdbc.executeQuery(sql, maLoai)) {
            if (rs.next()) {
                String ten = rs.getString("ten_loai");
                if (ten == null || ten.isBlank()) {
                    ten = "Không rõ";
                }
                CACHE.put(maLoai, ten);
                return ten;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String fallback = "Loại #" + maLoai;
        CACHE.put(maLoai, fallback);
        return fallback;
    }

    private static final String INSERT_SQL = "INSERT INTO LoaiPhim (ten_loai) VALUES (?)";
    private static final String UPDATE_SQL = "UPDATE LoaiPhim SET ten_loai = ? WHERE ma_loai = ?";
    private static final String DELETE_SQL = "DELETE FROM LoaiPhim WHERE ma_loai = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM LoaiPhim";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM LoaiPhim WHERE ma_loai = ?";

    @Override
    public LoaiPhim create(LoaiPhim entity) {
        try {
            XJdbc.executeUpdate(INSERT_SQL, entity.getTenLoai());
            ResultSet rs = XJdbc.executeQuery("SELECT TOP 1 * FROM LoaiPhim ORDER BY ma_loai DESC");
            if (rs.next()) {
                return new LoaiPhim(rs.getInt("ma_loai"), rs.getString("ten_loai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(LoaiPhim entity) {
        XJdbc.executeUpdate(UPDATE_SQL, entity.getTenLoai(), entity.getMaLoai());
    }

    @Override
    public void deleteById(Integer id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<LoaiPhim> findAll() {
        List<LoaiPhim> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                list.add(new LoaiPhim(rs.getInt("ma_loai"), rs.getString("ten_loai")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public LoaiPhim findById(Integer id) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, id)) {
            if (rs.next()) {
                return new LoaiPhim(rs.getInt("ma_loai"), rs.getString("ten_loai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
