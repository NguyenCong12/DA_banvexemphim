/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

import lombok.*;

/**
 *
 * @author Admin
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    private Integer maNv;              // ma_nv (ID)
    private String tenNv;             // ten_nv
    private String email;             // email
    private String matKhau;           // mat_khau
    @Builder.Default
    private boolean vaiTro = false;   // vai_tro (quản lý)
    private String sdt;               // sdt
    @Builder.Default
    private boolean hoatDong = true;  // hoat_dong
    @Builder.Default
    private String anhNv = "photo.png"; // anh_nv
}
