package poly.cinema.entity;

public class ThongKe {

    public static class DoanhThuPhim {
        private String tenPhim;
        private double doanhThu;

        public String getTenPhim() {
            return tenPhim;
        }

        public void setTenPhim(String tenPhim) {
            this.tenPhim = tenPhim;
        }

        public double getDoanhThu() {
            return doanhThu;
        }

        public void setDoanhThu(double doanhThu) {
            this.doanhThu = doanhThu;
        }
    }

    public static class DoanhThuSanPham {
        private String loai;
        private double doanhThu;

        public String getLoai() {
            return loai;
        }

        public void setLoai(String loai) {
            this.loai = loai;
        }

        public double getDoanhThu() {
            return doanhThu;
        }

        public void setDoanhThu(double doanhThu) {
            this.doanhThu = doanhThu;
        }
    }
} 
