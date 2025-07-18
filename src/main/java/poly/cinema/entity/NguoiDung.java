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
    private Boolean vaiTro;
    private String sdt; 
    private Boolean hoatDong; 
    private String anhDaiDien; 
}
