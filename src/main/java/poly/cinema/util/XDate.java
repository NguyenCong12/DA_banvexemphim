package poly.cinema.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XDate {

    public static final String PATTERN_FULL = "HH:mm:ss dd/MM/yyyy";
    public static final String PATTERN_SHORT = "dd/MM/yyyy";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private static final SimpleDateFormat formater = new SimpleDateFormat();

    public static Date now() {
        return new Date();
    }

    public static Date toDate(Date date) {
        try {
            String formatted = sdf.format(date); // Định dạng lại chuỗi: "dd/MM/yyyy"
            return sdf.parse(formatted);         // Parse lại thành Date, tự động mất giờ/phút/giây
        } catch (ParseException e) {
            return date; // nếu lỗi, trả về nguyên bản
        }
    }

    public static Date parse(String dateTime, String pattern) {
        formater.applyLocalizedPattern(pattern);
        try {
            return formater.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parse(String dateTime) {
        return parse(dateTime, PATTERN_SHORT);
    }

    public static String format(Date dateTime, String pattern) {
        if (dateTime == null) {
            return "";
        }
        formater.applyPattern(pattern);
        return formater.format(dateTime);
    }

    public static String format(Date dateTime) {
        return format(dateTime, PATTERN_SHORT);
    }

    public static void main(String[] args) {
        Date date = XDate.parse("Jan 21, 2024", "MMM dd, yyyy");
        String text = XDate.format(date, "dd-MMM-yyyy");
        System.out.println(text); // => 21-Jan-2024
    }
}
