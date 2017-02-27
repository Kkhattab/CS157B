package com.project2;

import java.sql.*;

import static com.project2.Util.readInt;

/**

 */
public class Patient extends User {

    public Patient(Connection connection, int userId) {
        super(connection, userId);
    }

    @Override
    public void execute() throws SQLException {
        int option = -1;
        //loop with customers functionalities
        while (option != 0) {
            System.out.println("Enter 1 to list doctors.");
            System.out.println("Enter 2 to select a  doctor.");
            System.out.println("Enter 3 to book an appointment.");
            System.out.println("Enter 4 to show your appointments.");
            System.out.println("Enter 5 to cancel an appointment.");
            System.out.println("Enter 6 to reschdule an appointment.");
            System.out.println("Enter 0 to quit.");
            option = readInt(getScanner(), true);
            if (option == 0) {
                //option to quit
                System.out.println("Bye!!!");
            }

            if (option == 1) {
                listDoctors();
            }

            if (option == 2) {
                showDoctorsSchedule();
            }
            if (option == 3) {
                bookAnAppointment();
            }
            if (option == 4) {
                listAppointments();
            }
            if (option == 5) {
                cancelAppoinment();
            }

            if (option == 6) {
                rescheduleAppointment();
            }
        }
    }

    public void rescheduleAppointment() throws SQLException {
        //reschedule appointment
        System.out.println("Reschedule An Appointment. You cannot reschedule cancelled or complete appointments.");
        System.out.println("Enter appointment id to reschedule");
        //request appointment to reschedule
        int aid = readInt(getScanner(), false);
        System.out.println("Select Same/Different Doctor");
        try (Statement statement = getConnection().createStatement()) {
            //show all doctors
            try (ResultSet rs = statement.executeQuery("select * from doctor")) {
                int count = 0;
                while (rs.next()) {
                    count += 1;
                    System.out.printf("%2d %20s %20s %20s\n", rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone"));
                }
                if (count == 0) {
                    System.out.println("No Doctors Found!");
                }
            }
        }
        //request doctor id
        System.out.println("Enter Doctor Id");
        int docId = readInt(getScanner(), false);
        //show the schedule of a doctor
        try (PreparedStatement ps = getConnection().prepareStatement("select distinct s.id as sid, d.first_name as fname,d.last_name as lname,d.phone as phone,s.start_time,s.end_time,s.day_of_week, COALESCE(a.status,'Open') as status from doctor as d left join schedule as s on s.doctor_id = d.id left join appointment as a on a.schedule_id = s.id where d.id = ? order by s.day_of_week")) {
            ps.setInt(1, docId);
            try (ResultSet rs = ps.executeQuery()) {
                try {
                    rs.first();
                    System.out.println("************Schedule********************");
                    System.out.println("First Name: " + rs.getString("fname"));
                    System.out.println("Last Name: " + rs.getString("lname"));
                    System.out.println("Phone: " + rs.getString("phone"));
                    rs.previous();
                    System.out.printf("%2s %11s %10s %10s %8s\n", "ID", "Day Of Week", "Start Time", "End Time", "Status");
                    while (rs.next()) {
                        int sid = rs.getInt("sid");
                        System.out.printf("%2d %11s %10s %10s %8s\n", sid, rs.getString("day_of_week"), getSdf().format(rs.getTime("start_time")), getSdf().format(rs.getTime("end_time")), rs.getString("status"));
                    }
                } catch (Exception e) {
                    System.out.println("Doctor Not Found!");
                }
            }
            System.out.println("");
        }
        //select a schedule slot
        System.out.println("Enter shedule ID");
        int sid = readInt(getScanner(), false);
        //execute update
        try (PreparedStatement ps = getConnection().prepareStatement("update appointment as a set a.schedule_id = ? where a.patient_id = ? and a.id = ? and a.status = 'Booked'")) {
            ps.setInt(1, sid);
            ps.setInt(2, getUserId());
            ps.setInt(3, aid);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                System.out.println("Reschedule Successful.");
            } else {
                System.out.println("Reschedule Failed. Try again.");
            }
        }
        System.out.println("");
    }

    public void cancelAppoinment() throws SQLException {
        //option to cancel an appointment
        System.out.println("Enter Appointment Id To Cancel");
        //request for appointment to cancel
        int aid = readInt(getScanner(), false);
        try (PreparedStatement ps = getConnection().prepareStatement("update appointment as a set a.status = 'Cancelled' where a.patient_id = ? and a.id = ? and a.status = 'Booked'")) {
            ps.setInt(1, getUserId());
            ps.setInt(2, aid);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Appoint ID " + aid + " Cancelled.");
            } else {
                System.out.println("Appoint ID " + aid + " Not Cancelled. Check if it is your appointment or it is  not already cancelled or completed.");
            }
        }
        System.out.println("");
    }

    public void listAppointments() throws SQLException {
        //lists patients appointments
        //an appoinment belongs to a schedule
        //a schedule belongs to a doctor
        //therefore to list all doctors appointments we use an inner join
        System.out.println("Your appointments");
        try (PreparedStatement ps = getConnection().prepareStatement("select a.id as aid,d.first_name as fname,d.last_name as lname,d.phone,s.day_of_week,s.start_time,s.end_time,a.status as status from appointment as a inner join schedule as s on s.id = a.schedule_id inner join doctor as d on d.id = s.doctor_id where a.patient_id = ?")) {
            ps.setInt(1, getUserId());
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%2s %15s %15s %15s %11s %10s %10s %8s\n", "ID", "Doctor F.Name", "Doctor L.Name", "Doctor Phone", "Day Of Week", "Start Time", "End Time", "Status");
                while (rs.next()) {
                    int sid = rs.getInt("aid");
                    System.out.printf("%2d %15s %15s %15s %11s %10s %10s %8s\n", sid, rs.getString("fname"), rs.getString("lname"), rs.getString("phone"), rs.getString("day_of_week"), getSdf().format(rs.getTime("start_time")), getSdf().format(rs.getTime("end_time")), rs.getString("status"));
                }
            }
            System.out.println("");
        }
    }

    public void bookAnAppointment() {
        System.out.println("To Book An Appointment Please Enter Schedule Id");
        int scheduleId = readInt(getScanner(), false);
        //book an appointment
        try (PreparedStatement st = getConnection().prepareStatement("insert into appointment(status,patient_id,schedule_id) values(?,?,?)")) {
            st.setString(1, "Booked");
            st.setInt(2, getUserId());
            st.setInt(3, scheduleId);
            int affected = st.executeUpdate();
            if (affected > 0) {
                System.out.println("Appointment Successfuly created!");
            } else {
                System.out.println("Appointment Could Not Be Created!");
            }
        } catch (Exception e) {
            System.out.println("Please Select An Open Schedule");
            System.out.println("");
        }
    }

    /**
     * Show doctors schedule/appointments
     *
     * @throws SQLException
     */
    public void showDoctorsSchedule() throws SQLException {
        //show doctors schedule
        System.out.println("Enter Doctor Id");
        int docId = readInt(getScanner(), false);
        //a doctor may not have a schedule and apointments
        //hence left join on both schedule and appoints
        //in a left join the 'left side' is always returned even when
        //the right side is null
        try (PreparedStatement ps = getConnection().prepareStatement("select distinct s.id as sid, d.first_name as fname,d.last_name as lname,d.phone as phone,s.start_time,s.end_time,s.day_of_week, COALESCE(a.status,'Open') as status from doctor as d left join schedule as s on s.doctor_id = d.id left join appointment as a on a.schedule_id = s.id where d.id = ? order by s.day_of_week")) {
            ps.setInt(1, docId);
            try (ResultSet rs = ps.executeQuery()) {
                try {
                    rs.first();
                    System.out.println("************Schedule********************");
                    System.out.println("First Name: " + rs.getString("fname"));
                    System.out.println("Last Name: " + rs.getString("lname"));
                    System.out.println("Phone: " + rs.getString("phone"));
                    rs.previous();
                    System.out.printf("%2s %11s %10s %10s %8s\n", "ID", "Day Of Week", "Start Time", "End Time", "Status");
                    while (rs.next()) {
                        int sid = rs.getInt("sid");
                        System.out.printf("%2d %11s %10s %10s %8s\n", sid, rs.getString("day_of_week"), getSdf().format(rs.getTime("start_time")), getSdf().format(rs.getTime("end_time")), rs.getString("status"));
                    }
                } catch (Exception e) {
                    System.out.println("Doctor Not Found!");
                }
            }
            System.out.println("");
        }
    }

    /**
     * list all doctors
     *
     * @throws SQLException
     */
    public void listDoctors() throws SQLException {
        //functionality to list doctors
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet rs = statement.executeQuery("select * from doctor")) {
                int count = 0;
                while (rs.next()) {
                    count += 1;
                    System.out.printf("%2d %20s %20s %20s\n", rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone"));
                }
                if (count == 0) {
                    System.out.println("No Doctors Found!");
                }
            }
        }
    }

}
