/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.List;
import poly.cinema.entity.QuanLyGhe;

/**
 *
 * @author Admin
 */
public interface QuanLyGheDao extends CrudDAO<QuanLyGhe, Integer> {
    List<QuanLyGhe> findByPhong(String maPhong);
    boolean updateTrangThai(int maGhe, String trangThai);
    QuanLyGhe findBySoGhe(String soGhe); 
    QuanLyGhe findBySoGheAndPhong(String soGhe, String maPhong);
   
    public List<String> getAllMaPhong();

}


