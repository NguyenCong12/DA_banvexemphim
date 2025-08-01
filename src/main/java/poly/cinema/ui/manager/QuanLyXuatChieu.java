/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.cinema.ui.manager;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import poly.cinema.dao.QuanLyPhimDao;
import poly.cinema.dao.QuanLyPhongChieuDao;
import poly.cinema.dao.QuanLySuatChieuDao;
import poly.cinema.dao.impl.QuanLyPhimDaoImpl;
import poly.cinema.dao.impl.QuanLyPhongChieuDaoImpl;
import poly.cinema.dao.impl.QuanLySuatChieuDaoImpl;
import poly.cinema.entity.Phim;
import poly.cinema.entity.PhongChieu;
import poly.cinema.entity.SuatChieu;
import static poly.cinema.util.TimeRange.today;
import poly.cinema.util.XDialog;

/**
 *
 * @author ADMIN
 */
public class QuanLyXuatChieu extends javax.swing.JPanel implements QuanLySuatChieuController {

    /**
     * Creates new form SuatChieu
     */
    public QuanLyXuatChieu() {
        initComponents();
        open();
    }
    private QuanLySuatChieuDao dao = new QuanLySuatChieuDaoImpl();
    private List<SuatChieu> items = new ArrayList<>();
    private List<Phim> phimList = new ArrayList<>();

    @Override
    public void open() {
        loadComboboxes();

        chooserNgayChieu.setLocale(new Locale("vi", "VN"));
        chooserNgayChieu1.setLocale(new Locale("vi", "VN"));

        chooserNgayChieu.setDateFormatString("dd/MM/yyyy");
        chooserNgayChieu1.setDateFormatString("dd/MM/yyyy");

        cboPhim1.setSelectedItem("-- Tất cả --");
        cboPhong1.setSelectedItem("-- Tất cả --");

        // 👉 Thêm dòng này để lọc mặc định hôm nay
        chooserNgayChieu1.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        cboPhim1.addActionListener(e -> {
            filterTable();
            suggestNextGioChieu();
        });

        cboPhong1.addActionListener(e -> {
            filterTable();
            suggestNextGioChieu();
        });

        chooserNgayChieu1.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                filterTable();
                suggestNextGioChieu();
            }
        });

        SpinnerDateModel model2 = new SpinnerDateModel();
        spnGioChieu.setModel(model2);
        spnGioChieu.setEditor(new JSpinner.DateEditor(spnGioChieu, "HH:mm"));
        setEditable(false);
        fillToTable();  // ⬅ tự động fill theo hôm nay vì chooserNgayChieu1 đã được set sẵn
        clear();
        txtMaXuat.setEditable(false);
        txtMaPhim.setEditable(false);
    }

    @Override
    public SuatChieu getForm() {
        try {
            String tenPhim = (String) cboPhim.getSelectedItem();
            if (tenPhim == null || tenPhim.equals("-- Chưa chọn --")) {
                XDialog.alert("Vui lòng chọn phim!");
                return null;
            }
            int maPhim = phimList.stream()
                    .filter(p -> p.getTenPhim().equals(tenPhim))
                    .findFirst()
                    .map(Phim::getMaPhim)
                    .orElse(-1);

            String maPhong = (String) cboPhong.getSelectedItem();
            if (maPhong == null || maPhong.equals("-- Chưa chọn --")) {
                XDialog.alert("Vui lòng chọn phòng chiếu!");
                return null;
            }

            LocalDate ngayChieu = chooserNgayChieu.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalTime gioChieu = ((Date) spnGioChieu.getValue())
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime();

            // Xử lý giá vé: xoá dấu . hoặc ,
            String giaVeStr = txtGiaVe.getText().trim().replace(".", "").replace(",", "");
            BigDecimal giaVe = new BigDecimal(giaVeStr);

            SuatChieu sc = SuatChieu.builder()
                    .maPhim(maPhim)
                    .maPhong(maPhong)
                    .ngayChieu(ngayChieu)
                    .gioChieu(gioChieu)
                    .giaVe(giaVe)
                    .build();

            if (!txtMaXuat.getText().trim().isEmpty()) {
                sc.setMaXuat(Integer.parseInt(txtMaXuat.getText().trim()));
            }

            return sc;
        } catch (Exception e) {
            XDialog.alert("Vui lòng nhập đúng định dạng dữ liệu!");
            return null;
        }
    }

    @Override
    public void setForm(SuatChieu sc) {
        txtMaXuat.setText(String.valueOf(sc.getMaXuat()));
        txtMaPhim.setText(String.valueOf(sc.getMaPhim()));

        phimList.stream()
                .filter(p -> p.getMaPhim() == sc.getMaPhim())
                .findFirst()
                .ifPresent(p -> cboPhim.setSelectedItem(p.getTenPhim()));

        cboPhong.setSelectedItem(sc.getMaPhong());

        chooserNgayChieu.setDate(Date.from(sc.getNgayChieu()
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));

        spnGioChieu.setValue(Date.from(
                sc.getGioChieu().atDate(LocalDate.now())
                        .atZone(ZoneId.systemDefault()).toInstant()));

        NumberFormat vnFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        txtGiaVe.setText(vnFormat.format(sc.getGiaVe()));
    }

    @Override
    public void fillToTable() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        DefaultTableModel model = (DefaultTableModel) tblSuatChieu.getModel();
        model.setRowCount(0); // Xóa bảng cũ

        items = dao.findAll(); // Cập nhật lại danh sách gốc

        for (SuatChieu sc : items) {
            String tenPhim = phimList.stream()
                    .filter(p -> p.getMaPhim() == sc.getMaPhim())
                    .map(Phim::getTenPhim)
                    .findFirst()
                    .orElse("Không rõ");

            String giaVeFormatted = String.format("%,.0f VNĐ", sc.getGiaVe());

            model.addRow(new Object[]{
                sc.getMaXuat(), // Cột 0: Mã suất
                tenPhim, // Cột 1: Tên phim
                sc.getMaPhong(), // Cột 2: Mã phòng
                sc.getNgayChieu().format(dateFormatter), // Cột 3: Ngày chiếu
                sc.getGioChieu().format(timeFormatter), // Cột 4: Giờ chiếu
                giaVeFormatted // Cột 5: Giá vé đã định dạng
            });
        }
    }

    @Override
    public void create() {
        SuatChieu sc = getForm();
        if (sc == null) {
            return;
        }

        // Ngày chiếu phải từ hôm nay trở đi
        if (sc.getNgayChieu().isBefore(LocalDate.now())) {
            XDialog.alert("Ngày chiếu không được nhỏ hơn ngày hiện tại!");
            return;
        }

        if (!sc.getNgayChieu().isAfter(LocalDate.now())) {
            XDialog.alert("Chỉ được tạo suất chiếu từ ngày mai trở đi!");
            return;
        }

        // Giờ chiếu hợp lệ (7h - 23h)
        if (sc.getGioChieu().isBefore(LocalTime.of(7, 0)) || sc.getGioChieu().isAfter(LocalTime.of(23, 0))) {
            XDialog.alert("Giờ chiếu phải trong khoảng từ 07:00 đến 23:00!");
            return;
        }

        // Giá vé > 0
        if (sc.getGiaVe().compareTo(BigDecimal.ZERO) <= 0) {
            XDialog.alert("Giá vé phải lớn hơn 0!");
            return;
        }

        // Phim còn tồn tại không?
        boolean phimTonTai = phimList.stream().anyMatch(p -> p.getMaPhim() == sc.getMaPhim());
        if (!phimTonTai) {
            XDialog.alert("Phim đã chọn không còn tồn tại!");
            return;
        }

        // Trùng hoặc sát suất chiếu khác
        if (isOverlapping(sc)) {
            XDialog.alert("Suất chiếu bị trùng hoặc cách nhau không đủ thời gian!");
            return;
        }

        dao.create(sc);
        filterTable();
        clear();
        XDialog.alert("Thêm suất chiếu thành công!");
    }

    @Override
    public void update() {
        int row = tblSuatChieu.getSelectedRow();
        if (row < 0) {
            XDialog.alert("Vui lòng chọn dòng cần cập nhật.");
            return;
        }

        SuatChieu sc = getForm();
        if (sc == null) {
            return;
        }

        int maXuat = (Integer) tblSuatChieu.getValueAt(row, 0);
        sc.setMaXuat(maXuat); // Giữ mã xuất cũ

        // Không cho sửa suất chiếu đã diễn ra
        if (sc.getNgayChieu().isBefore(LocalDate.now())) {
            XDialog.alert("Không thể cập nhật suất chiếu đã diễn ra!");
            return;
        }

        // Giờ chiếu hợp lệ (7h - 23h)
        if (sc.getGioChieu().isBefore(LocalTime.of(7, 0)) || sc.getGioChieu().isAfter(LocalTime.of(23, 0))) {
            XDialog.alert("Giờ chiếu phải trong khoảng từ 07:00 đến 23:00!");
            return;
        }

        // Giá vé > 0
        if (sc.getGiaVe().compareTo(BigDecimal.ZERO) <= 0) {
            XDialog.alert("Giá vé phải lớn hơn 0!");
            return;
        }

        // Phim còn tồn tại không?
        boolean phimTonTai = phimList.stream().anyMatch(p -> p.getMaPhim() == sc.getMaPhim());
        if (!phimTonTai) {
            XDialog.alert("Phim đã chọn không còn tồn tại!");
            return;
        }

        // Trùng lịch chiếu
        if (isOverlapping(sc)) {
            XDialog.alert("Suất chiếu bị trùng hoặc cách nhau không đủ thời gian!");
            return;
        }

        dao.update(sc);
        filterTable();
        XDialog.alert("Cập nhật thành công!");
    }

    @Override
    public void delete() {
        int row = tblSuatChieu.getSelectedRow();
        if (row < 0) {
            XDialog.alert("Vui lòng chọn dòng cần xóa.");
            return;
        }
        if (!XDialog.confirm("Bạn có chắc muốn xóa suất chiếu này?")) {
            return;
        }
        dao.deleteById((Integer) tblSuatChieu.getValueAt(row, 0));
        fillToTable();
        clear();
        XDialog.alert("Xóa thành công!");
    }

    @Override
    public void clear() {
        cboPhim.setSelectedIndex(0);
        cboPhong.setSelectedIndex(0);
        chooserNgayChieu.setDate(null);
        spnGioChieu.setModel(new SpinnerDateModel());
        spnGioChieu.setEditor(new JSpinner.DateEditor(spnGioChieu, "HH:mm"));
        spnGioChieu.setValue(new Date()); // cần set trước để tránh lỗi
        ((JSpinner.DefaultEditor) spnGioChieu.getEditor()).getTextField().setText("");
        txtGiaVe.setText("");
        txtMaPhim.setText("");
        txtMaXuat.setText("");
        tblSuatChieu.clearSelection();
        filterTable();
        setEditable(false);
    }

    @Override
    public void setEditable(boolean editable) {
        btnLamMoi.setEnabled(true); // luôn bật làm mới
        btnThem.setEnabled(!editable); // nếu đang edit thì tắt nút thêm
        btnXoa.setEnabled(editable);
    }

    @Override
    public void moveFirst() {
        if (!items.isEmpty()) {
            moveTo(0);
        }
    }

    @Override
    public void movePrevious() {
        int row = tblSuatChieu.getSelectedRow();
        if (row > 0) {
            moveTo(row - 1);
        }
    }

    @Override
    public void moveNext() {
        int row = tblSuatChieu.getSelectedRow();
        if (row < items.size() - 1) {
            moveTo(row + 1);
        }
    }

    @Override
    public void moveLast() {
        if (!items.isEmpty()) {
            moveTo(items.size() - 1);
        }
    }

    @Override
    public void moveTo(int rowIndex) {
        tblSuatChieu.setRowSelectionInterval(rowIndex, rowIndex);
        setForm(items.get(rowIndex));
    }

    @Override
    public void edit() {
        int row = tblSuatChieu.getSelectedRow();
        if (row >= 0) {
            int maXuat = (Integer) tblSuatChieu.getValueAt(row, 0);
            SuatChieu sc = dao.findById(maXuat);
            if (sc != null) {
                setForm(sc);
                setEditable(true);
            }
        }
    }

    @Override
    public void selectTimeRange() {
        // Chưa áp dụng
    }

    private void suggestNextGioChieu() {
        String maPhong = (String) cboPhong1.getSelectedItem();
        String tenPhim = (String) cboPhim1.getSelectedItem();
        if (maPhong == null || tenPhim == null || tenPhim.equals("-- Chưa chọn --") || maPhong.equals("-- Chưa chọn --")) {
            return;
        }
        LocalDate ngayChieu;
        try {
            ngayChieu = chooserNgayChieu1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            return;
        }
        List<SuatChieu> suatCungNgayPhong = items.stream()
                .filter(sc -> sc.getMaPhong().equals(maPhong) && sc.getNgayChieu().isEqual(ngayChieu))
                .sorted(Comparator.comparing(SuatChieu::getGioChieu))
                .toList();
        if (suatCungNgayPhong.isEmpty()) {
            spnGioChieu.setValue(Date.from(LocalTime.of(8, 0).atDate(ngayChieu).atZone(ZoneId.systemDefault()).toInstant()));
            return;
        }
        SuatChieu last = suatCungNgayPhong.getLast();
        int duration = phimList.stream().filter(p -> p.getMaPhim() == last.getMaPhim()).mapToInt(Phim::getThoiLuong).findFirst().orElse(0);
        LocalTime nextTime = last.getGioChieu().plusMinutes(duration + 15);
        spnGioChieu.setValue(Date.from(nextTime.atDate(ngayChieu).atZone(ZoneId.systemDefault()).toInstant()));
    }

    private boolean isOverlapping(SuatChieu newSc) {
        items = dao.findAll();
        int duration = phimList.stream().filter(p -> p.getMaPhim() == newSc.getMaPhim()).mapToInt(Phim::getThoiLuong).findFirst().orElse(0);
        for (SuatChieu sc : items) {
            if (newSc.getMaXuat() != 0 && sc.getMaXuat() == newSc.getMaXuat()) {
                continue;
            }
            if (sc.getMaPhong().equals(newSc.getMaPhong()) && sc.getNgayChieu().isEqual(newSc.getNgayChieu())) {
                LocalTime start1 = sc.getGioChieu();
                int dur1 = phimList.stream().filter(p -> p.getMaPhim() == sc.getMaPhim()).mapToInt(Phim::getThoiLuong).findFirst().orElse(0);
                LocalTime end1 = start1.plusMinutes(dur1 + 15);
                LocalTime start2 = newSc.getGioChieu();
                LocalTime end2 = start2.plusMinutes(duration + 15);
                if (!end1.isBefore(start2) && !end2.isBefore(start1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void loadComboboxes() {
        QuanLyPhimDao phimDao = new QuanLyPhimDaoImpl();
        phimList = phimDao.findAll();

        cboPhim.removeAllItems();
        cboPhim1.removeAllItems();
        cboPhim.addItem("-- Chưa chọn --");
        cboPhim1.addItem("-- Tất cả --"); // Lọc lịch sử

        for (Phim phim : phimList) {
            //Chỉ thêm phim đang chiếu vào cboPhim
            if (!"Ngừng chiếu".equalsIgnoreCase(phim.getTrangThai())) {
                cboPhim.addItem(phim.getTenPhim());
            }

            //Thêm tất cả phim (kể cả ngừng chiếu) vào cboPhim1
            cboPhim1.addItem(phim.getTenPhim());
        }

        QuanLyPhongChieuDao phongDao = new QuanLyPhongChieuDaoImpl();
        List<PhongChieu> phongList = phongDao.findAll();
        cboPhong.removeAllItems();
        cboPhong1.removeAllItems();
        cboPhong.addItem("-- Chưa chọn --");
        cboPhong1.addItem("-- Tất cả --");

        for (PhongChieu pc : phongList) {
            cboPhong.addItem(pc.getMaPhong());
            cboPhong1.addItem(pc.getMaPhong());
        }
    }

    private void filterTable() {
        String tenPhim = (String) cboPhim1.getSelectedItem();
        String maPhong = (String) cboPhong1.getSelectedItem();
        LocalDate selectedDate = null;

        try {
            Date date = chooserNgayChieu1.getDate();
            if (date != null) {
                selectedDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        } catch (Exception ignored) {
        }

        DefaultTableModel model = (DefaultTableModel) tblSuatChieu.getModel();
        model.setRowCount(0);
        items = dao.findAll();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (SuatChieu sc : items) {
            boolean matchPhim = tenPhim == null || tenPhim.equals("-- Tất cả --")
                    || phimList.stream().anyMatch(p -> p.getMaPhim() == sc.getMaPhim() && p.getTenPhim().equals(tenPhim));

            boolean matchPhong = maPhong == null || maPhong.equals("-- Tất cả --")
                    || sc.getMaPhong().equals(maPhong);

            boolean matchNgay = selectedDate == null || sc.getNgayChieu().isEqual(selectedDate);

            if (matchPhim && matchPhong && matchNgay) {
                String ten = phimList.stream()
                        .filter(p -> p.getMaPhim() == sc.getMaPhim())
                        .map(Phim::getTenPhim)
                        .findFirst()
                        .orElse("N/A");

                String giaVeFormatted = String.format("%,.0f VNĐ", sc.getGiaVe());

                model.addRow(new Object[]{
                    sc.getMaXuat(),
                    ten,
                    sc.getMaPhong(),
                    sc.getNgayChieu().format(dateFormatter),
                    sc.getGioChieu().format(timeFormatter),
                    giaVeFormatted
                });
            }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSuatChieu = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaXuat = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMaPhim = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtGiaVe = new javax.swing.JTextField();
        cboPhim = new javax.swing.JComboBox<>();
        cboPhong = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        chooserNgayChieu = new com.toedter.calendar.JDateChooser();
        spnGioChieu = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cboPhim1 = new javax.swing.JComboBox<>();
        chooserNgayChieu1 = new com.toedter.calendar.JDateChooser();
        cboPhong1 = new javax.swing.JComboBox<>();
        btnResetBoLoc = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1110, 720));

        tblSuatChieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã suất", "Tên phim", "Mã phòng", "Ngày chiếu", "Giờ chiếu", "Giá vé"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSuatChieu.setGridColor(new java.awt.Color(0, 0, 0));
        tblSuatChieu.setRowHeight(25);
        tblSuatChieu.setShowGrid(true);
        tblSuatChieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSuatChieuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSuatChieu);

        jPanel2.setBackground(new java.awt.Color(212, 212, 212));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Mã suất");

        txtMaXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaXuatActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Mã phim");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Ngày chiếu");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Mã phòng");

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Giá vé");

        cboPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPhimActionPerformed(evt);
            }
        });

        cboPhong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Giờ chiếu");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Tên phim:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtMaXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(86, 86, 86)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cboPhong, 0, 257, Short.MAX_VALUE)
                                .addGap(81, 81, 81)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtGiaVe, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cboPhim, javax.swing.GroupLayout.Alignment.LEADING, 0, 257, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaPhim, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(86, 86, 86)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnThem)
                                .addGap(42, 42, 42)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLamMoi)
                                .addGap(53, 53, 53))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(chooserNgayChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(spnGioChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(84, 84, 84))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaXuat)
                        .addComponent(cboPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtGiaVe, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spnGioChieu))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtMaPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(chooserNgayChieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboPhim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel1.setText("QUẢN LÝ SUẤT CHIẾU");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cboPhim1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPhim1ActionPerformed(evt);
            }
        });
        jPanel3.add(cboPhim1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 171, -1));
        jPanel3.add(chooserNgayChieu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 218, -1));

        cboPhong1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cboPhong1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, 172, -1));

        btnResetBoLoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnResetBoLoc.setText("Làm mới bộ lọc");
        btnResetBoLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetBoLocActionPerformed(evt);
            }
        });
        jPanel3.add(btnResetBoLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 30, 120, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Lọc theo phòng chiếu");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 170, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Lọc theo tên phim");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 170, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Lọc theo ngày chiếu");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 170, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(436, 436, 436)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1122, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clear();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtMaXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaXuatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaXuatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        create();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblSuatChieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSuatChieuMouseClicked
        edit();
    }//GEN-LAST:event_tblSuatChieuMouseClicked

    private void cboPhim1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPhim1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboPhim1ActionPerformed

    private void btnResetBoLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetBoLocActionPerformed
        cboPhim1.setSelectedItem("-- Tất cả --");
        cboPhong1.setSelectedItem("-- Tất cả --");
        chooserNgayChieu1.setDate(new Date());
        open();
    }//GEN-LAST:event_btnResetBoLocActionPerformed

    private void cboPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPhimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboPhimActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnResetBoLoc;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboPhim;
    private javax.swing.JComboBox<String> cboPhim1;
    private javax.swing.JComboBox<String> cboPhong;
    private javax.swing.JComboBox<String> cboPhong1;
    private com.toedter.calendar.JDateChooser chooserNgayChieu;
    private com.toedter.calendar.JDateChooser chooserNgayChieu1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnGioChieu;
    private javax.swing.JTable tblSuatChieu;
    private javax.swing.JTextField txtGiaVe;
    private javax.swing.JTextField txtMaPhim;
    private javax.swing.JTextField txtMaXuat;
    // End of variables declaration//GEN-END:variables

}
