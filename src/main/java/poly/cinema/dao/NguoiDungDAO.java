/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import poly.cinema.entity.NguoiDung;

/**
 *
 * @author ADMIN
 */
public interface NguoiDungDAO extends CrudDAO<NguoiDung, Integer> {
    NguoiDung findByEmail(String email);
    // Ghi dòng này để Git nhận ra file là mới
}
