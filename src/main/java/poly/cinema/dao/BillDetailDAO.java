/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.List;
import poly.cafe.entity.BillDetail;

/**
 *
 * @author Admin
 */
public interface BillDetailDAO{
 List<BillDetail> findByBillId(Long billId);
 List<BillDetail> findByDrinkId(String drinkId);
BillDetail findByBillIdAndDrinkId(long billId, String drinkId);
}
