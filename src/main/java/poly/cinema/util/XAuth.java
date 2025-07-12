/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.util;
//
//import poly.cafe.entity.User;

import poly.cinema.entity.User;


/**
 *
 * @author Admin
 */
public class XAuth {
    public static User user1;
    public static User user = User.builder()
            .email("123")
            .mat_khau("123")
            .hoat_dong(true)
            .vai_tro(true)
            .ten_nv("Nguyễn Văn Tèo")
            .anh_nv("trump.png")
            .build(); // biến user này sẽ được thay thế sau khi đăng nhập

}
