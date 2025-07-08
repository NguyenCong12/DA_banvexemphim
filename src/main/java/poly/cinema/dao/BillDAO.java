/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.Date;
import java.util.List;
import poly.cafe.entity.Bill;

/**
 *
 * @author Admin
 */
public interface BillDAO  {

    List<Bill> findByUsername(String username);

    List<Bill> findByCardId(Integer cardId);

    List<Bill> findByTimeRange(Date begin, Date end);

    public void update(List<Bill> items);

    public Bill findServicingByCardId(Integer cardId);

    List<Bill> findByUserAndTimeRange(String username, Date begin, Date end);

    void insert(Bill bill);
}
