/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

import java.math.BigDecimal;
import java.sql.Time;
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
public class XuatChieu {
    private Integer maXuat;
    private Integer maPhim;
    private String maPhong;
    private Date ngayChieu;
    private Time gioChieu;
    private BigDecimal giaVe;
}
