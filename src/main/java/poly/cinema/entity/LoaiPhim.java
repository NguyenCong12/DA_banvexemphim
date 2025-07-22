package poly.cinema.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity đại diện cho loại phim (thể loại)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoaiPhim {

    private Integer maLoai;
    private String tenLoai;

    @Override
    public String toString() {
        return tenLoai;
    }
    public Integer getMaLoai() {
    return maLoai;
}

}
