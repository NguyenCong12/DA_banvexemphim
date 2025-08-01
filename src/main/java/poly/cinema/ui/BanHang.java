/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.cinema.ui;

import java.awt.CardLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import poly.cinema.dao.MatHangDAO;
import poly.cinema.dao.impl.ChiTietHangDAOImpl;
import poly.cinema.dao.impl.ChiTietVeDAOImpl;
import poly.cinema.dao.impl.HoaDonDaoImpl;
import poly.cinema.dao.impl.MatHangDAOImpl;
import poly.cinema.dao.impl.VeDaoImpl;
import poly.cinema.entity.DangNhapSession;
import poly.cinema.entity.DatHang;
import poly.cinema.entity.DatVeSession;
import poly.cinema.entity.NguoiDung;
import poly.cinema.entity.SanPhamSession;
import poly.cinema.util.PDFExporter;

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
                try {
                    if (DatVeSession.getDanhSachGheDaChon() != null && !DatVeSession.getDanhSachGheDaChon().isEmpty()) {
                        updateSauKhiChonGhe();
                    }
                    loadSanPhamTuTam();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(BanHang.this, "Lỗi khi tải dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        txtTienKhachDua.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTienThua();
            }
        });
    }

    private double parseTien(String text) {
        try {
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            text = text.replace(" VNĐ", "").trim();
            return nf.parse(text).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public void refreshData() {
        updateSauKhiChonGhe();
        loadSanPhamTuTam();
    }

    public void updateSauKhiChonGhe() {
        List<String> gheChon = DatVeSession.getDanhSachGheDaChon();
        if (gheChon == null || gheChon.isEmpty()) {
            return;
        }

        int maXuat = DatVeSession.getMaXuat();
        List<Object[]> ds = layDanhSachVe(maXuat, gheChon);
        fillThongTinVe(ds);
    }

    private List<Object[]> layDanhSachVe(int maXuat, List<String> gheChon) {
        List<Object[]> ds = new ArrayList<>();
        for (String maGhe : gheChon) {
            try {
                Object[] row = veDao.getThongTinVe(maXuat, maGhe);
                if (row != null) {
                    ds.add(row);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ds;
    }

    public void fillThongTinVe(List<Object[]> ds) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        tongTienVe = 0;

        for (Object[] row : ds) {
            try {
                if (row.length > 6 && row[6] instanceof Number) {
                    double tien = ((Number) row[6]).doubleValue();
                    tongTienVe += tien;
                    row[3] = dinhDangTien(tien);
                }
                model.addRow(row);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        txtTienVe.setText(dinhDangTien(tongTienVe));
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

    public void open() {
    }

    public void thanhToan() {
        try {
            double tongTien = parseTien(txtTongTien.getText());
            double tienKhachDua = parseTien(txtTienKhachDua.getText());

            if (tongTien <= 0) {
                JOptionPane.showMessageDialog(this, "Chưa có vé hoặc sản phẩm!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txtTienKhachDua.getText().trim().isEmpty() || tienKhachDua < tongTien) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa không hợp lệ hoặc không đủ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            NguoiDung nguoiDung = DangNhapSession.getNguoiDung();
            if (nguoiDung == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Khởi tạo DAO
            HoaDonDaoImpl hoaDonDao = new HoaDonDaoImpl();
            ChiTietVeDAOImpl ctVeDao = new ChiTietVeDAOImpl();
            ChiTietHangDAOImpl ctSpDao = new ChiTietHangDAOImpl();

            // Tạo hóa đơn trước
            int maHoaDon = hoaDonDao.taoHoaDon(nguoiDung.getMaNd(), tongTien);

            // ====================== XỬ LÝ VÉ ======================
            Integer maXuat = DatVeSession.getMaXuat(); // Có thể null nếu chỉ bán sản phẩm
            List<String> danhSachGhe = DatVeSession.getDanhSachGheDaChon();

            if (maXuat != null && danhSachGhe != null && !danhSachGhe.isEmpty()) {
                List<Object[]> danhSachVe = layDanhSachVe(maXuat, danhSachGhe);
                for (Object[] row : danhSachVe) {
                    int maGhe = (int) row[0];
                    double giaVe = Double.parseDouble(row[6].toString());
                    ctVeDao.themChiTietVe(maHoaDon, maXuat, maGhe, giaVe);
                }
            }

            // ====================== XỬ LÝ SẢN PHẨM ======================
            List<DatHang> dsSanPham = SanPhamSession.getAll();

            if (dsSanPham != null && !dsSanPham.isEmpty()) {
                for (DatHang sp : dsSanPham) {
                    String tenSp = sp.getTenSanPham();
                    double gia = sp.getGia();
                    int soLuong = sp.getSoLuong();

                    MatHangDAO matHangDAO = new MatHangDAOImpl();
                    int maHang = matHangDAO.timMaHangTheoTen(tenSp);

                    ctSpDao.themChiTietSanPham(maHoaDon, maHang, gia, soLuong);
                }
            }

            // ====================== IN HÓA ĐƠN PDF ======================
            try {
                PDFExporter exporter = new PDFExporter();

                String fileName = "hoadon_" + System.currentTimeMillis() + ".pdf";
                String filePath = "hoadon/" + fileName; // Thư mục phải tồn tại

                String maPhieu = "HD" + maHoaDon;
                String nhanVien = nguoiDung.getTenNd();
                String trangThai = "Đã thanh toán";
                String checkin = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                String checkout = checkin;

                // Gộp dữ liệu từ 2 bảng vào bảng tạm để in
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new Object[]{"#", "Tên", "Giá", "SL", "Thành tiền"});

                for (int i = 0; i < tblHoaDon.getRowCount(); i++) {
                    model.addRow(new Object[]{
                        i + 1,
                        tblHoaDon.getValueAt(i, 1),
                        tblHoaDon.getValueAt(i, 3),
                        1,
                        tblHoaDon.getValueAt(i, 3)
                    });
                }

                for (int i = 0; i < tblchitiethang.getRowCount(); i++) {
                    model.addRow(new Object[]{
                        tblHoaDon.getRowCount() + i + 1,
                        tblchitiethang.getValueAt(i, 0),
                        tblchitiethang.getValueAt(i, 1),
                        tblchitiethang.getValueAt(i, 2),
                        tblchitiethang.getValueAt(i, 3)
                    });
                }

                JTable tableToPrint = new JTable(model);

                exporter.exportBillToPDF(
                        filePath,
                        maPhieu,
                        nhanVien,
                        trangThai,
                        checkout,
                        tableToPrint,
                        tongTien,
                        tienKhachDua,
                        tienKhachDua - tongTien
                );

                JOptionPane.showMessageDialog(this, "Đã thanh toán và in hóa đơn PDF:\n" + filePath, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi in hóa đơn PDF!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            // ====================== DỌN DẸP SAU KHI THANH TOÁN ======================
            ((DefaultTableModel) tblHoaDon.getModel()).setRowCount(0);
            ((DefaultTableModel) tblchitiethang.getModel()).setRowCount(0);
            txtTienVe.setText("0");
            txtTienSanPham.setText("0");
            txtTongTien.setText("0");
            txtTienKhachDua.setText("");
            txtTienThua.setText("0");

            DatVeSession.clear();
            SanPhamSession.clear();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi khi thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String dinhDangTien(double soTien) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf.format(soTien) + " VNĐ";
    }

    public void loadSanPhamTuTam() {
        try {
            List<DatHang> ds = SanPhamSession.getAll();
            DefaultTableModel model = (DefaultTableModel) tblchitiethang.getModel();
            model.setRowCount(0);

            double tongTienSanPham = 0;
            for (DatHang sp : ds) {
                double gia = sp.getGia();
                double thanhTien = sp.getThanhTien();
                model.addRow(new Object[]{
                    sp.getTenSanPham(),
                    dinhDangTien(gia),
                    sp.getSoLuong(),
                    dinhDangTien(thanhTien)
                });
                tongTienSanPham += thanhTien;
            }

            double tienVe = parseTien(txtTienVe.getText());
            double tong = tienVe + tongTienSanPham;

            txtTienSanPham.setText(dinhDangTien(tongTienSanPham));
            txtTongTien.setText(dinhDangTien(tong));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setDanhSachHoaDon(List<Object[]> ds) {
        DefaultTableModel model = (DefaultTableModel) tblchitiethang.getModel();
        model.setRowCount(0);
        for (Object[] row : ds) {
            model.addRow(row);
        }
    }

    private void huyHoaDon() {
        int confirm = JOptionPane.showConfirmDialog(
                this, "Bạn có chắc chắn muốn hủy toàn bộ hóa đơn?", "Xác nhận", JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ((DefaultTableModel) tblHoaDon.getModel()).setRowCount(0);
        ((DefaultTableModel) tblchitiethang.getModel()).setRowCount(0);

        txtTienVe.setText("0");
        txtTienSanPham.setText("0");
        txtTongTien.setText("0");
        txtTienKhachDua.setText("0");
        txtTienThua.setText("0");
        DatVeSession.clear();
        SanPhamSession.clear();

        JOptionPane.showMessageDialog(this, "Đã hủy hóa đơn.");
    }

    private void updateTienThua() {
        try {
            double tienKhachDua = parseTien(txtTienKhachDua.getText());
            double tongTien = parseTien(txtTongTien.getText());
            double tienThua = tienKhachDua - tongTien;

            txtTienThua.setText(dinhDangTien(Math.max(tienThua, 0)));
        } catch (Exception e) {
            txtTienThua.setText("0");
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
        txtTienSanPham = new javax.swing.JTextField();
        txtTienVe = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        txtTienKhachDua = new javax.swing.JTextField();
        txtTienThua = new javax.swing.JTextField();
        btnChonPhim = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

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

            },
            new String [] {
                "Mã chi tiết vé", "Tên phim", "Ngày chiếu", "Giá vé", "Phòng chiếu", "Số ghế"
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

        txtTienSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienSanPhamActionPerformed(evt);
            }
        });

        txtTienVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienVeActionPerformed(evt);
            }
        });

        jLabel6.setText("Tổng tiền:");

        jLabel7.setText("Tiền khách đưa:");

        jLabel8.setText("Tiền thừa:");

        btnChonPhim.setText("Chọn phim");
        btnChonPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonPhimActionPerformed(evt);
            }
        });

        jButton2.setText("Chọn sản phẩm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Thanh toán");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Hủy");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTienVe)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTienSanPham))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblchonphim))
                                .addGap(55, 55, 55)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblchonsanpham)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jButton3)
                                            .addGap(18, 18, 18)
                                            .addComponent(jButton4))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton2)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(113, 113, 113)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(99, 99, 99)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnChonPhim))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblchonphim)
                    .addComponent(lblchonsanpham))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChonPhim)
                    .addComponent(jButton2))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienVe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(49, 49, 49))
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

    private void txtTienSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienSanPhamActionPerformed

    private void txtTienVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienVeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienVeActionPerformed

    private void lblchonphimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblchonphimMouseClicked

    }//GEN-LAST:event_lblchonphimMouseClicked

    private void lblchonsanphamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblchonsanphamMouseClicked


    }//GEN-LAST:event_lblchonsanphamMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        thanhToan();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        huyHoaDon();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnChonPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonPhimActionPerformed
        chuyenPanel("pnlChonPhim");
    }//GEN-LAST:event_btnChonPhimActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        chuyenPanel("pnlBanSanPham");
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonPhim;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblchonphim;
    private javax.swing.JLabel lblchonsanpham;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblchitiethang;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienSanPham;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTienVe;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables

}
