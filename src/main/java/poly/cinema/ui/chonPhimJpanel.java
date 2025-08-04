/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.cinema.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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

    private boolean isLoadingPhim = false;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDaChon = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhim = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSuatChieu = new javax.swing.JTable();
        btnTieptheo = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dateChooserNgay = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        btnQuayLai = new javax.swing.JButton();

        tblDaChon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã chiếu", "Phòng chiếu", "Ngày chiếu", "Giờ chiếu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDaChon);

        tblPhim.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã phim", "Tên phim", "thời lượng", "Ngày khởi chiếu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
                "Mã chiếu", "Mã phim", "Phòng chiếu", "Ngày chiếu", "Giờ chiếu", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        btnTieptheo.setText("Tiếp theo");
        btnTieptheo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTieptheoActionPerformed(evt);
            }
        });

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        jLabel2.setText("Danh sách phim");

        jLabel3.setText("Danh sách suất chiếu");

        jLabel4.setText("Suất chiếu đã chọn");

        jLabel1.setText("Chọn ngày:");

        btnQuayLai.setText("Quay  lại");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnQuayLai)
                .addGap(26, 26, 26)
                .addComponent(btnTieptheo)
                .addGap(29, 29, 29)
                .addComponent(btnHuy)
                .addGap(28, 28, 28))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(367, 367, 367))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dateChooserNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateChooserNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 63, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTieptheo)
                        .addComponent(btnHuy)
                        .addComponent(btnQuayLai)))
                .addGap(40, 40, 40))
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
        chonGhePanel.loadGheTheoSuatChieu(DatVeSession.getMaXuat().toString(), maPhong);

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

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        huyChonSuatChieu();
    }//GEN-LAST:event_btnHuyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnTieptheo;
    private com.toedter.calendar.JDateChooser dateChooserNgay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
        // Thiết lập tiếng Việt
        Locale localeVN = new Locale("vi", "VN");
        Locale.setDefault(localeVN);

// Thiết lập định dạng và ngày mặc định
        dateChooserNgay.setLocale(localeVN);
        dateChooserNgay.setDateFormatString("dd/MM/yyyy");

        Date today = new Date(); // hôm nay
        dateChooserNgay.setDate(today); // mặc định hôm nay

// Giới hạn ngày: chỉ được chọn từ hôm nay đến 7 ngày sau
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date maxDate = cal.getTime();

        dateChooserNgay.setMinSelectableDate(today);
        dateChooserNgay.setMaxSelectableDate(maxDate);
        btnQuayLai.addActionListener(e -> quayLai());

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(btnQuayLai);

// Thêm vào layout chính của bạn (nếu dùng BorderLayout)
        add(pnlTop, BorderLayout.NORTH);
        open();
        dateChooserNgay.getDateEditor().addPropertyChangeListener("date", evt -> {
            fillProduct(); // tự động gọi fill khi chọn ngày
        });
    }

    private void quayLai() {
        CardLayout cl = (CardLayout) pnlMainContent.getLayout();
        cl.show(pnlMainContent, "pnlBanHang");
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

// Sửa đoạn này
            String giaVeStr = tblSuatChieu.getValueAt(row, 5).toString();
            giaVeStr = giaVeStr.replace(",", "").replace(" VNĐ", "").trim();
            BigDecimal giaVe = new BigDecimal(giaVeStr);

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

        SwingUtilities.invokeLater(() -> {
            fillThongTinPhimNeuDaChon();
        });
    }

    @Override
    public void fillProduct() {
        isLoadingPhim = true;
        DefaultTableModel model = (DefaultTableModel) tblPhim.getModel();
        model.setRowCount(0);

        try {
            Date selectedDate = dateChooserNgay.getDate();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!");
                return;
            }

            // Chuyển sang LocalDate
            LocalDate selectedLocalDate = selectedDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            List<Phim> allPhim = phimDao.findAll();
            List<Integer> phimIdNgayChon = new ArrayList<>();

            for (Phim p : allPhim) {
                List<SuatChieu> suatChieus = suatChieuDao.findByMaPhim(p.getMaPhim());
                boolean coSuat = suatChieus.stream()
                        .anyMatch(sc -> sc.getNgayChieu() != null && sc.getNgayChieu().isEqual(selectedLocalDate));
                if (coSuat) {
                    phimIdNgayChon.add(p.getMaPhim());
                }
            }

            for (Phim p : allPhim) {
                if (phimIdNgayChon.contains(p.getMaPhim())) {
                    model.addRow(new Object[]{
                        p.getMaPhim(),
                        p.getTenPhim(),
                        p.getThoiLuong(),
                        p.getNgayKhoiChieu()
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc phim theo ngày!");
        }

        isLoadingPhim = false;
    }

    private void initPhimSelectionListener() {
        tblPhim.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !isLoadingPhim) {
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

            // Lấy ngày đang chọn
            LocalDate selectedDate = dateChooserNgay.getDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            for (SuatChieu sc : list) {
                if (sc.getNgayChieu().isEqual(selectedDate)) { // Lọc đúng ngày
                    Object[] rowData = {
                        sc.getMaXuat(),
                        sc.getMaPhim(),
                        sc.getMaPhong(),
                        sc.getNgayChieu(),
                        sc.getGioChieu(),
                        formatVNĐ(sc.getGiaVe())
                    };
                    model.addRow(rowData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            XDialog.alert("Lỗi khi load suất chiếu");
        }
    }

    private String formatVNĐ(BigDecimal amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount) + " VNĐ";
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
        String tenPhim = tblPhim.getValueAt(tblPhim.getSelectedRow(), 1).toString();
        DatVeSession.setTenPhim(tenPhim);

        DatVeSession.setSuatChieuDuocChon(suat);
        DatVeSession.setMaXuat(suat.getMaXuat());
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

    private void fillThongTinPhimNeuDaChon() {
        if (DatVeSession.getMaXuat() != null) {
            DefaultTableModel model = (DefaultTableModel) tblDaChon.getModel();
            model.setRowCount(0);
            model.addRow(new Object[]{
                DatVeSession.getMaXuat(),
                DatVeSession.getMaPhong(),
                DatVeSession.getNgayChieu(),
                DatVeSession.getGioChieu()
            });
        }
    }

    private void huyChonSuatChieu() {
        try {
            // Kiểm tra nếu chưa chọn suất chiếu nào
            if (DatVeSession.getMaXuat() == null) {
                XDialog.alert("Chưa có suất chiếu nào được chọn để hủy!");
                return;
            }

            // Xóa dữ liệu trong session
            DatVeSession.setSuatChieuDuocChon(null);
            DatVeSession.setMaXuat(null);
            DatVeSession.setMaPhong(null);
            DatVeSession.setNgayChieu(null);
            DatVeSession.setGioChieu(null);

            // Xóa dòng trong bảng đã chọn
            DefaultTableModel model = (DefaultTableModel) tblDaChon.getModel();
            model.setRowCount(0);

            // Bỏ chọn dòng trong bảng suất chiếu
            tblSuatChieu.clearSelection();

            XDialog.alert("Đã hủy chọn suất chiếu!");

        } catch (Exception e) {
            e.printStackTrace();
            XDialog.alert("Lỗi khi hủy chọn suất chiếu!");
        }
    }

}
