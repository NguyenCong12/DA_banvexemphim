package poly.cinema.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
//import poly.cinema.entity.Bill;
//import poly.cinema.entity.BillDetail;
//import poly.cinema.entity.Customer;


//public class PDFExporter {
//
//   public void exportBillToPDF(Bill bill, List<BillDetail> details, Customer customer, String filePath) {
//Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//    try {
//        File file = new File(filePath);
//        file.getParentFile().mkdirs(); // 👉 Fix lỗi đường dẫn không tồn tại
//        PdfWriter.getInstance(document, new FileOutputStream(filePath));
//        document.open();
//
//        // Fonts
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
//        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
//        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
//        Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLUE);
//
//        // Logo
//        try {
//            Image logo = Image.getInstance("src/main/resources/icon/logo.png");
//            logo.scaleAbsolute(70f, 70f);
//            logo.setAlignment(Image.ALIGN_LEFT);
//            document.add(logo);
//        } catch (Exception e) {
//            System.err.println("Không thể chèn logo: " + e.getMessage());
//        }
//
//        // Tiêu đề
//        Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//        document.add(Chunk.NEWLINE);
//
//        // Thông tin khách hàng
//        document.add(new Paragraph("Mã hóa đơn: " + bill.getBillId(), bodyFont));
//        document.add(new Paragraph("Khách hàng: " + (customer != null ? customer.getCustomerName() : "Khách lẻ"), bodyFont));
//        document.add(new Paragraph("Ngày thanh toán: " + bill.getCheckout().toString(), bodyFont));
//        document.add(Chunk.NEWLINE);
//
//        // Bảng chi tiết
//        PdfPTable table = new PdfPTable(5);
//        table.setWidthPercentage(100);
//        table.setWidths(new float[]{3, 1, 2, 1, 2});
//
//        table.addCell(new PdfPCell(new Phrase("Sản phẩm", headerFont)));
//        table.addCell(new PdfPCell(new Phrase("Số lượng", headerFont)));
//        table.addCell(new PdfPCell(new Phrase("Đơn giá", headerFont)));
//        table.addCell(new PdfPCell(new Phrase("Giảm giá", headerFont)));
//        table.addCell(new PdfPCell(new Phrase("Thành tiền", headerFont)));
//
//        double total = 0;
//        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
//
//        for (BillDetail d : details) {
//            double thanhTien = d.getQuantity() * d.getUnitPrice() * (1 - d.getDiscount());
//            total += thanhTien;
//
//            table.addCell(new Phrase(d.getProductName(), bodyFont));
//            table.addCell(new Phrase(String.valueOf(d.getQuantity()), bodyFont));
//            table.addCell(new Phrase(formatter.format(d.getUnitPrice()), bodyFont));
//            table.addCell(new Phrase(String.format("%.0f%%", d.getDiscount() * 100), bodyFont));
//            table.addCell(new Phrase(formatter.format(thanhTien), bodyFont));
//        }
//
//        document.add(table);
//        document.add(Chunk.NEWLINE);
//
//        // Tổng cộng
//        Paragraph totalPara = new Paragraph("Tổng cộng: " + formatter.format(total) + " VNĐ", totalFont);
//        totalPara.setAlignment(Element.ALIGN_RIGHT);
//        document.add(totalPara);
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    } finally {
//        document.close();
//    }
//
//   }
//}
