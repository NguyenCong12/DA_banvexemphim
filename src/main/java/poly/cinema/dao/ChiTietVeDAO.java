/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.List;
import poly.cinema.entity.ChiTietVe;

/**
 *
 * @author ADMIN
 */
public interface ChiTietVeDAO extends CrudDAO<ChiTietVe, Integer> {
    List<ChiTietVe> selectByMaHD(Integer maHD);
}
