/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.cinema.ui;

import java.awt.CardLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import poly.cinema.dao.impl.ChiTietVeDAOImpl;
import poly.cinema.dao.impl.HoaDonDaoImpl;
import poly.cinema.dao.impl.VeDaoImpl;
import poly.cinema.entity.DangNhapSession;
import poly.cinema.entity.DatHang;
import poly.cinema.entity.DatVeSession;
import poly.cinema.entity.NguoiDung;
import poly.cinema.entity.SanPhamSession;

/**
 *
 * @author KhanhLinh
 */
public class BanHang extends javax.swing.JPanel {

private JPanel pnlMainContent;
    private List<String> gheDaChon = new ArrayList<>();
    private int maXuatChieu;
    private double tongTienVe = 0;

     private final VeDaoImpl veDao = new VeDaoImpl();
     
    public BanHang(JPanel pnlMainContent) {
        this.pnlMainContent = pnlMainContent;
        initComponents();
        loadSanPhamTuTam();

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
public void componentShown(java.awt.event.ComponentEvent evt) {
    if (DatVeSession.getDanhSachGheDaChon() != null && !DatVeSession.getDanhSachGheDaChon().isEmpty()) {
        updateSauKhiChonGhe();
    }
    loadSanPhamTuTam();
}

        });
    }

       public void refreshData() {
        updateSauKhiChonGhe();
        loadSanPhamTuTam();
    }


       public void updateSauKhiChonGhe() {
        List<String> gheChon = DatVeSession.getDanhSachGheDaChon();
        if (gheChon == null || gheChon.isEmpty()) return;

        int maXuat = DatVeSession.getMaXuat();
        List<Object[]> ds = layDanhSachVe(maXuat, gheChon);
        fillThongTinVe(ds);
    }

    private List<Object[]> layDanhSachVe(int maXuat, List<String> gheChon) {
        List<Object[]> ds = new ArrayList<>();
        for (String maGhe : gheChon) {
            Object[] row = veDao.getThongTinVe(maXuat, maGhe);
            if (row != null) ds.add(row);
        }
        return ds;
    }


    public void fillThongTinVe(List<Object[]> ds) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);

        tongTienVe = 0;
        for (Object[] row : ds) {
            model.addRow(row);
            tongTienVe += ((Number) row[6]).doubleValue();
        }

        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        txtThanhTien.setText(nf.format(tongTienVe));
    }

    private void chuyenPanel(String panelName) {
        CardLayout cl = (CardLayout) pnlMainContent.getLayout();
        cl.show(pnlMainContent, panelName);
    }

    public void setGheDaChon(int maXuat, List<String> danhSach) {
        this.maXuatChieu = maXuat;
        this.gheDaChon = new ArrayList<>(danhSach);
    }

    public void setDanhSachVeTam(List<Object[]> danhSachVe) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        for (Object[] row : danhSachVe) {
            model.addRow(row);
        }
    }

    public void open() {}


    public void thanhToan() {
        int maXuat = DatVeSession.getMaXuat();
        NguoiDung nguoiDung = DangNhapSession.getNguoiDung();
        if (nguoiDung == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int maNguoiDung = nguoiDung.getMaNd(); // Lấy mã người dùng hiện tại

        List<String> danhSachGhe = DatVeSession.getDanhSachGheDaChon();

        if (danhSachGhe == null || danhSachGhe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn ghế!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tính tổng tiền
        double tongTien = 0;
        List<Object[]> danhSachVe = layDanhSachVe(maXuat, danhSachGhe);
        for (Object[] row : danhSachVe) {
            tongTien += Double.parseDouble(row[6].toString()); // đúng: lấy giá vé

        }

        HoaDonDaoImpl hoaDonDao = new HoaDonDaoImpl();
        ChiTietVeDAOImpl ctVeDao = new ChiTietVeDAOImpl();

        try {
            // 1. Tạo hóa đơn -> trả về mã hóa đơn
            int maHoaDon = hoaDonDao.taoHoaDon(maNguoiDung, tongTien);

            // 2. Lưu chi tiết vé từng ghế
            for (Object[] row : danhSachVe) {
                int maGhe = (int) row[0];              // ✅ an toàn
                double giaVe = (double) row[6];       // ✅ đúng

                ctVeDao.themChiTietVe(maHoaDon, maXuat, maGhe, giaVe);
            }

            // 3. Xóa bảng và UI
            ((DefaultTableModel) tblHoaDon.getModel()).setRowCount(0);
            txtThanhTien.setText("0");
            DatVeSession.clear();

            JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi khi thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void loadSanPhamTuTam() {
        List<DatHang> ds = SanPhamSession.getAll();

        DefaultTableModel model = (DefaultTableModel) tblchitiethang.getModel();
        model.setRowCount(0); // Xóa bảng cũ

        double tongTienSanPham = 0;

        for (DatHang sp : ds) {
            Object[] row = new Object[]{
                sp.getTenSanPham(),
                sp.getGia(),
                sp.getSoLuong(),
                sp.getThanhTien()
            };
            model.addRow(row);
            tongTienSanPham += sp.getThanhTien();
        }

        // Cộng thêm tiền vé (nếu có)
        double tienVe = 0;
        try {
            String txtTienVe = txtThanhTien.getText().replace(".", "").replace(",", "");
            tienVe = Double.parseDouble(txtTienVe);
        } catch (Exception e) {
            // Ignore parse error
        }

        double tong = tienVe + tongTienSanPham;
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        txtThanhTien.setText(nf.format(tong));
    }

    public void setDanhSachHoaDon(List<Object[]> ds) {
        DefaultTableModel model = (DefaultTableModel) tblchitiethang.getModel();
        System.out.println(ds.size());
        model.setRowCount(0); // Xoá hết dòng cũ
        for (Object[] row : ds) {
            model.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblchonphim = new javax.swing.JLabel();
        lblchonsanpham = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblchitiethang = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        txtThanhTien = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1110, 720));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel1.setText("BÁN HÀNG");

        lblchonphim.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblchonphim.setText("MUA VÉ");
        lblchonphim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblchonphimMouseClicked(evt);
            }
        });

        lblchonsanpham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblchonsanpham.setText("MUA SẢN PHẨM");
        lblchonsanpham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblchonsanphamMouseClicked(evt);
            }
        });

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã chi tiết vé", "Mã hóa đơn", "Suất chiếu", "Số ghế", "Giá vé", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(25);
        jScrollPane1.setViewportView(tblHoaDon);

        tblchitiethang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblchitiethang.setRowHeight(25);
        jScrollPane2.setViewportView(tblchitiethang);

        jLabel4.setText("Tiền vé:");

        jLabel5.setText("Tiền sản phẩm:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        txtThanhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhTienActionPerformed(evt);
            }
        });

        jLabel6.setText("Tổng tiền:");

        jLabel7.setText("Tiền khách đưa:");

        jLabel8.setText("Tiền thừa:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("HỦY HÓA ĐƠN");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("THANH TOÁN");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(461, 461, 461)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(81, 81, 81)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(158, 158, 158)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblchonphim)
                                .addGap(593, 593, 593)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(60, 60, 60))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtThanhTien)))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblchonsanpham)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(72, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblchonsanpham)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(lblchonphim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(78, 78, 78)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void txtThanhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThanhTienActionPerformed

    private void lblchonphimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblchonphimMouseClicked
        chuyenPanel("pnlChonPhim");
    }//GEN-LAST:event_lblchonphimMouseClicked

    private void lblchonsanphamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblchonsanphamMouseClicked

        chuyenPanel("pnlBanSanPham");
    }//GEN-LAST:event_lblchonsanphamMouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        thanhToan();
    }//GEN-LAST:event_jLabel10MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel lblchonphim;
    private javax.swing.JLabel lblchonsanpham;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblchitiethang;
    private javax.swing.JTextField txtThanhTien;
    // End of variables declaration//GEN-END:variables

}
