/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.ui.manager;

import java.util.Date;

/**
 *
 * @author NITRO
 */
public interface LichSuBanHangController {

    void open();                         // Gọi khi form mở

    void fillTable();                    // Đổ dữ liệu toàn bộ (selectAll)

    void fillTableByDate(Date begin, Date end); // Đổ dữ liệu theo khoảng ngày

    void clear();                        // Xóa dữ liệu bảng

    void selectTimeRange();
}
