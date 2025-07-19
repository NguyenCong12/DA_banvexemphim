/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

import java.util.Date;
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
public class Phim {
    private int maPhim;
    private String tenPhim;
    private int maLoai;
    private int thoiLuong;
    private String moTa;
    private Date ngayKhoiChieu;
    private Date ngayKetThuc; // ➕ Mới thêm
    private String trangThai;
    private String hinhAnh;

    @Override
    public String toString() {
        return tenPhim; // hoặc "P" + maPhim + " - " + tenPhim
    }
}

