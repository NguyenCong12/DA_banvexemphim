/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NITRO
 */
public class SanPhamSession {

    private static List<DatHang> danhSachTam = new ArrayList<>();

    public static void them(DatHang sp) {
        danhSachTam.add(sp);
    }

    public static List<DatHang> getDanhSachHoaDonTam() {
        return danhSachTam;
    }

    public static void setDanhSachHoaDonTam(List<DatHang> danhSach) {
        danhSachTam = danhSach;
    }

    public static List<DatHang> getAll() {
        return new ArrayList<>(danhSachTam); // tránh bị sửa bên ngoài
    }

    public static void clear() {
        danhSachTam.clear();
    }
}
