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

    private Integer maNd; // ma_nd thay vì ma_nv
    private String tenNd; // ten_nd thay vì ten_nv
    private String email;
    private String matKhau;
    @Builder.Default
    private boolean vaiTro = false;
    private String sdt;
    @Builder.Default
    private boolean hoatDong = true;
    @Builder.Default
    private String anhDaiDien = "photo.png"; // đổi từ anh_nv
}
