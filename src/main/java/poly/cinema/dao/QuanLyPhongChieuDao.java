/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import poly.cinema.entity.PhongChieu;

/**
 *
 * @author Admin
 */
public interface QuanLyPhongChieuDao extends CrudDAO<PhongChieu, Integer>{
    PhongChieu findById(String maPhong);
    
    void delete(String maPhong);
}
