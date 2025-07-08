/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import poly.cinema.entity.User;

/**
 *
 * @author Admin
 */
public interface UserDAO extends CrudDAO<User, String>{ 
      User findByUsername(String username);
    User findByEmail(String email);
}

