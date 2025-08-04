package poly.cinema.util;

import java.sql.*;
import java.time.*;
import poly.cinema.util.XJdbc;
import poly.cinema.util.XMailer;

public class OtpService {

    public static String sendOtp(String email, String username) {
        String otp = generateOtp();
        String subject = "Mã xác thực tài khoản - BanVeXemPhim";

        try {
            // Gửi email
            XMailer.send(email, subject, username, otp);

            // Đã cập nhật DB trong XMailer.send()
            return otp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Kiểm tra OTP đúng và còn hạn, trả về thông báo cụ thể
     */
    public static String verifyOtp(String email, String inputOtp) {
        String sql = "SELECT ma_xac_thuc, ma_xac_thuc_Expiration FROM NguoiDung WHERE email = ?";

        try (ResultSet rs = XJdbc.executeQuery(sql, email)) {
            if (rs.next()) {
                String otp = rs.getString("ma_xac_thuc");
                Timestamp expiration = rs.getTimestamp("ma_xac_thuc_Expiration");

                if (otp == null || expiration == null) {
                    return "Không tìm thấy mã xác thực!";
                }

                if (!otp.equals(inputOtp)) {
                    return "Mã OTP không đúng!";
                }

                if (expiration.toInstant().isBefore(Instant.now())) {
                    return "Mã OTP đã hết hạn!";
                }

                return "Xác thực thành công!";
            } else {
                return "Email không tồn tại trong hệ thống!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi kiểm tra OTP!";
        }
    }

    /**
     * Sinh OTP ngẫu nhiên 6 chữ số
     */
    private static String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
}
