/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import poly.cinema.entity.LoaiPhim;


/**
 *
 * @author Admin
 */
public interface LoaiPhimDao extends CrudDAO<LoaiPhim, Integer>{
    LoaiPhim findByName(String tenLoai);
}
