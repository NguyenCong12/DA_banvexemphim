package poly.cinema.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class XIcon {
    /**
     * Đọc icon từ resource hoặc file
     * @param path đường dẫn file, đường dẫn resource hoặc tên resource
     * @return ImageIcon
     */
    public static ImageIcon getIcon(String path) {
        if (!path.contains("/") && !path.contains("\\")) { // Tên file icon đơn giản (resource name)
            return getIcon("/poly/cafe/icons/" + path);
        }

        if (path.startsWith("/")) { // Đường dẫn tài nguyên nội bộ
            URL url = XIcon.class.getResource(path);
            if (url == null) {
                System.err.println("❌ Không tìm thấy icon: " + path);
                return new ImageIcon(); // Trả về icon rỗng (tránh null)
            }
            return new ImageIcon(url);
        }

        // Trường hợp còn lại: là đường dẫn đến file trong máy
        return new ImageIcon(path);
    }

    /**
     * Đọc icon theo kích thước
     *
     * @param path đường dẫn file hoặc tài nguyên
     * @param width chiều rộng mong muốn
     * @param height chiều cao mong muốn
     * @return ImageIcon với kích thước mới
     */
    public static ImageIcon getIcon(String path, int width, int height) {
        ImageIcon icon = getIcon(path);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    /**
     * Gán icon cho JLabel theo path
     *
     * @param label JLabel cần gán icon
     * @param path đường dẫn đến file hoặc resource
     */
    public static void setIcon(JLabel label, String path) {
        label.setIcon(getIcon(path, label.getWidth(), label.getHeight()));
    }

    /**
     * Gán icon cho JLabel từ một file
     *
     * @param label JLabel cần gán icon
     * @param file file ảnh icon
     */
    public static void setIcon(JLabel label, File file) {
        setIcon(label, file.getAbsolutePath());
    }

    /**
     * Sao chép file vào thư mục chỉ định với tên mới là ngẫu nhiên (tránh trùng)
     *
     * @param fromFile file nguồn
     * @param folder thư mục đích
     * @return File mới đã được sao chép
     */
    public static File copyTo(File fromFile, String folder) {
        String fileExt = fromFile.getName().substring(fromFile.getName().lastIndexOf("."));
        File toFile = new File(folder, XStr.getKey() + fileExt);
        toFile.getParentFile().mkdirs();

        try {
            Files.copy(fromFile.toPath(), toFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return toFile;
        } catch (IOException ex) {
            throw new RuntimeException("❌ Không thể sao chép file: " + fromFile.getAbsolutePath(), ex);
        }
    }

    /**
     * Sao chép file vào thư mục mặc định "files"
     *
     * @param fromFile file nguồn
     * @return File mới đã sao chép
     */
    public static File copyTo(File fromFile) {
        return copyTo(fromFile, "files");
    }
}