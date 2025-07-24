/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.entity;

/**
 *
 * @author Admin
 */
public class DangNhapSession {
    private static NguoiDung nguoiDungHienTai;

    public static void setNguoiDung(NguoiDung nd) {
        nguoiDungHienTai = nd;
    }

    public static NguoiDung getNguoiDung() {
        return nguoiDungHienTai;
    }
}
