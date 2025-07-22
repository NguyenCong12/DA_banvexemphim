/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.cinema.dao.LoaiGheDAO;
import poly.cinema.entity.LoaiGhe;
import poly.cinema.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class LoaiGheDAOImpl implements LoaiGheDAO {
    
    private final String INSERT_SQL = "INSERT INTO LoaiGhe (loai_ghe, phu_phi) VALUES (?, ?)";
    private final String UPDATE_SQL = "UPDATE LoaiGhe SET phu_phi = ? WHERE loai_ghe = ?";
    private final String DELETE_SQL = "DELETE FROM LoaiGhe WHERE loai_ghe = ?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM LoaiGhe WHERE loai_ghe = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM LoaiGhe";

    @Override
    public LoaiGhe create(LoaiGhe loaiGhe) {
        XJdbc.executeUpdate(INSERT_SQL,
            loaiGhe.getLoaiGhe(),
            loaiGhe.getPhuPhi());
        return loaiGhe;
    }

    @Override
    public void update(LoaiGhe loaiGhe) {
        XJdbc.executeUpdate(UPDATE_SQL,
            loaiGhe.getPhuPhi(),
            loaiGhe.getLoaiGhe());
    }

    @Override
    public void deleteById(String loaiGhe) {
        XJdbc.executeUpdate(DELETE_SQL, loaiGhe);
    }

    @Override
    public LoaiGhe findById(String loaiGhe) {
        List<LoaiGhe> list = selectBySql(SELECT_BY_ID_SQL, loaiGhe);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<LoaiGhe> findAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    protected List<LoaiGhe> selectBySql(String sql, Object... args) {
        List<LoaiGhe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                LoaiGhe entity = new LoaiGhe();
                entity.setLoaiGhe(rs.getString("loai_ghe"));
                entity.setPhuPhi(rs.getDouble("phu_phi"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn LoaiGhe", e);
        }
        return list;
    }
    
}
