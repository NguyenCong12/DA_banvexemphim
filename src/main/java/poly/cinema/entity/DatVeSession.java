/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DatVeSession {

    private static String maPhong;
    private static String ngayChieu;
    private static String gioChieu;
    private static String tenPhim;
    private static SuatChieu suatChieuDuocChon;
    private static List<String> danhSachGheDaChon = new ArrayList<>();
    private static Integer maXuat;

    public static Integer getMaXuat() {
        return maXuat;
    }

    public static void setMaXuat(Integer maXuat) {
        DatVeSession.maXuat = maXuat;
    }

    public static String getMaPhong() {
        return maPhong;
    }

    public static void setMaPhong(String maPhong) {
        DatVeSession.maPhong = maPhong;
    }

    public static String getNgayChieu() {
        return ngayChieu;
    }

    public static void setNgayChieu(String ngayChieu) {
        DatVeSession.ngayChieu = ngayChieu;
    }

    public static String getGioChieu() {
        return gioChieu;
    }

    public static void setGioChieu(String gioChieu) {
        DatVeSession.gioChieu = gioChieu;
    }

    public static String getTenPhim() {
        return tenPhim;
    }

    public static void setTenPhim(String tenPhim) {
        DatVeSession.tenPhim = tenPhim;
    }

    public static SuatChieu getSuatChieuDuocChon() {
        return suatChieuDuocChon;
    }

    public static void setSuatChieuDuocChon(SuatChieu sc) {
        suatChieuDuocChon = sc;
    }

    public static List<String> getDanhSachGheDaChon() {
        return danhSachGheDaChon;
    }

    public static void setDanhSachGheDaChon(List<String> danhSach) {
        danhSachGheDaChon = danhSach;
    }

    public static void clear() {
        maPhong = null;
        ngayChieu = null;
        gioChieu = null;
        tenPhim = null;
        suatChieuDuocChon = null;
        danhSachGheDaChon.clear();
    }

    public static List<Object[]> getDanhSachHangTam() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
