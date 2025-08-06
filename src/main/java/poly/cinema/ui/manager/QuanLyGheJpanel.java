/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.cinema.ui.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import poly.cinema.dao.LoaiGheDAO;
import poly.cinema.dao.QuanLyGheDao;
import poly.cinema.dao.impl.LoaiGheDAOImpl;
import poly.cinema.dao.impl.QuanLyGheDaoImpl;
import poly.cinema.entity.QuanLyGhe;

/**
 *
 * @author Admin
 */
public class QuanLyGheJpanel extends javax.swing.JPanel implements QuanLyGheController {

    /**
     * Creates new form QuanLyGheJpanel
     */
    private final QuanLyGheDao dao = new QuanLyGheDaoImpl();
    private final List<QuanLyGhe> items = new ArrayList<>();

    private QuanLyGhe gheDangChon;
    private String maGheDangChon = null;

    public QuanLyGheJpanel() {
        setLayout(new BorderLayout());
        initUI();
        open();
    }

    @Override
    public void open() {
        loadLoaiGhe();
        loadPhongChieu();
        loadGhe();
    }

    private void initUI() {
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cboPhong = new JComboBox<>();
        cboPhong.addActionListener(e -> loadGhe());
        pnlTop.add(new JLabel("Phòng:"));
        pnlTop.add(cboPhong);
        add(pnlTop, BorderLayout.NORTH);

        pnlGhe = new JPanel();
        add(new JScrollPane(pnlGhe), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cboLoaiGhe = new JComboBox<>();
        loadLoaiGhe(); // 🔹 load từ DB

        cboTrangThai = new JComboBox<>(new String[]{"Bình thường", "Hư", "Cho nhân viên"});
        btnCapNhat = new JButton("Cập nhật");

        btnCapNhat.addActionListener(e -> {
            if (gheDangChon == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ghế.");
                return;
            }

            String newLoaiGhe = (String) cboLoaiGhe.getSelectedItem();
            String newTrangThai = (String) cboTrangThai.getSelectedItem();

            if (newLoaiGhe.equals(gheDangChon.getLoaiGhe()) && newTrangThai.equals(gheDangChon.getTrangThai())) {
                JOptionPane.showMessageDialog(this, "Không có thay đổi để cập nhật.");
                return;
            }

            gheDangChon.setLoaiGhe(newLoaiGhe);
            gheDangChon.setTrangThai(newTrangThai);

            boolean ok = dao.updateWithResult(gheDangChon);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                // Ghi nhớ mã ghế được chọn
                maGheDangChon = gheDangChon.getMaGhe() + "";
                loadGhe(); // sẽ khôi phục gheDangChon bên trong loadGhe
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        if (maGheDangChon != null) {
            for (QuanLyGhe g : items) {
                if (g.getMaGhe().equals(maGheDangChon)) {
                    gheDangChon = g;
                    break;
                }
            }
        }

        pnlBottom.add(new JLabel("Loại ghế:"));
        pnlBottom.add(cboLoaiGhe);
        pnlBottom.add(new JLabel("Trạng thái:"));
        pnlBottom.add(cboTrangThai);
        pnlBottom.add(btnCapNhat);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private void loadLoaiGhe() {
        cboLoaiGhe.removeAllItems();
        LoaiGheDAO loaiGheDao = new LoaiGheDAOImpl();
        List<String> dsLoaiGhe = loaiGheDao.findAllTenLoaiGhe();
        for (String ten : dsLoaiGhe) {
            cboLoaiGhe.addItem(ten);
        }
    }

    void loadPhongChieu() {
        cboPhong.removeAllItems();
        List<String> maPhongs = dao.getAllMaPhong();
        for (String ma : maPhongs) {
            cboPhong.addItem(ma);
        }
        if (!maPhongs.isEmpty()) {
            cboPhong.setSelectedIndex(0);
        }
    }

    void loadGhe() {
        pnlGhe.removeAll();
        String maPhong = (String) cboPhong.getSelectedItem();
        if (maPhong == null) {
            return;
        }
        items.clear();
        items.addAll(dao.findByMaPhong(maPhong));

        int maxCot = items.stream().mapToInt(QuanLyGhe::getCot).max().orElse(5);
        pnlGhe.setLayout(new GridLayout(0, maxCot, 5, 5));

        for (QuanLyGhe g : items) {
            JButton btn = new JButton(g.getSoGhe());
            btn.setOpaque(true);
            btn.setBackground(getColorByLoaiVaTrangThai(g.getLoaiGhe(), g.getTrangThai()));
            btn.setToolTipText("Ghế " + g.getSoGhe() + " - " + g.getLoaiGhe() + " - " + g.getTrangThai());

            // 👉 Đánh dấu ghế đang chọn
            if (maGheDangChon != null && g.getMaGhe().toString().equals(maGheDangChon)) {
                gheDangChon = g; // gán lại sau khi load
                btn.setFont(btn.getFont().deriveFont(Font.BOLD)); // in đậm
                btn.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // thêm viền đỏ
            }

            btn.addActionListener(e -> {
                gheDangChon = g;
                cboLoaiGhe.setSelectedItem(g.getLoaiGhe());
                cboTrangThai.setSelectedItem(g.getTrangThai());

                // 👉 Ghi nhớ ghế mới khi chọn thủ công
                maGheDangChon = g.getMaGhe().toString();

                // 👉 Load lại giao diện để cập nhật in đậm
                loadGhe();
            });

            pnlGhe.add(btn);
        }

        pnlGhe.revalidate();
        pnlGhe.repaint();
    }

    private Color getColorByLoaiVaTrangThai(String loai, String trangThai) {
        if ("Hư".equals(trangThai)) {
            return Color.DARK_GRAY;
        }
        if ("Cho nhân viên".equals(trangThai)) {
            return Color.CYAN;
        }
        if ("VIP".equals(loai)) {
            return Color.ORANGE;
        }
        return Color.LIGHT_GRAY;
    }

    // ✅ Phương thức này để bên khác gọi sau khi thêm phòng chiếu mới
    public void capNhatDanhSachPhong(String maPhongMoi) {
        loadPhongChieu();
        cboPhong.setSelectedItem(maPhongMoi); // tự động chọn phòng vừa thêm
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cboPhong = new javax.swing.JComboBox<>();
        cboLoaiGhe = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnCapNhat = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        pnlGhe = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1110, 720));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ GHẾ");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(438, 6, 291, 88));

        jPanel3.setBackground(new java.awt.Color(212, 212, 212));
        jPanel3.setPreferredSize(new java.awt.Dimension(111, 111));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Lọc theo phòng chiếu");

        cboPhong.setEditable(true);
        cboPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPhongActionPerformed(evt);
            }
        });

        cboLoaiGhe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thường", "Vip", " " }));
        cboLoaiGhe.setToolTipText("");
        cboLoaiGhe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiGheActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Thay đổi loại ghế");

        btnCapNhat.setText("Sửa");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Trạng thái");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bình thường", "Hư", "Cho nhân viên" }));
        cboTrangThai.setToolTipText("");
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(102, 102, 102)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboLoaiGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(btnCapNhat))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboLoaiGhe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(44, 44, 44))
        );

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 590, 1080, 90));

        javax.swing.GroupLayout pnlGheLayout = new javax.swing.GroupLayout(pnlGhe);
        pnlGhe.setLayout(pnlGheLayout);
        pnlGheLayout.setHorizontalGroup(
            pnlGheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );
        pnlGheLayout.setVerticalGroup(
            pnlGheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        add(pnlGhe, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 770, 490));
    }// </editor-fold>//GEN-END:initComponents

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed

    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void cboLoaiGheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiGheActionPerformed

    }//GEN-LAST:event_cboLoaiGheActionPerformed

    private void cboPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPhongActionPerformed

    }//GEN-LAST:event_cboPhongActionPerformed

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTrangThaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JComboBox<String> cboLoaiGhe;
    private javax.swing.JComboBox<String> cboPhong;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel pnlGhe;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setForm(QuanLyGhe entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public QuanLyGhe getForm() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void fillToTable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void edit() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void create() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setEditable(boolean editable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void moveFirst() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void movePrevious() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void moveNext() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void moveLast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void moveTo(int rowIndex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void selectTimeRange() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
