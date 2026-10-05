package com.complinemanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ComplineManagement {

	static final String URL = "jdbc:mysql://localhost:3306/complaint_db";
	static final String USER = "root";
	static final String PASSWORD = "root";
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            while (true) {

                System.out.println("\n===== COMPLAINT MANAGEMENT SYSTEM =====");
                System.out.println("1. Register User");
                System.out.println("2. Register Officer");
                System.out.println("3. Register Complaint");
                System.out.println("4. Assign Officer");
                System.out.println("5. Update Complaint Status");
                System.out.println("6. View Complaints");
                System.out.println("7. Exit");

                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                // 1. Register User
                if (choice == 1) {

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();

                    String sql =
                            "INSERT INTO users(name, email, phone) VALUES (?, ?, ?)";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.setString(3, phone);

                    ps.executeUpdate();

                    System.out.println("User registered successfully!");
                }

                // 2. Register Officer
                else if (choice == 2) {

                    System.out.print("Enter officer name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter department: ");
                    String department = sc.nextLine();

                    String sql =
                            "INSERT INTO officers(officer_name, department) VALUES (?, ?)";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, name);
                    ps.setString(2, department);

                    ps.executeUpdate();

                    System.out.println("Officer registered successfully!");
                }

                // 3. Register Complaint
                else if (choice == 3) {

                    System.out.print("Enter user ID: ");
                    int userId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter complaint: ");
                    String complaint = sc.nextLine();

                    String sql =
                            "INSERT INTO complaints(user_id, complaint_text, status) " +
                            "VALUES (?, ?, 'Pending')";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setInt(1, userId);
                    ps.setString(2, complaint);

                    ps.executeUpdate();

                    System.out.println("Complaint registered successfully!");
                }

                // 4. Assign Officer
                else if (choice == 4) {

                    System.out.print("Enter complaint ID: ");
                    int complaintId = sc.nextInt();

                    System.out.print("Enter officer ID: ");
                    int officerId = sc.nextInt();

                    String sql =
                            "UPDATE complaints SET officer_id = ? " +
                            "WHERE complaint_id = ?";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setInt(1, officerId);
                    ps.setInt(2, complaintId);

                    int rows = ps.executeUpdate();

                    if (rows > 0)
                        System.out.println("Officer assigned successfully!");
                    else
                        System.out.println("Complaint not found.");
                }

                // 5. Update Status
                else if (choice == 5) {

                    System.out.print("Enter complaint ID: ");
                    int complaintId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter new status: ");
                    String status = sc.nextLine();

                    System.out.print("Enter resolution: ");
                    String resolution = sc.nextLine();

                    String sql =
                            "UPDATE complaints SET status = ?, resolution = ? " +
                            "WHERE complaint_id = ?";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, status);
                    ps.setString(2, resolution);
                    ps.setInt(3, complaintId);

                    int rows = ps.executeUpdate();

                    if (rows > 0)
                        System.out.println("Complaint updated successfully!");
                    else
                        System.out.println("Complaint not found.");
                }

                // 6. View Complaints
                else if (choice == 6) {

                    String sql =
                            "SELECT c.complaint_id, u.name, " +
                            "c.complaint_text, o.officer_name, " +
                            "c.status, c.resolution " +
                            "FROM complaints c " +
                            "JOIN users u ON c.user_id = u.user_id " +
                            "LEFT JOIN officers o ON c.officer_id = o.officer_id";

                    Statement st = con.createStatement();

                    ResultSet rs = st.executeQuery(sql);

                    System.out.println("\n===== COMPLAINTS =====");

                    while (rs.next()) {

                        System.out.println(
                                "\nComplaint ID: " +
                                rs.getInt("complaint_id"));

                        System.out.println(
                                "User: " +
                                rs.getString("name"));

                        System.out.println(
                                "Complaint: " +
                                rs.getString("complaint_text"));

                        System.out.println(
                                "Officer: " +
                                rs.getString("officer_name"));

                        System.out.println(
                                "Status: " +
                                rs.getString("status"));

                        System.out.println(
                                "Resolution: " +
                                rs.getString("resolution"));
                    }
                }

                // 7. Exit
                else if (choice == 7) {

                    System.out.println("Program closed.");
                    break;
                }

                else {
                    System.out.println("Invalid choice!");
                }
            }

            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	}


