/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

import java.util.List;


/**
 *
 * @author NITRO
 */
public class DatHang {

    private String tenSanPham;
    private double gia;
    private int soLuong;
    private double thanhTien;

    public DatHang() {
    }

    public DatHang(String tenSanPham, double gia, int soLuong) {
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.soLuong = soLuong;
        this.thanhTien = gia * soLuong;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
        this.thanhTien = this.gia * this.soLuong; // cập nhật lại thành tiền
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        this.thanhTien = this.gia * this.soLuong; // cập nhật lại thành tiền
    }

    public double getThanhTien() {
        return thanhTien;
    }
}
