/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
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
public class LichSu {

    private int maHd;
    private Timestamp ngayLap;
    private String tenNhanVien;

    private String tenPhim;
    private Date ngayChieu;
    private Time gioChieu;
    private String soGhe;
    private String loaiGhe;
    private Double giaVe;

    private String tenHang;
    private Integer soLuongHang;
    private Double giaHang;

    private double tongTien;

}
