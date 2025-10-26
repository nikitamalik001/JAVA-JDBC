import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yourDatabaseName","yourUsername","yourPassword");
            con.setAutoCommit(false);
            Scanner sc = new Scanner(System.in);
            while(true) {
                System.out.println("1. Create Product\n2. Read Products\n3. Update Product\n4. Delete Product\n5. Exit");
                int choice = sc.nextInt();
                sc.nextLine();
                if(choice == 5) break;

                switch(choice) {
                    case 1:
                        System.out.println("Enter Product Name, Price, Quantity:");
                        String name = sc.nextLine();
                        double price = sc.nextDouble();
                        int qty = sc.nextInt();
                        PreparedStatement psInsert = con.prepareStatement(
                            "INSERT INTO Product(ProductName, Price, Quantity) VALUES(?, ?, ?)");
                        psInsert.setString(1, name);
                        psInsert.setDouble(2, price);
                        psInsert.setInt(3, qty);
                        psInsert.executeUpdate();
                        con.commit();
                        System.out.println("Product added.");
                        break;
                    case 2:
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ProductID, ProductName, Price, Quantity FROM Product");
                        while(rs.next()) {
                            System.out.println("ID: " + rs.getInt("ProductID") +
                                               ", Name: " + rs.getString("ProductName") +
                                               ", Price: " + rs.getDouble("Price") +
                                               ", Quantity: " + rs.getInt("Quantity"));
                        }
                        rs.close();
                        break;
                    case 3:
                        System.out.println("Enter ProductID to update:");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter new Name, Price, Quantity:");
                        String newName = sc.nextLine();
                        double newPrice = sc.nextDouble();
                        int newQty = sc.nextInt();
                        PreparedStatement psUpdate = con.prepareStatement(
                            "UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?");
                        psUpdate.setString(1, newName);
                        psUpdate.setDouble(2, newPrice);
                        psUpdate.setInt(3, newQty);
                        psUpdate.setInt(4, updateId);
                        psUpdate.executeUpdate();
                        con.commit();
                        System.out.println("Product updated.");
                        break;
                    case 4:
                        System.out.println("Enter ProductID to delete:");
                        int delId = sc.nextInt();
                        PreparedStatement psDelete = con.prepareStatement(
                            "DELETE FROM Product WHERE ProductID=?");
                        psDelete.setInt(1, delId);
                        psDelete.executeUpdate();
                        con.commit();
                        System.out.println("Product deleted.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
            sc.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
