package poly.cinema.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javax.swing.JTable;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFExporter {

    public void exportBillToPDF(String filePath,
                                 String maPhieu,
                                 String nhanVien,
                                 String trangThai,
                                 String checkout,
                                 JTable table,
                                 double tongTien,
                                 double tienKhachDua,
                                 double tienThoiLai) {
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Font tiếng Việt
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font shopNameFont = new Font(baseFont, 20, Font.BOLD, BaseColor.RED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD, BaseColor.BLUE);
            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            Font normalFont = new Font(baseFont, 12);
            Font italicFont = new Font(baseFont, 11, Font.ITALIC, BaseColor.DARK_GRAY);

            // Logo
            try {
                Image logo = Image.getInstance("src\\main\\resources\\images\\LOGOHOADON.png");
                logo.scaleToFit(80, 80);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("Không tìm thấy logo. Bỏ qua.");
            }

            // Tên shop & địa chỉ
            Paragraph shopName = new Paragraph("FCINEMA", shopNameFont);
            shopName.setAlignment(Element.ALIGN_CENTER);
            shopName.setSpacingAfter(5f);
            document.add(shopName);

            Paragraph address = new Paragraph("Địa chỉ: Toà nhà FPT Polytechnic, Đ. Số 22, Thường Thạnh, Cái Răng, Cần Thơ", normalFont);
            address.setAlignment(Element.ALIGN_CENTER);
            address.setSpacingAfter(10f);
            document.add(address);

            document.add(new LineSeparator());

            // Tiêu đề hóa đơn
            Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(10f);
            title.setSpacingAfter(15f);
            document.add(title);

            // Thông tin hóa đơn
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(80);
            infoTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            infoTable.setWidths(new float[]{1.5f, 4});
            infoTable.setSpacingAfter(10f);

            addInfoCell(infoTable, "Mã phiếu:", maPhieu, headerFont, normalFont);
            addInfoCell(infoTable, "Nhân viên:", nhanVien, headerFont, normalFont);
            addInfoCell(infoTable, "Trạng thái:", trangThai, headerFont, normalFont);
            addInfoCell(infoTable, "Thời điểm thanh toán:", checkout, headerFont, normalFont);

            document.add(infoTable);

            document.add(new LineSeparator());

            // Bảng chi tiết
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount() - 1);
            pdfTable.setWidthPercentage(100);
            pdfTable.setSpacingBefore(10f);

            for (int col = 1; col < table.getColumnCount(); col++) {
                PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(col), headerFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell);
            }

            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 1; col < table.getColumnCount(); col++) {
                    Object value = table.getValueAt(row, col);
                    PdfPCell dataCell = new PdfPCell(new Phrase(value != null ? value.toString() : "", normalFont));
                    dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(dataCell);
                }
            }

            document.add(pdfTable);

            // Bảng tổng kết
            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(40);
            summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryTable.setSpacingBefore(20f);

            addInfoCell(summaryTable, "Tổng tiền:", String.format("%.0f VND", tongTien), headerFont, normalFont);
            addInfoCell(summaryTable, "Tiền khách đưa:", String.format("%.0f VND", tienKhachDua), headerFont, normalFont);
            addInfoCell(summaryTable, "Tiền thối lại:", String.format("%.0f VND", tienThoiLai), headerFont, normalFont);

            document.add(summaryTable);

            // Cảm ơn
            Paragraph thanks = new Paragraph("\nCảm ơn quý khách đã sử dụng dịch vụ của chúng tôi!\nHẹn gặp lại quý khách lần sau.", italicFont);
            thanks.setAlignment(Element.ALIGN_CENTER);
            thanks.setSpacingBefore(30f);
            document.add(thanks);

            // Ngày in
            String printDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
            Paragraph printTime = new Paragraph("Ngày in: " + printDate, italicFont);
            printTime.setAlignment(Element.ALIGN_RIGHT);
            printTime.setSpacingBefore(20f);
            document.add(printTime);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addInfoCell(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, labelFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, valueFont));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell1);
        table.addCell(cell2);
    }
}
