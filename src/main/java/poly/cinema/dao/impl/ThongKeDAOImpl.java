/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao.impl;

import java.util.Date;
import java.util.List;
import poly.cinema.dao.ThongKeDAO;
import poly.cinema.entity.ThongKe;
import poly.cinema.util.XQuery;

public class ThongKeDAOImpl implements ThongKeDAO{

   @Override
    public List<ThongKe.ByUser> getByUser(Date begin, Date end) {
        String revenueByUserSql = 
            "SELECT bill.Username AS [User], "  
            + "   SUM(detail.UnitPrice * detail.Quantity * (1 - detail.Discount)) AS revenue," 
            + "   COUNT(DISTINCT detail.BillId) AS quantity,"
            + "   MIN(bill.Checkin) AS firstTime,"
            + "   MAX(bill.Checkin) AS lastTime "
            + "FROM BillDetails detail "
            + "   JOIN Bills bill ON bill.BillId = detail.BillId " 
            + "WHERE bill.Status = 1 "
            + "   AND bill.Checkout IS NOT NULL "
            + "   AND bill.Checkout BETWEEN ? AND ? "
            + "GROUP BY bill.Username "
            + "ORDER BY revenue DESC";

        return XQuery.getBeanList(ThongKe.ByUser.class, revenueByUserSql, begin, end);
    }
}

