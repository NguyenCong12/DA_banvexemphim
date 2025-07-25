/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.cinema.ui.manager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.cinema.dao.LichSuDAO;
import poly.cinema.dao.impl.LichSuDAOImpl;
import poly.cinema.entity.LichSu;
import poly.cinema.util.TimeRange;
import poly.cinema.util.XDate;

/**
 *
 * @author NITRO
 */
public class LichSuBanHang extends javax.swing.JPanel implements LichSuBanHangController {

    /**
     * Creates new form QuanLyHoaDon
     */
    public LichSuBanHang() {
        initComponents();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBills = new javax.swing.JTable();
        txtBegin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEnd = new javax.swing.JTextField();
        btnLoc = new javax.swing.JButton();
        cboTimeRanges = new javax.swing.JComboBox<>();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1110, 720));

        tblBills.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Thời điểm lập", "Tên nhân viên", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBills.setRowHeight(25);
        jScrollPane1.setViewportView(tblBills);

        txtBegin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));
        txtBegin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBeginActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Từ ngày:");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel1.setText("LỊCH SỬ BÁN HÀNG");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Đến ngày:");

        txtEnd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        btnLoc.setBackground(new java.awt.Color(212, 212, 212));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(51, 51, 51));
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        cboTimeRanges.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hôm nay", "Tuần này", "Tháng này", "Quý này", "Năm này" }));
        cboTimeRanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTimeRangesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBegin, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(btnLoc)
                .addGap(71, 71, 71)
                .addComponent(cboTimeRanges, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1053, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(416, 416, 416)
                        .addComponent(jLabel1)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBegin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(btnLoc)
                    .addComponent(cboTimeRanges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        this.locTheoNgay();
        if (tblBills.getRowCount() == 0 && tblBills.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong khoảng thời gian đã chọn.");
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void cboTimeRangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTimeRangesActionPerformed

    }//GEN-LAST:event_cboTimeRangesActionPerformed

    private void txtBeginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBeginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBeginActionPerformed

    private final LichSuDAO lichSuDAO = new LichSuDAOImpl();
    private List<LichSu> listLichSu;
    private DefaultTableModel model;

    private void initComboTimeRanges() {
        cboTimeRanges.removeAllItems();
        cboTimeRanges.addItem("Hôm nay");
        cboTimeRanges.addItem("Tuần này");
        cboTimeRanges.addItem("Tháng này");
        cboTimeRanges.addItem("Quý này");
        cboTimeRanges.addItem("Năm nay");
    }

    private void initComboTimeRangeListener() {
        if (tblBills.getRowCount() == 0 && tblBills.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu trong khoảng thời gian đã chọn.");
        } else {
            cboTimeRanges.addActionListener(e -> {
                Object selectedObj = cboTimeRanges.getSelectedItem();
                if (selectedObj == null) {
                    return;
                }

                String selected = selectedObj.toString(); // hoặc (String) selectedObj nếu chắc chắn
                TimeRange range = switch (selected) {
                    case "Hôm nay" ->
                        TimeRange.today();
                    case "Tuần này" ->
                        TimeRange.thisWeek();
                    case "Tháng này" ->
                        TimeRange.thisMonth();
                    case "Quý này" ->
                        TimeRange.thisQuarter();
                    case "Năm nay" ->
                        TimeRange.thisYear();
                    default ->
                        null;
                };

                if (range != null) {
                    var sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
                    txtBegin.setText(sdf.format(range.getBegin()));
                    txtEnd.setText(sdf.format(range.getEnd()));

                    locTheoNgay();
                }
            });
        }
    }

    private void fillTableLichSu(List<LichSu> list) {
        listLichSu = list;
        model = (DefaultTableModel) tblBills.getModel();
        model.setRowCount(0);

        for (LichSu ls : listLichSu) {
            model.addRow(new Object[]{
                ls.getMaHd(),
                ls.getNgayLap(),
                ls.getTenNhanVien(),
                ls.getTongTien()
            });
        }
    }

    private void locTheoNgay() {
        try {
            if (txtBegin.getText().trim().isEmpty() || txtEnd.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ ngày bắt đầu và kết thúc.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            Date begin = sdf.parse(txtBegin.getText());
            Date end = sdf.parse(txtEnd.getText());

            if (begin.after(end)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày kết thúc.");
                return;
            }

            // Đặt thời gian
            Calendar cal = Calendar.getInstance();
            cal.setTime(begin);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            begin = cal.getTime();

            cal.setTime(end);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, 1);
            end = cal.getTime();

            List<LichSu> list = lichSuDAO.getByDate(begin, end);
            fillTableLichSu(list);

            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu trong khoảng thời gian đã chọn.");
            }

        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ. Định dạng đúng: dd-MM-yyyy");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu. Vui lòng kiểm tra kết nối.");
            e.printStackTrace();
        }
    }

    @Override
    public void open() {
        fillTable();
        selectTimeRange();
        initComboTimeRanges();
        initComboTimeRangeListener();
    }

    @Override
    public void fillTable() {
        List<LichSu> list = lichSuDAO.selectAll();
        fillTableLichSu(list);
    }

    @Override
    public void fillTableByDate(Date begin, Date end) {
        List<LichSu> list = lichSuDAO.getByDate(begin, end);
        fillTableLichSu(list);
    }

    @Override
    public void clear() {
        DefaultTableModel model = (DefaultTableModel) tblBills.getModel();
        model.setRowCount(0);
    }

    @Override
    public void selectTimeRange() {
        int index = cboTimeRanges.getSelectedIndex();
        TimeRange range = switch (index) {
            case 0 ->
                TimeRange.today();
            case 1 ->
                TimeRange.thisWeek();
            case 2 ->
                TimeRange.thisMonth();
            case 3 ->
                TimeRange.thisQuarter();
            case 4 ->
                TimeRange.thisYear();
            default ->
                TimeRange.today();
        };

        var sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        txtBegin.setText(sdf.format(range.getBegin()));
        txtEnd.setText(sdf.format(range.getEnd()));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private javax.swing.JComboBox<String> cboTimeRanges;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBills;
    private javax.swing.JTextField txtBegin;
    private javax.swing.JTextField txtEnd;
    // End of variables declaration//GEN-END:variables

}
