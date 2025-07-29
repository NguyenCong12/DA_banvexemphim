package poly.cinema.entity;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * ⚠️ KHÔNG nên dùng hàm này nữa.
     * Sử dụng addGheDaChon() để tránh ghi đè danh sách ghế cũ.
     */
    @Deprecated
    public static void setDanhSachGheDaChon(List<String> danhSach) {
        danhSachGheDaChon = danhSach;
    }

    public static void addGheDaChon(List<String> gheMoi) {
        if (danhSachGheDaChon == null) {
            danhSachGheDaChon = new ArrayList<>();
        }

        for (String ghe : gheMoi) {
            if (!danhSachGheDaChon.contains(ghe)) {
                danhSachGheDaChon.add(ghe);
            }
        }
    }

    public static void removeGheDaChon(String maGhe) {
        if (danhSachGheDaChon != null) {
            danhSachGheDaChon.remove(maGhe);
        }
    }

    public static void clear() {
        maPhong = null;
        ngayChieu = null;
        gioChieu = null;
        tenPhim = null;
        suatChieuDuocChon = null;
        danhSachGheDaChon.clear();
        maXuat = null;
    }

    public static List<Object[]> getDanhSachHangTam() {
        throw new UnsupportedOperationException("Chưa được hỗ trợ.");
    }
}
