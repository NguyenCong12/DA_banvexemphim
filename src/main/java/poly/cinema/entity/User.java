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

    private Integer ma_nv;
    private String ten_nv;
    private String email;
    private String mat_khau;
    @Builder.Default
    private boolean vai_tro = false;
    private String sdt;
    @Builder.Default
    private boolean hoat_dong = true;
    @Builder.Default
    private String anh_nv = "photo.png";
}
