/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.cinema.ui;

import java.awt.CardLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import poly.cinema.dao.QuanLyPhimDao;
import poly.cinema.dao.QuanLySuatChieuDao;
import poly.cinema.dao.impl.QuanLyPhimDaoImpl;
import poly.cinema.dao.impl.QuanLyPhongChieuDaoImpl;
import poly.cinema.dao.impl.QuanLySuatChieuDaoImpl;
import poly.cinema.entity.DatVeSession;
import poly.cinema.entity.LoaiPhim;
import poly.cinema.entity.Phim;
import poly.cinema.entity.PhongChieu;
import poly.cinema.entity.SuatChieu;
import poly.cinema.util.XDialog;

/**
 *
 * @author Admin
 */
public class chonPhimJpanel extends javax.swing.JPanel implements chonPhimController {

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDaChon = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhim = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSuatChieu = new javax.swing.JTable();
        btnTieptheo = new javax.swing.JButton();

        jLabel1.setText("Hủy");

        tblDaChon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblDaChon);

        tblPhim.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblPhim);

        tblSuatChieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSuatChieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSuatChieuMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSuatChieu);

        btnTieptheo.setText("jButton1");
        btnTieptheo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTieptheoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTieptheo)
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addGap(73, 73, 73))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btnTieptheo))
                        .addGap(97, 97, 97))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTieptheoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTieptheoActionPerformed
        if (DatVeSession.getMaXuat() == null) {
            XDialog.alert("Vui lòng chọn suất chiếu trước!");
            return;
        }

        String maPhong = DatVeSession.getMaPhong();
        PhongChieu phong = new QuanLyPhongChieuDaoImpl().findById(maPhong);

        if (phong == null) {
            XDialog.alert("Không tìm thấy thông tin phòng chiếu!");
            return;
        }

        int soHang = phong.getSoHang();
        int soCot = phong.getSoCot();

        // Tạo và cấu hình panel chọn ghế
        chonGheJPanel chonGhePanel = new chonGheJPanel(pnlMainContent);
        chonGhePanel.loadGheTheoSuatChieu(DatVeSession.getMaXuat(), maPhong);

        // Thêm panel vào pnlMainContent nếu chưa có
        pnlMainContent.add(chonGhePanel, "chonGhePanel");

        // Chuyển sang panel chọn ghế
        chuyenPanel("chonGhePanel");
    }//GEN-LAST:event_btnTieptheoActionPerformed

    private void tblSuatChieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSuatChieuMouseClicked
        if (evt.getClickCount() == 2) {
            addToBill(); // Gọi hàm xử lý dữ liệu
        }
    }//GEN-LAST:event_tblSuatChieuMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTieptheo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblDaChon;
    private javax.swing.JTable tblPhim;
    private javax.swing.JTable tblSuatChieu;
    // End of variables declaration//GEN-END:variables
private JPanel pnlMainContent;

    QuanLyPhimDao phimDao = new QuanLyPhimDaoImpl();
    QuanLySuatChieuDao suatChieuDao = new QuanLySuatChieuDaoImpl();
    List<Phim> items = new ArrayList<>();
    private List<LoaiPhim> loaiPhimList;

    public chonPhimJpanel(JPanel pnlMainContent) {
        this.pnlMainContent = pnlMainContent;
        initComponents();
        open();
    }

    private void chuyenPanel(String panelName) {
        CardLayout cl = (CardLayout) pnlMainContent.getLayout();
        cl.show(pnlMainContent, panelName);
    }

    private SuatChieu getSuatChieuDuocChon() {
        int row = tblSuatChieu.getSelectedRow();
        if (row < 0) {
            return null;
        }

        try {
            int maXuat = (int) tblSuatChieu.getValueAt(row, 0);
            int maPhim = (int) tblSuatChieu.getValueAt(row, 1);
            String maPhong = (String) tblSuatChieu.getValueAt(row, 2);

            Object ngayObj = tblSuatChieu.getValueAt(row, 3);
            LocalDate ngayChieu;
            if (ngayObj instanceof LocalDate) {
                ngayChieu = (LocalDate) ngayObj;
            } else if (ngayObj instanceof java.sql.Date) {
                ngayChieu = ((java.sql.Date) ngayObj).toLocalDate();
            } else if (ngayObj instanceof java.util.Date) {
                ngayChieu = ((java.util.Date) ngayObj).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
            } else {
                ngayChieu = LocalDate.parse(ngayObj.toString());
            }

            Object gioObj = tblSuatChieu.getValueAt(row, 4);
            LocalTime gioChieu = (gioObj instanceof LocalTime)
                    ? (LocalTime) gioObj
                    : LocalTime.parse(gioObj.toString());

            BigDecimal giaVe = new BigDecimal(tblSuatChieu.getValueAt(row, 5).toString());

            return SuatChieu.builder()
                    .maXuat(maXuat)
                    .maPhim(maPhim)
                    .maPhong(maPhong)
                    .ngayChieu(ngayChieu)
                    .gioChieu(gioChieu)
                    .giaVe(giaVe)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            XDialog.alert("Lỗi khi đọc dữ liệu suất chiếu được chọn!");
            return null;
        }
    }

    @Override
    public void open() {
        fillProduct();
        initPhimSelectionListener();
    }

    @Override
    public void fillProduct() {
        DefaultTableModel model = (DefaultTableModel) tblPhim.getModel();
        model.setRowCount(0);
        try {
            List<Phim> list = phimDao.findAll();
            for (Phim p : list) {
                model.addRow(new Object[]{
                    p.getMaPhim(),
                    p.getTenPhim(),
                    p.getThoiLuong(),
                    p.getNgayKhoiChieu(),
                    p.getNgayKetThuc()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            XDialog.alert("Lỗi khi load danh sách phim");
        }
    }

    private void initPhimSelectionListener() {
        tblPhim.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblPhim.getSelectedRow();
                if (row >= 0) {
                    String maPhim = tblPhim.getValueAt(row, 0).toString();
                    fillSuatChieuTheoPhim(maPhim);
                }
            }
        });
    }

    private void fillSuatChieuTheoPhim(String maPhim) {
        DefaultTableModel model = (DefaultTableModel) tblSuatChieu.getModel();
        model.setRowCount(0);
        try {
            int maPhimInt = Integer.parseInt(maPhim);
            List<SuatChieu> list = suatChieuDao.findByMaPhim(maPhimInt);

            for (SuatChieu sc : list) {
                Object[] rowData = {
                    sc.getMaXuat(),
                    sc.getMaPhim(),
                    sc.getMaPhong(),
                    sc.getNgayChieu(),
                    sc.getGioChieu(),
                    sc.getGiaVe()
                };
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            XDialog.alert("Lỗi khi load suất chiếu");
        }
    }

    @Override
    public void addToBill() {
        int row = tblSuatChieu.getSelectedRow();
        if (row < 0) {
            XDialog.alert("Vui lòng chọn suất chiếu!");
            return;
        }

        SuatChieu suat = getSuatChieuDuocChon();
        if (suat == null) {
            return;
        }

        DatVeSession.setSuatChieuDuocChon(suat);
        DatVeSession.setMaXuat(String.valueOf(suat.getMaXuat()));
        DatVeSession.setMaPhong(suat.getMaPhong());
        DatVeSession.setNgayChieu(suat.getNgayChieu().toString());
        DatVeSession.setGioChieu(suat.getGioChieu().toString());

        DefaultTableModel model = (DefaultTableModel) tblDaChon.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{
            suat.getMaXuat(),
            suat.getMaPhong(),
            suat.getNgayChieu(),
            suat.getGioChieu()
        });
    }

    public void fillThongTinPhim() {
        String tenPhim = DatVeSession.getTenPhim();
        String ngay = DatVeSession.getNgayChieu();
        String gio = DatVeSession.getGioChieu();

        DefaultTableModel model = (DefaultTableModel) tblDaChon.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{tenPhim, ngay + " " + gio});
    }

    @Override
    public void fillBrand() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
