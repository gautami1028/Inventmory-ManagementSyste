import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_NAME = "inventory";
    static final String USER = "root";
    static final String PASS = "Gautami@123!";
    static final String CREATE_DB_SQL = "CREATE DATABASE IF NOT EXISTS inventory";
    static final String CREATE_PRODUCTS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS Products (" +
            "ProductID INT AUTO_INCREMENT PRIMARY KEY," +
            "ProductName VARCHAR(255) NOT NULL," +
            "Description TEXT," +
            "CategoryID INT," +
            "SupplierID INT," +
            "UnitPrice DECIMAL(10, 2)," +
            "StockQuantity INT)";
    static final String CREATE_CATEGORIES_TABLE_SQL = "CREATE TABLE IF NOT EXISTS Categories (" +
            "CategoryID INT PRIMARY KEY," +
            "CategoryName VARCHAR(255))";
    static final String CREATE_SUPPLIERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS Suppliers (" +
            "SupplierID INT PRIMARY KEY," +
            "SupplierName VARCHAR(255)," +
            "ContactName VARCHAR(255)," +
            "ContactEmail VARCHAR(255)," +
            "ContactPhone VARCHAR(20))";
    static final String CREATE_TRANSACTIONS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS Transactions (" +
            "TransactionID INT PRIMARY KEY," +
            "TransactionType VARCHAR(50)," +
            "ProductID INT," +
            "Quantity INT," +
            "TransactionDate DATE," +
            "FOREIGN KEY (ProductID) REFERENCES Products(ProductID))";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            createDatabase(stmt, DB_NAME);
            useDatabase(stmt, DB_NAME);
            createTable(stmt, CREATE_PRODUCTS_TABLE_SQL);
            createTable(stmt, CREATE_CATEGORIES_TABLE_SQL);
            createTable(stmt, CREATE_SUPPLIERS_TABLE_SQL);
            createTable(stmt, CREATE_TRANSACTIONS_TABLE_SQL);

            boolean run = true;

            while (run) {
                System.out.println("1. Add Product\n2. Add Category\n3. Add Supplier\n4. Add Transaction\n" +
                        "5. Delete Product\n6. Delete Category\n7. Delete Supplier\n8. Delete Transaction\n" +
                        "9. Return Product\n10. Display Products\n11. Exit\nEnter choice: ");
                int ch = sc.nextInt();
                sc.nextLine();

                switch (ch) {
                    case 1:
                        addProduct(stmt, sc);
                        break;

                    case 2:
                        addCategory(stmt, sc);
                        break;

                    case 3:
                        addSupplier(stmt, sc);
                        break;

                    case 4:
                        addTransaction(stmt, sc);
                        break;

                    case 5:
                        deleteProduct(stmt, sc);
                        break;

                    case 6:
                        deleteCategory(stmt, sc);
                        break;

                    case 7:
                        deleteSupplier(stmt, sc);
                        break;

                    case 8:
                        deleteTransaction(stmt, sc);
                        break;

                    case 9:
                        returnProduct(stmt, sc);
                        break;

                    case 10:
                        displayProducts(stmt);
                        break;

                    case 11:
                        run = false;
                        break;

                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            }

        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private static void createDatabase(Statement stmt, String dbName) throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
        stmt.executeUpdate(sql);
        System.out.println("Database created or already exists.");
    }

    private static void useDatabase(Statement stmt, String dbName) throws SQLException {
        String sql = "USE " + dbName;
        stmt.executeUpdate(sql);
        System.out.println("Using database.");
    }

    private static void createTable(Statement stmt, String createTableSql) throws SQLException {
        stmt.executeUpdate(createTableSql);
        System.out.println("Table created");
    }

    private static void addProduct(Statement stmt, Scanner scanner) throws SQLException {
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Category ID: ");
        int categoryId = scanner.nextInt();
        System.out.print("Enter Supplier ID: ");
        int supplierId = scanner.nextInt();
        System.out.print("Enter Unit Price: ");
        double unitPrice = scanner.nextDouble();
        System.out.print("Enter Stock Quantity: ");
        int stockQuantity = scanner.nextInt();

        String sql = "INSERT INTO Products (ProductName, Description, CategoryID, SupplierID, UnitPrice, StockQuantity) " +
                "VALUES ('" + productName + "', '" + description + "', " + categoryId + ", " + supplierId + ", " +
                unitPrice + ", " + stockQuantity + ")";
        stmt.executeUpdate(sql);
        System.out.println("Product added.");
    }

    private static void addCategory(Statement stmt, Scanner scanner) throws SQLException {
        System.out.print("Enter Category ID: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Category Name: ");
        String categoryName = scanner.nextLine();

        String sql = "INSERT INTO Categories (CategoryID, CategoryName) VALUES (" + categoryId + ", '" + categoryName + "')";
        stmt.executeUpdate(sql);
        System.out.println("Category added.");
    }

    private static void addSupplier(Statement stmt, Scanner scanner) throws SQLException {
        System.out.print("Enter Supplier ID: ");
        int supplierId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Supplier Name: ");
        String supplierName = scanner.nextLine();
        System.out.print("Enter Contact Name: ");
        String contactName = scanner.nextLine();
        System.out.print("Enter Contact Email: ");
        String contactEmail = scanner.nextLine();
        System.out.print("Enter Contact Phone: ");
        String contactPhone = scanner.nextLine();

        String sql = "INSERT INTO Suppliers (SupplierID, SupplierName, ContactName, ContactEmail, ContactPhone) " +
                "VALUES (" + supplierId + ", '" + supplierName + "', '" + contactName + "', '" +
                contactEmail + "', '" + contactPhone + "')";
        stmt.executeUpdate(sql);
        System.out.println("Supplier added.");
    }

    private static void addTransaction(Statement stmt, Scanner scanner) throws SQLException {
        System.out.print("Enter Transaction ID: ");
        int transactionId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Transaction Type: ");
        String transactionType = scanner.nextLine();
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter Transaction Date (YYYY-MM-DD): ");
        String transactionDate = scanner.nextLine();

        String sql = "INSERT INTO Transactions (TransactionID, TransactionType, ProductID, Quantity, TransactionDate) " +
                "VALUES (" + transactionId + ", '" + transactionType + "', " + productId + ", " +
                quantity + ", '" + transactionDate + "')";
        stmt.executeUpdate(sql);
        System.out.println("Transaction added.");
    }

    private static void deleteProduct(Statement stmt, Scanner scanner) throws SQLException {
        System.out.print("Enter product ID to delete: ");
        int productIdToDelete = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String sql = "DELETE FROM Products WHERE ProductID = " + productIdToDelete;
        int rowsAffected = stmt.executeUpdate(sql);
        if (rowsAffected > 0) {
            System.out.println("Product with ID " + productIdToDelete + " deleted.");
        } else {
            System.out.println("Product with ID " + productIdToDelete + " not found.");
        }
    }

    private static void deleteCategory(Statement stmt, Scanner scanner) throws SQLException {
        System.out.print("Enter category ID to delete: ");
        int categoryIdToDelete = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String sql = "DELETE FROM Categories WHERE CategoryID = " + categoryIdToDelete;
        int rowsAffected = stmt.executeUpdate(sql);
        if (rowsAffected > 0) {
            System.out.println("Category with ID " + categoryIdToDelete + " deleted.");
        } else {
            System.out.println("Category with ID " + categoryIdToDelete + " not found.");
        }
    }

    private static void deleteSupplier(Statement stmt, Scanner scanner) throws SQLException {
        System.out.print("Enter supplier ID to delete: ");
        int supplierIdToDelete = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String sql = "DELETE FROM Suppliers WHERE SupplierID = " + supplierIdToDelete;
        int rowsAffected = stmt.executeUpdate(sql);
        if (rowsAffected > 0) {
            System.out.println("Supplier with ID " + supplierIdToDelete + " deleted.");
        } else {
            System.out.println("Supplier with ID " + supplierIdToDelete + " not found.");
        }
    }

    private static void deleteTransaction(Statement stmt, Scanner scanner) throws SQLException {
        System.out.print("Enter transaction ID to delete: ");
        int transactionIdToDelete = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String sql = "DELETE FROM Transactions WHERE TransactionID = " + transactionIdToDelete;
        int rowsAffected = stmt.executeUpdate(sql);
        if (rowsAffected > 0) {
            System.out.println("Transaction with ID " + transactionIdToDelete + " deleted.");
        } else {
            System.out.println("Transaction with ID " + transactionIdToDelete + " not found.");
        }
    }

    private static void returnProduct(Statement stmt, Scanner scanner) throws SQLException {
        addProduct(stmt, scanner);
        System.out.println("Product returned and added back to inventory.");
    }

    private static void displayProducts(Statement stmt) throws SQLException {
        String sql = "SELECT * FROM Products";
        ResultSet resultSet = stmt.executeQuery(sql);

        while (resultSet.next()) {
            int productId = resultSet.getInt("ProductID");
            String productName = resultSet.getString("ProductName");
            String description = resultSet.getString("Description");
            int categoryId = resultSet.getInt("CategoryID");
            int supplierId = resultSet.getInt("SupplierID");
            double unitPrice = resultSet.getDouble("UnitPrice");
            int stockQuantity = resultSet.getInt("StockQuantity");

            System.out.println("Product ID: " + productId + ", Product Name: " + productName +
                    ", Description: " + description + ", Category ID: " + categoryId +
                    ", Supplier ID: " + supplierId + ", Unit Price: " + unitPrice +
                    ", Stock Quantity: " + stockQuantity);
        }
    }    
}

