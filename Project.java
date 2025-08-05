import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Project {
    Scanner sc=new Scanner(System.in);
    private static final String DB_URL="jdbc:mysql://localhost:3306/Inventary";
    private static final String USER="root";
    private static final String PASS="root";
    Connection conn;
    public Project() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        if(conn != null){
            System.out.println("Connection established successfully!");
            System.out.println("-------------------------------------------------");
        }
        else{
            System.out.println("Failed to established successfully!");

        }
    }

    public void start(){
        int choice;
        do { 
            menu();
            choice=sc.nextInt();
            switch(choice){
                case 1:
                    add();
                    break;
                case 2:
                    view();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    del();
                    break;
                case 5:
                    System.out.println();
                    closeconn();
                    break;
                default:
                    System.out.println("Invalid input try again");
            }

        } while (choice!=5);

    }
    public void add(){
        System.out.println("Enter product name:");
        sc.nextLine();
        String name=sc.nextLine();
        System.out.println("Enter the quantity of the product:");
        int quantity=sc.nextInt();
        System.out.println("Enter the price of the product");
        double prise=sc.nextDouble();

        String addSql="INSERT INTO product_stock(name,quantity,price) VALUES(?,?,?)";
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement insertStmt = conn.prepareStatement(addSql);){
            insertStmt.setString(1,name);
            insertStmt.setInt(2,quantity);
            insertStmt.setDouble(3,prise);
            int rowsAffected=insertStmt.executeUpdate();
            if(rowsAffected>0){
                System.out.println("product "+name+" added successfully");
            }
            else{
                System.out.println("NO products added");
            }

        }
        catch(SQLException e){
            System.err.println("SQL Exception occured"+e.getMessage());
            e.printStackTrace();
        }
    } 
    public void view(){
        String selectSql="SELECT id,name,quantity,price FROM product_stock";

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement selectStmt=conn.prepareStatement(selectSql);
        ResultSet rs=selectStmt.executeQuery();)
        {
            System.out.println("Stock Data");
            System.out.println("----------------------------");
            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                int quantity=rs.getInt("quantity");
                double price=rs.getDouble("price");
                System.out.printf("ID: %d, Name: %s, quantity:%d, price: %.2f%n",id,name,quantity,price);
            }
        }
            catch(SQLException e){
            System.err.println("SQL Exception occured"+e.getMessage());
            e.printStackTrace();
        }
    }
    public void update(){
        System.out.println("Enter product id:");        
        int id=sc.nextInt();
        System.out.println("Enter the quantity of the product:");
        int quantity=sc.nextInt();
        System.out.println("Enter the price of the product");
        double price=sc.nextDouble();
        String updateSql="UPDATE product_stock SET quantity = ? , price = ? WHERE id = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);){
            updateStmt.setInt(1,quantity);
            updateStmt.setDouble(2,price);
            updateStmt.setInt(3,id);
            int rowsAffected=updateStmt.executeUpdate();
            if(rowsAffected>0){
                System.out.println("id number "+id+" updated successfully");
            }
            else{
                System.out.println("NO products updated");
            }
        }
        catch(SQLException e){
            System.err.println("SQL Exception occured"+e.getMessage());
            e.printStackTrace();
        }
    }
    public void del(){
        System.out.println("Enter product id to delete:");        
        int id=sc.nextInt();
        String deleteSql="DELETE FROM product_stock WHERE id = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement updateStmt = conn.prepareStatement(deleteSql);){
            updateStmt.setInt(1,id);
            int rowsAffected=updateStmt.executeUpdate();
            if(rowsAffected>0){
                System.out.println("id number "+id+" deleted successfully");
            }
            else{
                System.out.println("NO products deleted");
            }
        }
        catch(SQLException e){
            System.err.println("SQL Exception occured"+e.getMessage());
            e.printStackTrace();
        }
    }
    

    public void menu(){
        System.out.println("-------------------------------------------------");
        System.out.println("Select the operation");
        System.out.println("1: Add product.");
        System.out.println("2: View products.");
        System.out.println("3: Update product.");
        System.out.println("4: Delete product.");
        System.out.println("5: Exit.");
        System.out.println("Enter value to select operation");
    }
    public void closeconn(){
        try{
            if(conn !=null){
                conn.close();
                System.out.println("Database Connection closed.");
            }
            if(sc!=null){
                sc.close();
                System.out.println("Scanner closed");
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
           
    }

    public static void main(String[] args) throws Exception{
        Project obj=new Project();
        obj.start();
    }
}
