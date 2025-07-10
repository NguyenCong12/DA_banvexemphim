/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.ui.manager;

import poly.cinema.entity.ThongKe;



/**
 *
 * @author Bao Nhien
 */
public interface ThongKeController extends CrudController<ThongKe>{
    
     void open(); // hiển thị doanh thu từng loại trong ngày 
     
    void selectTimeRange(); // hiển thị doanh thu theo khoảng thời gian được chọn     
    void fillRevenue(); // hiển thị doanh thu 
}
