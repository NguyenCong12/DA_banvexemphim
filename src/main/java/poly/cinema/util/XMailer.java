package poly.cinema.util;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.logging.log4j.message.Message;


public class XMailer {

    private static final String USERNAME = "bhnhien40@gmail.com";
    private static final String PASSWORD = "hlwdojdocpzpyxzr"; // App password

    public static void send(String toEmail, String subject, String username, String otpCode) {
        try {
            // Cấu hình SMTP Gmail
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Đăng nhập email gửi
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

            // HTML email content - hiện đại, chuyên nghiệp cho app bán điện thoại
            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f6f8; margin: 0; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.1);">
                        <h2 style="text-align: center; color: #2c3e50;">📱 PolyPhone - Xác thực tài khoản</h2>
                        <p>Xin chào <strong>%s</strong>,</p>
                        <p>Bạn hoặc ai đó đã yêu cầu <strong>khôi phục mật khẩu</strong> cho tài khoản tại <strong>PolyPhone</strong>.</p>
                        <p>Mã xác thực (OTP) của bạn là:</p>
                        <div style="font-size: 32px; font-weight: bold; color: #e67e22; text-align: center; margin: 20px 0;">
                            %s
                        </div>
                        <p>Vui lòng nhập mã OTP này vào hệ thống để tiếp tục quá trình đặt lại mật khẩu.</p>
                        <p><em>Mã có hiệu lực trong vòng 5 phút.</em></p>
                        <hr style="margin: 30px 0; border: none; border-top: 1px solid #eee;">
                        <p style="text-align: center; font-size: 12px; color: #888888;">
                            © 2025 PolyPhone. Đây là email tự động, vui lòng không trả lời lại.
                        </p>
                    </div>
                </body>
                </html>
                """.formatted(username, otpCode);

            // Tạo email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME, "PolyPhone"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            // Gửi
            Transport.send(message);
            System.out.println("Email đã được gửi đến " + toEmail);
        } catch (Exception e) {
            System.err.println("Gửi email thất bại: " + e.getMessage());
        }
    }
}
