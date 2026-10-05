package com.project1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class VehicleService {
	  static final String URL =
	            "jdbc:mysql://localhost:3306/vehicle_service";

	    static final String USER = "root";

	    static final String PASSWORD = "root";

	    public static void main(String[] args) {

	        Scanner sc = new Scanner(System.in);

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection con =
	                    DriverManager.getConnection(URL, USER, PASSWORD);

	            while (true) {

	                System.out.println("\n===== VEHICLE SERVICE CENTER =====");
	                System.out.println("1. Register Customer");
	                System.out.println("2. Register Vehicle");
	                System.out.println("3. Book Service");
	                System.out.println("4. View Service History");
	                System.out.println("5. View Customers");
	                System.out.println("6. View Vehicles");
	                System.out.println("7. Exit");

	                System.out.print("Enter choice: ");
	                int choice = sc.nextInt();
	                sc.nextLine();

	                // 1. Register Customer
	                if (choice == 1) {

	                    System.out.print("Enter customer name: ");
	                    String name = sc.nextLine();

	                    System.out.print("Enter phone: ");
	                    String phone = sc.nextLine();

	                    System.out.print("Enter email: ");
	                    String email = sc.nextLine();

	                    String sql =
	                            "INSERT INTO customers(name, phone, email) " +
	                            "VALUES (?, ?, ?)";

	                    PreparedStatement ps =
	                            con.prepareStatement(sql);

	                    ps.setString(1, name);
	                    ps.setString(2, phone);
	                    ps.setString(3, email);

	                    ps.executeUpdate();

	                    System.out.println(
	                            "Customer registered successfully!");
	                }

	                // 2. Register Vehicle
	                else if (choice == 2) {

	                    System.out.print("Enter customer ID: ");
	                    int customerId = sc.nextInt();
	                    sc.nextLine();

	                    System.out.print("Enter vehicle number: ");
	                    String vehicleNumber = sc.nextLine();

	                    System.out.print("Enter vehicle model: ");
	                    String vehicleModel = sc.nextLine();

	                    String sql =
	                            "INSERT INTO vehicles " +
	                            "(customer_id, vehicle_number, vehicle_model) " +
	                            "VALUES (?, ?, ?)";

	                    PreparedStatement ps =
	                            con.prepareStatement(sql);

	                    ps.setInt(1, customerId);
	                    ps.setString(2, vehicleNumber);
	                    ps.setString(3, vehicleModel);

	                    ps.executeUpdate();

	                    System.out.println(
	                            "Vehicle registered successfully!");
	                }

	                // 3. Book Service
	                else if (choice == 3) {

	                    System.out.print("Enter vehicle ID: ");
	                    int vehicleId = sc.nextInt();
	                    sc.nextLine();

	                    System.out.print(
	                            "Enter service date (YYYY-MM-DD): ");
	                    String date = sc.nextLine();

	                    System.out.print("Enter service type: ");
	                    String serviceType = sc.nextLine();

	                   

	                    String sql =
	                            "INSERT INTO service_records " +
	                            "(vehicle_id, service_date, service_type, cost) " +
	                            "VALUES (?, ?, ?)";

	                    PreparedStatement ps =
	                            con.prepareStatement(sql);

	                    ps.setInt(1, vehicleId);
	                    ps.setDate(2, Date.valueOf(date));
	                    ps.setString(3, serviceType);
	                  

	                    ps.executeUpdate();

	                    System.out.println(
	                            "Service booked successfully!");
	                }

	                // 4. View Service History
	                else if (choice == 4) {

	                    System.out.print("Enter vehicle ID: ");
	                    int vehicleId = sc.nextInt();

	                    String sql =
	                            "SELECT s.service_id, " +
	                            "v.vehicle_number, " +
	                            "v.vehicle_model, " +
	                            "s.service_date, " +
	                            "s.service_type, " +
	                            "s.cost " +
	                            "FROM service_records s " +
	                            "JOIN vehicles v " +
	                            "ON s.vehicle_id = v.vehicle_id " +
	                            "WHERE s.vehicle_id = ?";

	                    PreparedStatement ps =
	                            con.prepareStatement(sql);

	                    ps.setInt(1, vehicleId);

	                    ResultSet rs = ps.executeQuery();

	                    System.out.println(
	                            "\n===== SERVICE HISTORY =====");

	                    while (rs.next()) {

	                        System.out.println(
	                                "Service ID: " +
	                                rs.getInt("service_id"));

	                        System.out.println(
	                                "Vehicle Number: " +
	                                rs.getString("vehicle_number"));

	                        System.out.println(
	                                "Vehicle Model: " +
	                                rs.getString("vehicle_model"));

	                        System.out.println(
	                                "Service Date: " +
	                                rs.getDate("service_date"));

	                        System.out.println(
	                                "Service Type: " +
	                                rs.getString("service_type"));

	                        System.out.println(
	                                "Cost: ₹" +
	                                rs.getDouble("cost"));

	                        System.out.println("-------------------------");
	                    }
	                }

	                // 5. View Customers
	                else if (choice == 5) {

	                    String sql = "SELECT * FROM customers";

	                    Statement st = con.createStatement();

	                    ResultSet rs = st.executeQuery(sql);

	                    System.out.println("\n===== CUSTOMERS =====");

	                    while (rs.next()) {

	                        System.out.println(
	                                "ID: " +
	                                rs.getInt("customer_id"));

	                        System.out.println(
	                                "Name: " +
	                                rs.getString("name"));

	                        System.out.println(
	                                "Phone: " +
	                                rs.getString("phone"));

	                        System.out.println(
	                                "Email: " +
	                                rs.getString("email"));

	                        System.out.println("--------------------");
	                    }
	                }

	                // 6. View Vehicles
	                else if (choice == 6) {

	                    String sql =
	                            "SELECT v.vehicle_id, " +
	                            "v.vehicle_number, " +
	                            "v.vehicle_model, " +
	                            "c.name " +
	                            "FROM vehicles v " +
	                            "JOIN customers c " +
	                            "ON v.customer_id = c.customer_id";

	                    Statement st = con.createStatement();

	                    ResultSet rs = st.executeQuery(sql);

	                    System.out.println("\n===== VEHICLES =====");

	                    while (rs.next()) {

	                        System.out.println(
	                                "Vehicle ID: " +
	                                rs.getInt("vehicle_id"));

	                        System.out.println(
	                                "Customer: " +
	                                rs.getString("name"));

	                        System.out.println(
	                                "Vehicle Number: " +
	                                rs.getString("vehicle_number"));

	                        System.out.println(
	                                "Vehicle Model: " +
	                                rs.getString("vehicle_model"));

	                        System.out.println("--------------------");
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

	


