/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

import java.sql.Timestamp;
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
    private double tongTien;

}
