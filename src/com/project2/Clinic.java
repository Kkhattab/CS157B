package com.project2;

import java.sql.*;
import java.util.Scanner;

import static com.project2.Util.readInt;

public class Clinic {

    //README
    //Before running the program create a new database called hc
    //Then import hc.sql into it
    //Type in the following commands
    //mysql -u root
    //create database hc;use hc;
    //source /path/to/hc.sql
    public static void main(String[] args) throws SQLException {
        //connect to database
        try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/hc", "root", "")) {
            //prompt the user for doctor or patient
            System.out.println("Who Am I?");
            Scanner scanner = new Scanner(System.in);
            int nextInt = 0;
            while (nextInt == 0) {
                System.out.println("Please Enter 1 For Doctor and 2 for patient");
                try {
                    nextInt = scanner.nextInt();
                    if (!(nextInt >= 1 && nextInt <= 2)) {
                        nextInt = 0;
                    }
                } catch (Exception e) {
                    System.out.println("Please Enter A Valid Input");
                    scanner.nextLine();
                }
            }
            if (nextInt == 1) {
                //the user is a doctor
                System.out.println("Welcome Doctor!");
                System.out.println("Enter Your Doctor Id.");
                //request doctor id
                int doctorId = readInt(scanner, false);
                //select doctors detail
                try (PreparedStatement statement = c.prepareStatement("select * from doctor where id = ?")) {
                    statement.setInt(1, doctorId);
                    try (ResultSet rs = statement.executeQuery()) {
                        int count = 0;
                        while (rs.next()) {
                            count += 1;
                            //print welcome message
                            System.out.printf("Welcome %10s %10s\n", rs.getString("first_name"), rs.getString("last_name"));
                            //doctor functionality
                            Doctor doctor = new Doctor(c, doctorId);
                            doctor.execute();
                        }
                        if (count == 0) {
                            System.out.println("Doctor Not Found! Try Again");
                        }
                    }
                }

            } else {
                System.out.println("Welcome Patient!");
                System.out.println("Enter Your Patient Id.");
                int patientId = 0;
                while (patientId == 0) {
                    try {
                        patientId = scanner.nextInt();
                        if (patientId == 0) {
                            System.out.println("Invalid Input. Enter Patient ID > 0");
                            scanner.nextLine();
                        }
                    } catch (Exception e) {
                        System.out.println("Please Enter A Valid Input");
                        scanner.nextLine();
                    }
                }
                try (PreparedStatement statement = c.prepareStatement("select * from patient where id = ?")) {
                    statement.setInt(1, patientId);
                    try (ResultSet rs = statement.executeQuery()) {
                        int count = 0;
                        while (rs.next()) {
                            count += 1;
                            System.out.printf("Welcome %10s %10s\n", rs.getString("first_name"), rs.getString("last_name"));
                            //customer functionality
                            Patient patient = new Patient(c, patientId);
                            patient.execute();
                        }
                        if (count == 0) {
                            System.out.println("Patient Not Found! Try Again");
                        }
                    }
                }
            }
        }
    }
}
