/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.ui;

import poly.cinema.entity.HoaDon;

/**
 *
 * @author LAPTOP MSI
 */
public interface QL_BanHang_Controler {
   

    void fillBillDetails(); // tải và hiển thị chi tiết phiếu

    void selectTimeRange(); // xử lý chọn khoảng thời gian trong cboTimeRanges

    void setBill(HoaDon bill); // truyền bill vào cửa sổ để hiển thị 

    void open(); // Hiển thị bill 

    void close(); // Xóa bill nếu ko chứa gì

    void showQLPhimDialog(); 
    
    void showQLSanphamDialog(); 
    
    void showQLkhachhang(); // Hiển thị cửa sổ bổ sung đồ uống vào bill 

    void remove(); // Xóa đồ uống khỏi bill

    void updateQuantity(); // Thay đổi số lượng đồ uống 

    void checkout(); // Thanh toán 

    void cancel(); // Hủy bill

}


