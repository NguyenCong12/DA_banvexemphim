/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cafe.entity;

import java.util.Date;
import lombok.*;

/**
 *
 * @author Admin
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
public class Bill {
 private Long id;
 private String username;
 private Integer cardId;
 @Builder.Default
 private Date checkin = new Date();
 private Date checkout;
 private int status;
public enum Status {
        Servicing, Completed, Canceled
    }


    // Getter & Setter cho status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
