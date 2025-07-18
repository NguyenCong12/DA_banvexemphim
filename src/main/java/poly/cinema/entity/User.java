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

    private Integer ma_nd; // ma_nd thay vì ma_nv
    private String ten_nd; // ten_nd thay vì ten_nv
    private String email;
    private String mat_khau;
    @Builder.Default
    private boolean vai_tro = false;
    private String sdt;
    @Builder.Default
    private boolean hoat_dong = true;
    @Builder.Default
    private String anh_dai_dien = "photo.png"; // đổi từ anh_nv
}
