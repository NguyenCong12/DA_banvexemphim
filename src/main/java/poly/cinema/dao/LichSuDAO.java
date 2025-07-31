/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cinema.dao;

import java.util.Date;
import java.util.List;
import poly.cinema.entity.LichSu;

/**
 *
 * @author NITRO
 */
public interface LichSuDAO {

    List<LichSu> selectAll();

    List<LichSu> getByDate(Date begin, Date end);

    List<LichSu> selectByUsername(int maND);

    List<LichSu> getByDateAndUser(Date begin, Date end, int maND);
    
    List<LichSu> getByDateAndUserId(Date begin, Date end, int maNd);
}
