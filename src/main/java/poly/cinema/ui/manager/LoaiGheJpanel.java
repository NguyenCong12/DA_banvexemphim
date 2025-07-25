package poly.cinema.ui.manager;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.cinema.dao.LoaiGheDAO;
import poly.cinema.dao.impl.LoaiGheDAOImpl;
import poly.cinema.entity.LoaiGhe;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
/**
 *
 * @author BA HAO
 */
public class LoaiGheJpanel extends javax.swing.JPanel implements LoaiGheController {

    /**
     * Creates new form LoaiGhe
     */
    public LoaiGheJpanel() {
        initComponents();
        init();
    }

    private void init() {
        open();
        tblBillDetaills.getSelectionModel().addListSelectionListener(e -> {
            int row = tblBillDetaills.getSelectedRow();
            if (row >= 0 && row < list.size()) {
                moveTo(row);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBillDetaills = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenGhe = new javax.swing.JTextField();
        txtPhuPhi = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        btnNhapMoi = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1110, 720));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel1.setText("LOẠI GHẾ");

        tblBillDetaills.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên ghế", "Phụ phí"
            }
        ));
        tblBillDetaills.setRowHeight(25);
        tblBillDetaills.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillDetaillsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBillDetaills);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tên ghế ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Phụ phí");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa1.setText("XÓA");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });

        btnNhapMoi.setText("NHẬP  MỚI");
        btnNhapMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNhapMoi)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNhapMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtPhuPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(475, 475, 475))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhuPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(44, 44, 44)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        this.create();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnNhapMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapMoiActionPerformed
        // TODO add your handling code here:
        this.clear();
    }//GEN-LAST:event_btnNhapMoiActionPerformed

    private void tblBillDetaillsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillDetaillsMouseClicked
        int row = tblBillDetaills.getSelectedRow();
        if (row >= 0 && row < list.size()) {
            moveTo(row); // đổ dữ liệu dòng được chọn vào form
        }
        updateButtonStatus();
    }//GEN-LAST:event_tblBillDetaillsMouseClicked

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        // TODO add your handling code
        this.delete();
    }//GEN-LAST:event_btnXoa1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNhapMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBillDetaills;
    private javax.swing.JTextField txtPhuPhi;
    private javax.swing.JTextField txtTenGhe;
    // End of variables declaration//GEN-END:variables

    private final LoaiGheDAO dao = new LoaiGheDAOImpl();
    private List<LoaiGhe> list = new ArrayList<>();
    private int currentIndex = -1;

    @Override
    public void open() {
        fillToTable();
        clear();
        setEditable(true);
        updateButtonStatus();
    }

    @Override
    public void setForm(LoaiGhe entity) {
        txtTenGhe.setText(entity.getLoaiGhe());
        txtPhuPhi.setText(String.valueOf(entity.getPhuPhi()));
        txtTenGhe.setEnabled(false);
    }

    private void updateButtonStatus() {
        boolean isSelected = tblBillDetaills.getSelectedRow() >= 0;
        btnThem.setEnabled(!isSelected); // Chỉ bật khi KHÔNG chọn dòng
        btnSua.setEnabled(isSelected);   // Bật khi có chọn dòng
        btnXoa1.setEnabled(isSelected);  // Bật khi có chọn dòng
    }

    @Override
public LoaiGhe getForm() {
    String ma = txtTenGhe.getText().trim();
    String phuPhiStr = txtPhuPhi.getText().trim();

    if (ma.isEmpty() || phuPhiStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
        return null;
    }

    try {
        double phuPhi = Double.parseDouble(phuPhiStr);
        if (phuPhi < 0) {
            JOptionPane.showMessageDialog(this, "Phụ phí không được âm.");
            return null;
        }
        return new LoaiGhe(ma, phuPhi);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Phụ phí phải là số.");
        return null;
    }
}


    @Override
    public void fillToTable() {
        list = dao.findAll();
        DefaultTableModel model = (DefaultTableModel) tblBillDetaills.getModel();
        model.setRowCount(0);
        for (LoaiGhe lg : list) {
            model.addRow(new Object[]{
                lg.getLoaiGhe(),
                lg.getPhuPhi()
            });
        }
        updateButtonStatus();
    }

    @Override
    public void edit() {
        moveTo(currentIndex);
    }

    @Override
    public void create() {
        LoaiGhe entity = getForm();
        if (entity != null) {
            if (dao.findById(entity.getLoaiGhe()) != null) {
                JOptionPane.showMessageDialog(this, "Mã ghế đã tồn tại!");
                return;
            }
            try {
                dao.create(entity);
                fillToTable();
                clear();
                JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu: ");
                e.printStackTrace();
            }
        }
    }

    @Override
public void update() {
    int row = tblBillDetaills.getSelectedRow();
    if (row < 0 || row >= list.size()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn loại ghế cần cập nhật.");
        return;
    }

    LoaiGhe newEntity = getForm();
    if (newEntity == null) {
        return; // getForm đã báo lỗi rồi
    }

    // Lấy dữ liệu cũ trong DB
    LoaiGhe oldEntity = dao.findById(newEntity.getLoaiGhe());
    if (oldEntity == null) {
        JOptionPane.showMessageDialog(this, "Mã ghế không tồn tại để cập nhật!");
        return;
    }

    // ✅ So sánh xem có thay đổi gì không
    if (oldEntity.getPhuPhi() == newEntity.getPhuPhi()) {
        JOptionPane.showMessageDialog(this, "Không có thay đổi nào để cập nhật.");
        return;
    }

    // ✅ Nếu có thay đổi thì mới cập nhật
    try {
        dao.update(newEntity);
        fillToTable();
        clear();
        updateButtonStatus();
        JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi cập nhật: ");
        e.printStackTrace();
    }
}


    @Override
    public void delete() {
        int row = tblBillDetaills.getSelectedRow();
        if (row >= 0 && row < list.size()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa loại ghế này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String ma = (String) tblBillDetaills.getValueAt(row, 0);
                try {
                    dao.deleteById(ma);
                    fillToTable();
                    clear();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Không thể xóa vì loại ghế này đang được sử dụng!");
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại ghế cần xóa.");
        }
    }

    @Override
    public void clear() {
        txtTenGhe.setText("");
        txtPhuPhi.setText("");
        tblBillDetaills.clearSelection();
        currentIndex = -1;
        txtTenGhe.setEnabled(true);
        updateButtonStatus();
    }

    @Override
    public void setEditable(boolean editable) {
        txtTenGhe.setEditable(editable);
    }

    @Override
    public void moveFirst() {
        moveTo(0);
    }

    @Override
    public void movePrevious() {
        if (currentIndex > 0) {
            moveTo(currentIndex - 1);
        }
    }

    @Override
    public void moveNext() {
        if (currentIndex < list.size() - 1) {
            moveTo(currentIndex + 1);
        }
    }

    @Override
    public void moveLast() {
        moveTo(list.size() - 1);
    }

    @Override
    public void moveTo(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < list.size()) {
            currentIndex = rowIndex;
            setForm(list.get(currentIndex));
        }
    }

    @Override
    public void selectTimeRange() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
