/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.util;
//
//import poly.cafe.entity.User;

import poly.cinema.entity.NguoiDung;


/**
 *
 * @author Admin
 */
public class XAuth {
    public static NguoiDung user1;
    public static NguoiDung user = NguoiDung.builder()
            .email("123")
            .matKhau("123")
            .hoat_dong(true)
            .vai_tro(true)
            .tenNd("Nguyễn Văn Tèo")
            .anh_dai_dien("trump.png")
            .build(); // biến user này sẽ được thay thế sau khi đăng nhập

}
