//SQL File
package com.db.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.db.models.Bills;
import com.db.models.Employee;
import com.db.models.OrderDetailsView;
import com.db.models.Orders;
import com.db.models.Product;
import com.db.models.ProductDiscountView;
import com.db.models.Store;
import com.db.models.StoreStats;
import com.db.models.Supplier;
import com.db.models.Vehicle;
import com.db.models.Warehouse;

public class DbHelper {

    // Create (Insert)
    public static boolean addSupplier(Supplier supplier) {
        String query = "INSERT INTO suppliers (supplier_id, name, location, phone_number) VALUES (?, ?, ?, ?)";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, supplier.getSupplierId());
            stmt.setString(2, supplier.getName());
            stmt.setString(3, supplier.getLocation());
            stmt.setString(4, supplier.getPhoneNumber());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read (Select by ID)
    public static Supplier getSupplierById(int id) {
        String query = "SELECT * FROM suppliers WHERE supplier_id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("supplier_id"));
                supplier.setName(rs.getString("name"));
                supplier.setLocation(rs.getString("location"));
                supplier.setPhoneNumber(rs.getString("phone_number"));
                return supplier;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    public static boolean updateSupplier(Supplier supplier) {
        String query = "UPDATE suppliers SET name = ?, location = ?, phone_number = ? WHERE supplier_id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getLocation());
            stmt.setString(3, supplier.getPhoneNumber());
            stmt.setInt(4, supplier.getSupplierId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    public static boolean deleteSupplier(int id) {
        String query = "DELETE FROM suppliers WHERE supplier_id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // List All
    public static List<Supplier> getAllSuppliers() throws Exception {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM suppliers";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("supplier_id"));
                supplier.setName(rs.getString("name"));
                supplier.setLocation(rs.getString("location"));
                supplier.setPhoneNumber(rs.getString("phone_number"));
                suppliers.add(supplier);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public static int getNextSupplierId() {
        String query = "SELECT MAX(supplier_id) AS max_id FROM suppliers";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //Q1
    public static Map<String, Integer> getSupplierCountByLocation() {
        Map<String, Integer> data = new HashMap<>();
        String query = "SELECT location, COUNT(*) AS total_suppliers FROM suppliers GROUP BY location ORDER BY total_suppliers DESC";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String location = rs.getString("location");
                int count = rs.getInt("total_suppliers");
                data.put(location, count);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;

    }
    // ---------- Product CRUD Operations ----------

    // Create (Insert)
    public static boolean addProduct(Product product) {
        String query = "INSERT INTO products (pN, name, selling_price, buying_price, quantity, expiring_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getName());
            stmt.setBigDecimal(3, product.getSellingPrice());
            stmt.setBigDecimal(4, product.getBuyingPrice());
            stmt.setInt(5, product.getQuantity());
            stmt.setDate(6, Date.valueOf(product.getExpiringDate()));

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read (Select by ID)
    public static Product getProductById(int id) {
        String query = "SELECT * FROM products WHERE pN = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("pN"));
                product.setName(rs.getString("name"));
                product.setSellingPrice(rs.getBigDecimal("selling_price"));
                product.setBuyingPrice(rs.getBigDecimal("buying_price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setExpiringDate(rs.getDate("expiring_date").toLocalDate());
                return product;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    public static boolean updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, selling_price = ?, buying_price = ?, quantity = ?, expiring_date = ? WHERE pN = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getSellingPrice());
            stmt.setBigDecimal(3, product.getBuyingPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.setDate(5, Date.valueOf(product.getExpiringDate()));
            stmt.setInt(6, product.getProductId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    public static boolean deleteProduct(int productId) {
        String query = "DELETE FROM products WHERE pN = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // List All
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("pN"));
                product.setName(rs.getString("name"));
                product.setSellingPrice(rs.getBigDecimal("selling_price"));
                product.setBuyingPrice(rs.getBigDecimal("buying_price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setExpiringDate(rs.getDate("expiring_date").toLocalDate());
                products.add(product);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static List<Product> filterProducts(String name, BigDecimal minSellingPrice, BigDecimal maxSellingPrice,
            Date fromExpiringDate, Date toExpiringDate) {
        List<Product> products = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM products WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        // Name filter
        if (name != null && !name.trim().isEmpty()) {
            queryBuilder.append(" AND name LIKE ?");
            parameters.add("%" + name.trim() + "%");
        }

        // Selling price range filter
        if (minSellingPrice != null) {
            queryBuilder.append(" AND selling_price >= ?");
            parameters.add(minSellingPrice);
        }
        if (maxSellingPrice != null) {
            queryBuilder.append(" AND selling_price <= ?");
            parameters.add(maxSellingPrice);
        }

        // Expiring date range filter
        if (fromExpiringDate != null) {
            queryBuilder.append(" AND expiring_date >= ?");
            parameters.add(fromExpiringDate);
        }
        if (toExpiringDate != null) {
            queryBuilder.append(" AND expiring_date <= ?");
            parameters.add(toExpiringDate);
        }

        queryBuilder.append(" ORDER BY name");

        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {

            // Set parameters
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("pN"));
                product.setName(rs.getString("name"));
                product.setSellingPrice(rs.getBigDecimal("selling_price"));
                product.setBuyingPrice(rs.getBigDecimal("buying_price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setExpiringDate(rs.getDate("expiring_date").toLocalDate());
                products.add(product);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Optional: Get next product ID
    public static int getNextProductId() {
        String query = "SELECT MAX(pN) AS max_id FROM products";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

// =========================
// EMPLOYEE CRUD OPERATIONS
// =========================
    public static boolean addEmployee(Employee employee) {
        String query = "INSERT INTO employees (employee_id, ename, salary, phone_number, birth_date, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, employee.getEmployeeId());
            stmt.setString(2, employee.getEname());
            stmt.setBigDecimal(3, employee.getSalary());
            stmt.setString(4, employee.getPhoneNumber());
            stmt.setDate(5, Date.valueOf(employee.getBirthDate()));
            stmt.setString(6, employee.getRole());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Employee getEmployeeById(int id) {
        String query = "SELECT * FROM employees WHERE employee_id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setEname(rs.getString("ename"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setPhoneNumber(rs.getString("phone_number"));
                employee.setBirthDate(rs.getDate("birth_date").toLocalDate());
                employee.setRole(rs.getString("role"));
                return employee;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateEmployee(Employee employee) {
        String query = "UPDATE employees SET ename = ?, salary = ?, phone_number = ?, birth_date = ?, role = ? WHERE employee_id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employee.getEname());
            stmt.setBigDecimal(2, employee.getSalary());
            stmt.setString(3, employee.getPhoneNumber());
            stmt.setDate(4, Date.valueOf(employee.getBirthDate()));
            stmt.setString(5, employee.getRole());
            stmt.setInt(6, employee.getEmployeeId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Employee> getAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setEname(rs.getString("ename"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setPhoneNumber(rs.getString("phone_number"));
                employee.setBirthDate(rs.getDate("birth_date").toLocalDate());
                employee.setRole(rs.getString("role"));
                employees.add(employee);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static List<Employee> searchEmployeesByName(String searchName) throws Exception {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees WHERE ename LIKE ? ORDER BY ename";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + searchName + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setEname(rs.getString("ename"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setPhoneNumber(rs.getString("phone_number"));
                employee.setBirthDate(rs.getDate("birth_date").toLocalDate());
                employee.setRole(rs.getString("role"));
                employees.add(employee);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static int getNextEmployeeId() {
        String query = "SELECT MAX(employee_id) AS max_id FROM employees";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    // ========== ORDERS TABLE ==========

    // Create (Insert)
    public static boolean addOrder(Orders order) {
        String query = "INSERT INTO orders (order_id, store_name, employee_id, order_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, order.getOrderId());
            stmt.setString(2, order.getStoreName());
            stmt.setInt(3, order.getEmployeeId());
            stmt.setDate(4, Date.valueOf(order.getOrderDate())); // Convert LocalDate to java.sql.Date

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read (Select by ID)
    public static Orders getOrderById(int id) {
        String query = "SELECT o.order_ID, o.store_name, o.employee_ID, o.order_date, e.ename "
                + "FROM orders o LEFT JOIN employees e ON o.employee_ID = e.employee_ID "
                + "WHERE o.order_ID = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("order_ID"));
                order.setStoreName(rs.getString("store_name"));
                order.setEmployeeId(rs.getInt("employee_ID"));
                order.setEmployeeName(rs.getString("ename"));
                order.setOrderDate(rs.getDate("order_date").toLocalDate());
                return order;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    public static boolean updateOrder(Orders order) {
        String query = "UPDATE orders SET store_name = ?, employee_id = ?, order_date = ? WHERE order_id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, order.getStoreName());
            stmt.setInt(2, order.getEmployeeId());
            stmt.setDate(3, Date.valueOf(order.getOrderDate()));
            stmt.setInt(4, order.getOrderId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    public static boolean deleteOrder(int id) {
        String query = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // List All
    public static List<Orders> getAllOrders() throws Exception {
        List<Orders> orders = new ArrayList<>();
        String query = "SELECT o.order_id, o.store_name, o.employee_id, o.order_date, e.ename "
                + "FROM orders o LEFT JOIN employees e ON o.employee_id = e.employee_id";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("order_id"));
                order.setStoreName(rs.getString("store_name"));
                order.setEmployeeId(rs.getInt("employee_id"));
                order.setEmployeeName(rs.getString("ename"));
                order.setOrderDate(rs.getDate("order_date").toLocalDate());
                orders.add(order);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static List<Orders> filterAndOrderOrders(String nameSearch, String orderByColumn, String orderDirection) throws Exception {
        List<Orders> orders = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT o.order_id, o.store_name, o.employee_id, o.order_date, e.ename "
                + "FROM orders o LEFT JOIN employees e ON o.employee_id = e.employee_id WHERE 1=1"
        );
        List<Object> parameters = new ArrayList<>();

        // Name search filter (searches both store name and employee name)
        if (nameSearch != null && !nameSearch.trim().isEmpty()) {
            queryBuilder.append(" AND (o.store_name LIKE ? OR e.ename LIKE ?)");
            String searchPattern = "%" + nameSearch.trim() + "%";
            parameters.add(searchPattern);
            parameters.add(searchPattern);
        }

        // Add ORDER BY clause
        queryBuilder.append(" ORDER BY ");
        queryBuilder.append(orderByColumn);
        queryBuilder.append(" ");
        queryBuilder.append(orderDirection);

        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {

            // Set parameters
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("order_id"));
                order.setStoreName(rs.getString("store_name"));
                order.setEmployeeId(rs.getInt("employee_id"));
                order.setEmployeeName(rs.getString("ename"));
                order.setOrderDate(rs.getDate("order_date").toLocalDate());
                orders.add(order);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Calculate total amount for an order using database query
    public static BigDecimal calculateOrderTotal(int orderId) {
        String query = "SELECT SUM(od.quantity * p.selling_price) AS total "
                + "FROM order_details od "
                + "JOIN products p ON od.PN = p.pN "
                + "WHERE od.order_ID = ?";

        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    // Get bill statistics using database queries
    public static class BillStatistics {

        private BigDecimal maxBillValue;
        private BigDecimal minBillValue;
        private int billCount;

        public BillStatistics(BigDecimal maxBillValue, BigDecimal minBillValue, int billCount) {
            this.maxBillValue = maxBillValue;
            this.minBillValue = minBillValue;
            this.billCount = billCount;
        }

        public BigDecimal getMaxBillValue() {
            return maxBillValue;
        }

        public BigDecimal getMinBillValue() {
            return minBillValue;
        }

        public int getBillCount() {
            return billCount;
        }
    }

    public static BillStatistics getBillStatistics() {
        String query = "SELECT MAX(total_price) AS max_value, MIN(total_price) AS min_value, COUNT(*) AS bill_count FROM bills";

        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                BigDecimal maxValue = rs.getBigDecimal("max_value");
                BigDecimal minValue = rs.getBigDecimal("min_value");
                int count = rs.getInt("bill_count");

                // Handle null values if no bills exist
                maxValue = maxValue != null ? maxValue : BigDecimal.ZERO;
                minValue = minValue != null ? minValue : BigDecimal.ZERO;

                return new BillStatistics(maxValue, minValue, count);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new BillStatistics(BigDecimal.ZERO, BigDecimal.ZERO, 0);
    }

    // Get Next ID
    public static int getNextOrderId() {
        String query = "SELECT MAX(order_id) AS max_id FROM orders";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    // =========================
    // Bills CRUD OPERATIONS
    // =========================
    // Create (Insert)
    public static boolean addBill(Bills bill) {
        String query = "INSERT INTO bills (bill_ID, date_of_transaction, type, total_price, order_ID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bill.getBillId());
            stmt.setDate(2, Date.valueOf(bill.getDateOfTransaction()));
            stmt.setString(3, bill.getType());
            stmt.setBigDecimal(4, bill.getTotalPrice());
            stmt.setInt(5, bill.getOrderId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read (Select by ID)
    public static Bills getBillById(int id) {
        String query = "SELECT * FROM bills WHERE bill_ID = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bills bill = new Bills();
                bill.setBillId(rs.getInt("bill_ID"));
                bill.setDateOfTransaction(rs.getDate("date_of_transaction").toLocalDate());
                bill.setType(rs.getString("type"));
                bill.setTotalPrice(rs.getBigDecimal("total_price"));
                bill.setOrderId(rs.getInt("order_ID"));
                return bill;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    public static boolean updateBill(Bills bill) {
        String query = "UPDATE bills SET date_of_transaction = ?, type = ?, total_price = ?, order_ID = ? WHERE bill_ID = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(bill.getDateOfTransaction()));
            stmt.setString(2, bill.getType());
            stmt.setBigDecimal(3, bill.getTotalPrice());
            stmt.setInt(4, bill.getOrderId());
            stmt.setInt(5, bill.getBillId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    public static boolean deleteBill(int id) {
        String query = "DELETE FROM bills WHERE bill_ID = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // List All
    public static List<Bills> getAllBills() throws Exception {
        List<Bills> billsList = new ArrayList<>();
        String query = "SELECT * FROM bills";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Bills bill = new Bills();
                bill.setBillId(rs.getInt("bill_ID"));
                bill.setDateOfTransaction(rs.getDate("date_of_transaction").toLocalDate());
                bill.setType(rs.getString("type"));
                bill.setTotalPrice(rs.getBigDecimal("total_price"));
                bill.setOrderId(rs.getInt("order_ID"));
                billsList.add(bill);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return billsList;
    }

    // Get next ID
    public static int getNextBillId() {
        String query = "SELECT MAX(bill_ID) AS max_id FROM bills";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //-------orderDetailsView-----------
    public static List<OrderDetailsView> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetailsView> list = new ArrayList<>();

        String query = """
        SELECT od.pn, p.name AS product_name, p.selling_price, od.quantity
        FROM order_details od
        JOIN products p ON od.pn = p.PN
        WHERE od.order_id = ?
    """;

        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int pn = rs.getInt("pn");
                String name = rs.getString("product_name");
                BigDecimal price = rs.getBigDecimal("selling_price");
                int qty = rs.getInt("quantity");

                list.add(new OrderDetailsView(pn, name, price, qty));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<ProductDiscountView> getExpiringProductsDiscounted() {
        List<ProductDiscountView> list = new ArrayList<>();

        String query = """
        SELECT 
            pN,
            name,
            SUM(quantity) AS total_quantity,
            MAX(selling_price) AS original_price,
            MAX(expiring_date) AS expiring_date,
            CASE
                WHEN SUM(quantity) > 100 THEN ROUND(MAX(selling_price) * 0.60, 2)
                WHEN SUM(quantity) > 50 THEN ROUND(MAX(selling_price) * 0.75, 2)
                ELSE NULL
            END AS discounted_price
        FROM products
        WHERE expiring_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
        GROUP BY pN, name
        HAVING total_quantity > 50;
    """;

        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int pN = rs.getInt("pN");
                String name = rs.getString("name");
                int totalQty = rs.getInt("total_quantity");
                BigDecimal original = rs.getBigDecimal("original_price");
                BigDecimal discounted = rs.getBigDecimal("discounted_price");
                java.time.LocalDate expiringDate = rs.getDate("expiring_date").toLocalDate();

                list.add(new ProductDiscountView(pN, name, totalQty, original, discounted, expiringDate));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get top 5 employees with most orders per month for chart data (last 6 months)
    public static List<java.util.Map<String, Object>> getEmployeeOrdersPerMonth() {
        List<java.util.Map<String, Object>> chartData = new ArrayList<>();

        String query = """
        WITH top_employees AS (
            SELECT e.employee_id, e.ename
            FROM employees e
            JOIN orders o ON e.employee_id = o.employee_id
            WHERE o.order_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)
            GROUP BY e.employee_id, e.ename
            ORDER BY COUNT(o.order_id) DESC
            LIMIT 5
        )
        SELECT 
            te.ename as employee_name,
            DATE_FORMAT(o.order_date, '%Y-%m') as month_year,
            COUNT(o.order_id) as order_count
        FROM top_employees te
        LEFT JOIN orders o ON te.employee_id = o.employee_id 
            AND o.order_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)
        GROUP BY te.employee_id, te.ename, DATE_FORMAT(o.order_date, '%Y-%m')
        ORDER BY te.ename, month_year
        """;

        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                java.util.Map<String, Object> dataPoint = new java.util.HashMap<>();
                dataPoint.put("employeeName", rs.getString("employee_name"));
                dataPoint.put("monthYear", rs.getString("month_year"));
                dataPoint.put("orderCount", rs.getInt("order_count"));
                chartData.add(dataPoint);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return chartData;
    }

    public static List<String> getAllStoreNames() throws Exception {
        List<String> stores = new ArrayList<>();
        String query = "SELECT DISTINCT store_name FROM stores ORDER BY store_name";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                stores.add(rs.getString("store_name"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stores;
    }

    public static boolean addOrderDetail(int orderId, int productId, int quantity) {
        String query = "INSERT INTO order_details (order_id, pn, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrderDetail(int orderId, int productId) {
        String query = "DELETE FROM order_details WHERE order_id = ? AND pn = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateOrderDetail(int orderId, int productId, int newQuantity) {
        String query = "UPDATE order_details SET quantity = ? WHERE order_id = ? AND pn = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, orderId);
            stmt.setInt(3, productId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
// STORE CRUD OPERATIONS
// =========================
    // Create (Insert)
    public static boolean addStore(Store store) {
        String query = "INSERT INTO stores (store_name, phone_number, location) VALUES (?, ?, ?)";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, store.getStoreName());
            stmt.setString(2, store.getPhoneNumber());
            stmt.setString(3, store.getLocation());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    // Read (Select by store_name)
    public static Store getStoreByName(String name) {
        String query = "SELECT * FROM stores WHERE store_name = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Store store = new Store();
                store.setStoreName(rs.getString("store_name"));
                store.setPhoneNumber(rs.getString("phone_number"));
                store.setLocation(rs.getString("location"));
                return store;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    // Update
    public static boolean updateStore(Store store) {
        String query = "UPDATE stores SET phone_number = ?, location = ? WHERE store_name = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, store.getPhoneNumber());
            stmt.setString(2, store.getLocation());
            stmt.setString(3, store.getStoreName());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    // Delete
    public static boolean deleteStore(String storeName) {
        String query = "DELETE FROM stores WHERE store_name = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, storeName);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    // List All
    public static List<Store> getAllStores() throws Exception {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT * FROM stores";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Store store = new Store();
                store.setStoreName(rs.getString("store_name"));
                store.setPhoneNumber(rs.getString("phone_number"));
                store.setLocation(rs.getString("location"));
                stores.add(store);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stores;

    }

    public static List<StoreStats> getStoreCountPerLocation() throws Exception {
        List<StoreStats> stats = new ArrayList<>();
        String query = "SELECT location, COUNT(*) AS total_stores FROM stores GROUP BY location ORDER BY total_stores DESC";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String location = rs.getString("location");
                int count = rs.getInt("total_stores");
                stats.add(new StoreStats(location, count));
            }
        }
        return stats;
    }

    public static List<Store> searchStoresByPhone(String keyword) throws Exception {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT * FROM stores WHERE phone_number LIKE ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Store store = new Store();
                store.setStoreName(rs.getString("store_name"));
                store.setPhoneNumber(rs.getString("phone_number"));
                store.setLocation(rs.getString("location"));
                stores.add(store);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return stores;
    }

    // =========================
// VEHICLE CRUD OPERATIONS
// =========================
// Create (Insert)
    public static boolean addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicles (permit_number, capacity, vehicle_number, phone_number, employee_ID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vehicle.getPermitNumber());
            stmt.setInt(2, vehicle.getCapacity());
            stmt.setString(3, vehicle.getVehicleNumber());
            stmt.setString(4, vehicle.getPhoneNumber());
            stmt.setInt(5, vehicle.getEmployeeId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

// Update
    public static boolean updateVehicle(Vehicle vehicle) {
        String query = "UPDATE vehicles SET capacity = ?, vehicle_number = ?, phone_number = ?, employee_ID = ? WHERE permit_number = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vehicle.getCapacity());
            stmt.setString(2, vehicle.getVehicleNumber());
            stmt.setString(3, vehicle.getPhoneNumber());
            stmt.setInt(4, vehicle.getEmployeeId());
            stmt.setString(5, vehicle.getPermitNumber());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

// Delete
    public static boolean deleteVehicle(String permitNumber) {
        String query = "DELETE FROM vehicles WHERE permit_number = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, permitNumber);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

// List All
    public static List<Vehicle> getAllVehicles() throws Exception {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setPermitNumber(rs.getString("permit_number"));
                vehicle.setCapacity(rs.getInt("capacity"));
                vehicle.setVehicleNumber(rs.getString("vehicle_number"));
                vehicle.setPhoneNumber(rs.getString("phone_number"));
                vehicle.setEmployeeId(rs.getInt("employee_ID"));
                vehicles.add(vehicle);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

// Quire1 SELECT * FROM vehicles WHERE capacity > 500;
    public static List<Vehicle> getVehiclesWithHighCapacity(int minCapacity) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE capacity > ?";

        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, minCapacity);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setPermitNumber(rs.getString("permit_number"));
                v.setCapacity(rs.getInt("capacity"));
                v.setVehicleNumber(rs.getString("vehicle_number"));
                v.setPhoneNumber(rs.getString("phone_number"));
                v.setEmployeeId(rs.getInt("employee_ID"));
                vehicles.add(v);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return vehicles;

    }
//q2

    public static Vehicle getVehicleByPermit(String permitNumber) {
        String query = "SELECT * FROM vehicles WHERE permit_number = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, permitNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Vehicle(
                        rs.getString("permit_number"),
                        rs.getInt("capacity"),
                        rs.getString("vehicle_number"),
                        rs.getString("phone_number"),
                        rs.getInt("employee_ID")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
//q3

    public static int countVehicles() {
        String query = "SELECT COUNT(*) AS total FROM vehicles";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;

    }
//to check if the employee is existing to add the vehicle

    public static boolean employeeExists(int employeeId) { //q3
        String query = "SELECT 1 FROM employees WHERE employee_id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
// ==========================
// WAREHOUSE CRUD OPERATIONS
// ==========================

// Create (Insert)
    public static boolean addWarehouse(Warehouse warehouse) {
        String query = "INSERT INTO warehouse (id, location, capacity) VALUES (?, ?, ?)";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, warehouse.getWarehouseId());
            stmt.setString(2, warehouse.getLocation());
            stmt.setInt(3, warehouse.getCapacity());
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

// Read (Select by ID)
    public static Warehouse getWarehouseById(int id) {
        String query = "SELECT * FROM warehouse WHERE id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("id"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setCapacity(rs.getInt("capacity"));
                return warehouse;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

// Update
    public static boolean updateWarehouse(Warehouse warehouse) {
        String query = "UPDATE warehouse SET location = ?, capacity = ? WHERE id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, warehouse.getLocation());
            stmt.setInt(2, warehouse.getCapacity());
            stmt.setInt(3, warehouse.getWarehouseId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

// Delete
    public static boolean deleteWarehouse(int id) {
        String query = "DELETE FROM warehouse WHERE id = ?";
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

// List All
    public static List<Warehouse> getAllWarehouses() throws Exception {
        List<Warehouse> warehouses = new ArrayList<>();
        String query = "SELECT * FROM warehouse";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("id"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setCapacity(rs.getInt("capacity"));
                warehouses.add(warehouse);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return warehouses;

    }
// Get Next ID

    public static int getNextWarehouseId() {
        String query = "SELECT MAX(id) AS max_id FROM warehouse";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 1;

    }
//Q1

    public static Map<String, Integer> getWarehouseCountByLocation() {
        Map<String, Integer> map = new HashMap<>();
        String query = "SELECT location, COUNT(*) AS total FROM warehouse GROUP BY location";

        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String location = rs.getString("location");
                int count = rs.getInt("total");
                map.put(location, count);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }
//Q2

    public static List<Warehouse> getTop5WarehousesByCapacity() {
        List<Warehouse> topWarehouses = new ArrayList<>();
        String query = "SELECT * FROM warehouse ORDER BY capacity DESC LIMIT 5";
        try (Connection conn = DB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("id"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setCapacity(rs.getInt("capacity"));
                topWarehouses.add(warehouse);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return topWarehouses;
    }

    // Get top products by selling price
    public static List<Product> getTopProductsBySellingPrice(int limit) throws Exception {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products ORDER BY selling_price DESC LIMIT ?";
        
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("PN"));
                product.setName(rs.getString("name"));
                product.setSellingPrice(rs.getBigDecimal("selling_price"));
                product.setBuyingPrice(rs.getBigDecimal("buying_price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setExpiringDate(rs.getDate("expiring_date").toLocalDate());
                products.add(product);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Error fetching top products by selling price", e);
        }
        
        return products;
    }

    // Get top products by buying price
    public static List<Product> getTopProductsByBuyingPrice(int limit) throws Exception {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products ORDER BY buying_price DESC LIMIT ?";
        
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("PN"));
                product.setName(rs.getString("name"));
                product.setSellingPrice(rs.getBigDecimal("selling_price"));
                product.setBuyingPrice(rs.getBigDecimal("buying_price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setExpiringDate(rs.getDate("expiring_date").toLocalDate());
                products.add(product);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Error fetching top products by buying price", e);
        }
        
        return products;
    }

    // Get top products by quantity
    public static List<Product> getTopProductsByQuantity(int limit) throws Exception {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products ORDER BY quantity DESC LIMIT ?";
        
        try (Connection conn = DB.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("PN"));
                product.setName(rs.getString("name"));
                product.setSellingPrice(rs.getBigDecimal("selling_price"));
                product.setBuyingPrice(rs.getBigDecimal("buying_price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setExpiringDate(rs.getDate("expiring_date").toLocalDate());
                products.add(product);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Error fetching top products by quantity", e);
        }
        
        return products;
    }

}
