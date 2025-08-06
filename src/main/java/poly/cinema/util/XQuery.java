package poly.cinema.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp tiện ích hỗ trợ truy vấn và chuyển đổi kết quả ResultSet thành đối tượng
 */
public class XQuery {
    public static <B> List<B> getBeanList(Class<B> beanClass, String sql, Object... values) {
        List<B> list = new ArrayList<>();
        try {
            ResultSet resultSet = XJdbc.executeQuery(sql, values);
            while (resultSet.next()) {
                list.add(XQuery.readBean(resultSet, beanClass));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    /**
     * Truy vấn và lấy ra 1 đối tượng duy nhất
     */
    public static <B> B getSingleBean(Class<B> beanClass, String sql, Object... values) {
        List<B> list = getEntityList(beanClass, sql, values);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Truy vấn và lấy ra danh sách đối tượng từ kết quả truy vấn SQL
     */
    public static <B> List<B> getEntityList(Class<B> beanClass, String sql, Object... values) {
        List<B> list = new ArrayList<>();
        try {
            ResultSet resultSet = XJdbc.executeQuery(sql, values);
            while (resultSet.next()) {
                B bean = readBean(resultSet, beanClass);
                list.add(bean);
            }
            resultSet.getStatement().getConnection().close();
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi khi truy vấn danh sách " + beanClass.getSimpleName(), ex);
        }
        return list;
    }

    /**
     * Ánh xạ 1 dòng trong ResultSet thành đối tượng Java
     */
    private static <B> B readBean(ResultSet resultSet, Class<B> beanClass) throws Exception {
    B bean = beanClass.getDeclaredConstructor().newInstance();
    Method[] methods = beanClass.getDeclaredMethods();
    for (Method method : methods) {
        String name = method.getName();
        if (name.startsWith("set") && method.getParameterCount() == 1) {
            String columnName = name.substring(3);
            if (hasColumn(resultSet, columnName)) { // ✅ kiểm tra tồn tại cột
                try {
                    Object value = resultSet.getObject(columnName);
                    method.invoke(bean, value);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    // bỏ qua lỗi
                }
            }
        }
    }
    return bean;
}

private static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
    var meta = rs.getMetaData();
    for (int i = 1; i <= meta.getColumnCount(); i++) {
        if (columnName.equalsIgnoreCase(meta.getColumnLabel(i))) {
            return true;
        }
    }
    return false;
}

  
    
    public static void main(String[] args) {
        demo1();
        demo2();
    }

    private static void demo1() {
        String sql = "SELECT * FROM Users WHERE Username=? AND Password=?";
       // User user = XQuery.getSingleBean(User.class, sql, "NghiemN", "123456");
    }

    private static void demo2() {
        String sql = "SELECT * FROM Users WHERE Fullname LIKE ?";
        //List<User> list = XQuery.getBeanList(User.class, sql, "%Nguyễn %");
    }

    
}
