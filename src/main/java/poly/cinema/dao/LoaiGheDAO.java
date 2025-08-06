/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.List;
import poly.cinema.entity.LoaiGhe;

/**
 *
 * @author ADMIN
 */
public interface LoaiGheDAO extends CrudDAO<LoaiGhe, String>  {
    public List<String> findAllTenLoaiGhe();
}
