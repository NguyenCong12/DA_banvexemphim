/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguoiDung {
    private Integer maNd; 
    private String tenNd; 
    private String email; 
    private String matKhau; 
    @Builder.Default
    private boolean vai_tro = false;
    private String sdt;
    @Builder.Default
    private boolean hoat_dong = true;
    @Builder.Default
    private String anh_dai_dien = "photo.png"; // đổi từ anh_nv
}
