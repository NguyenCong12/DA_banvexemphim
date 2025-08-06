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
public class PhongChieu {

    private int maPhong;   // mã phòng (khóa chính)
    private String tenPhong;  // tên phòng chiếu
    private int soHang;       // số hàng ghế
    private int soCot;        // số cột ghế

    @Override
    public String toString() {
        return tenPhong;
    }

}
