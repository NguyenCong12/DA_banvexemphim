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
 * @author NITRO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPham {

    private String maSanPham;
    private String tenSanPham;
    private String loai;
    private double gia;
    private boolean trangThai;
    private String anh;
}
