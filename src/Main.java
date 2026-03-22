import java.util.Scanner;
import java.sql.*;

public class Main {

    public static Connection getConnection() throws SQLException ,ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/startAhb";
        return DriverManager.getConnection(
                url,
                "root",
                "root"
        );
    }
    
    public static void main(String[] args) {
        
        try {
            int ch;
            Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nChoose the Option ");
            System.out.println("1.Insert Record ");
            System.out.println("2.Delete Record");
            System.out.println("3.Update Record");
            System.out.println("4.Display all Records");
            System.out.println("5.Insert Multiple Records");
            System.out.println("6.Exit");

            ch = sc.nextInt();

            switch (ch) {
                case 1 -> insert();
                case 2 -> delete();
                case 3 -> update();
                case 4 -> display();
               case 5 -> insertBatch();
                case 6 -> {
                    break;
                }
            }
        }while(ch!=6);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertBatch() throws SQLException {

        Scanner sc = new Scanner(System.in);

        System.out.print("How many records do you want to insert: ");
        int count = sc.nextInt();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            String sql = "INSERT INTO dyp (roll, fname, mname, lname) VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);

            for (int i = 0; i < count; i++) {

                System.out.println("\nEnter details for record " + (i + 1));

                System.out.print("Roll: ");
                int roll = sc.nextInt();

                System.out.print("First Name: ");
                String fname = sc.next();

                System.out.print("Middle Name: ");
                String mname = sc.next();

                System.out.print("Last Name: ");
                String lname = sc.next();

                stmt.setInt(1, roll);
                stmt.setString(2, fname);
                stmt.setString(3, mname);
                stmt.setString(4, lname);

                stmt.addBatch();   // Add to batch
            }

            int[] res = stmt.executeBatch();  // Execute batch
            con.commit();

            System.out.println(res.length + " rows inserted successfully.");

        } catch (SQLDataException | ClassNotFoundException e) {
            if (con != null) {
                con.rollback();   // Rollback on error
            }
            System.out.println(e.getMessage());
        }
        finally {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
            sc.close();
        }
    }

    public static void insert() {
        Connection con = null;
        Scanner sc = new Scanner(System.in);

        try {
            con = getConnection();
            con.setAutoCommit(false);

            System.out.print("Enter Roll Number: ");
            int roll = sc.nextInt();

            System.out.print("Enter First Name: ");
            String fname = sc.next();

            System.out.print("Enter Middle Name: ");
            String mname = sc.next();

            System.out.print("Enter Last Name: ");
            String lname = sc.next();

            String query = "INSERT INTO dyp (roll, fname, mname, lname) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, roll);
            stmt.setString(2, fname);
            stmt.setString(3, mname);
            stmt.setString(4, lname);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Record inserted successfully");
            }

            con.commit();
            stmt.clearBatch();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate Entry: Roll number already exists");
        } catch (SQLException e) {
            rollbackConnection(con);
            System.out.println("SQL Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Error: " + e.getMessage());
        } finally {
            closeConnection(con);
        }
    }


    public static void display() {
        try {
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM dyp");

            System.out.println("Roll Number | First Name | Middle Name | Last Name");
            System.out.println("---------------------------------------------------");

            while (rs.next()) {
                int roll = rs.getInt("roll");
                String fname = rs.getString("fname");
                String mname = rs.getString("mname");
                String lname = rs.getString("lname");

                System.out.println(roll + " | " + fname + " | " + mname + " | " + lname);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void delete() {
        Connection con = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter Roll Number to Delete:");
            int roll = sc.nextInt();

            PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM dyp WHERE roll=?"
            );

            stmt.setInt(1, roll);

            int res = stmt.executeUpdate();

            if (res > 0) {
                System.out.println("Record Deleted Successfully");
            } else {
                System.out.println("Record Not Found");
            }

            con.commit();

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }


    public static void update() {
        Connection con = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter Roll Number to Update:");
            int roll = sc.nextInt();
            sc.nextLine();

            System.out.println("What do you want to update?");
            System.out.println("1. First Name");
            System.out.println("2. Middle Name");
            System.out.println("3. Last Name");
            System.out.println("4. All Fields");

            int choice = sc.nextInt();
            sc.nextLine();

            PreparedStatement stmt = null;

            switch (choice) {

                case 1 -> {
                    System.out.println("Enter New First Name:");
                    String fname = sc.nextLine();

                    stmt = con.prepareStatement(
                            "UPDATE dyp SET fname=? WHERE roll=?"
                    );
                    stmt.setString(1, fname);
                    stmt.setInt(2, roll);
                }

                case 2 -> {
                    System.out.println("Enter New Middle Name:");
                    String mname = sc.nextLine();

                    stmt = con.prepareStatement(
                            "UPDATE dyp SET mname=? WHERE roll=?"
                    );
                    stmt.setString(1, mname);
                    stmt.setInt(2, roll);
                }

                case 3 -> {
                    System.out.println("Enter New Last Name:");
                    String lname = sc.nextLine();

                    stmt = con.prepareStatement(
                            "UPDATE dyp SET lname=? WHERE roll=?"
                    );
                    stmt.setString(1, lname);
                    stmt.setInt(2, roll);
                }

                case 4 -> {
                    System.out.println("Enter New First Name:");
                    String fname = sc.nextLine();

                    System.out.println("Enter New Middle Name:");
                    String mname = sc.nextLine();

                    System.out.println("Enter New Last Name:");
                    String lname = sc.nextLine();

                    stmt = con.prepareStatement(
                            "UPDATE dyp SET fname=?, mname=?, lname=? WHERE roll=?"
                    );

                    stmt.setString(1, fname);
                    stmt.setString(2, mname);
                    stmt.setString(3, lname);
                    stmt.setInt(4, roll);
                }

                default -> {
                    System.out.println("Invalid Choice");
                    return;
                }
            }

            int res = stmt.executeUpdate();

            if (res > 0) {
                System.out.println("Record Updated Successfully");
            } else {
                System.out.println("Record Not Found");
            }

            con.commit();

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }

    private static void rollbackConnection(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException ex) {
            System.out.println("Rollback Error: " + ex.getMessage());
        }
    }

    private static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Connection Close Error: " + e.getMessage());
        }
    }
}