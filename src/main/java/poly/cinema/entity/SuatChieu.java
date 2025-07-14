/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class SuatChieu {

    private int maXuat;         // ma_xuat INT PRIMARY KEY IDENTITY(1,1)
    private int maPhim;         // ma_phim INT
    private String maPhong;     // ma_phong VARCHAR(5)
    private LocalDate ngayChieu; // ✅ dùng java.time.LocalDate
    private LocalTime gioChieu;  // ✅ dùng java.time.LocalTime
    private BigDecimal giaVe;    // gia_ve DECIMAL(10,2)
}
