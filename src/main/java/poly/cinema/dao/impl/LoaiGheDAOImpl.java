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
    
    private final String INSERT_SQL = "INSERT INTO LoaiGhe (MaLoaiGhe, TenLoaiGhe, Gia) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE LoaiGhe SET TenLoaiGhe=?, Gia=? WHERE MaLoaiGhe=?";
    private final String DELETE_SQL = "DELETE FROM LoaiGhe WHERE MaLoaiGhe=?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM LoaiGhe WHERE MaLoaiGhe=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM LoaiGhe";
    
    @Override
    public LoaiGhe create(LoaiGhe loaiGhe) {
        XJdbc.executeUpdate(INSERT_SQL,
            loaiGhe.getMaLoaiGhe(),
            loaiGhe.getTenLoaiGhe(),
            loaiGhe.getGia());
        return loaiGhe;
    }

    @Override
    public void update(LoaiGhe loaiGhe) {
        XJdbc.executeUpdate(UPDATE_SQL,
            loaiGhe.getTenLoaiGhe(),
            loaiGhe.getGia(),
            loaiGhe.getMaLoaiGhe());
    }

    @Override
    public void deleteById(String maLoaiGhe) {
        XJdbc.executeUpdate(DELETE_SQL, maLoaiGhe);
    }

    @Override
    public LoaiGhe findById(String maLoaiGhe) {
        List<LoaiGhe> list = selectBySql(SELECT_BY_ID_SQL, maLoaiGhe);
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
                LoaiGhe loaiGhe = new LoaiGhe();
                loaiGhe.setMaLoaiGhe(rs.getString("MaLoaiGhe"));
                loaiGhe.setTenLoaiGhe(rs.getString("TenLoaiGhe"));
                loaiGhe.setGia(rs.getDouble("Gia"));
                list.add(loaiGhe);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn LoaiGhe",e);
        }
        return list;
    }
    
}
