package com.project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.project2.Util.readInt;

/**

 */
public class Doctor extends User {

    public Doctor(Connection connection, int userId) {
        super(connection, userId);
    }

    @Override
    public void execute() throws SQLException {
        int option = -1;
        //loop with doctors functionalities
        //the loop exists when a doctor  enters 0
        while (option != 0) {
            System.out.println("Enter 1 To Create A Schedule");
            System.out.println("Enter 2 To Update A Schedule");
            System.out.println("Enter 3 To Delete A Schedule");
            System.out.println("Enter 4 To Complete An Appointment");
            System.out.println("Enter 5 To List Appointments");
            System.out.println("Enter 6 To Show Schedule");
            System.out.println("Enter 7 To Pick Up A Patient From Another Doctor.");
            System.out.println("Enter 0 To Quit");
            option = readInt(getScanner(), true);
            if (option == 0) {
                //option to quit
                System.out.println("Bye!!");
            }
            if (option == 1) {
                createSchedule();
            }
            if (option == 2) {
                //option to update schedule
                showSchedule();
                updateSchedule();
            }
            if (option == 3) {
                //option to delete a schedule
                showSchedule();
                deleteSchedule();
            }
            if (option == 4) {
                completeAppointment();
            }
            if (option == 5) {
                //lists doctors appointments
                System.out.println("My Appointments");
                showAppointments();
            }
            if (option == 6) {
                //lists doctor schedule
                showSchedule();
            }
            if (option == 7) {
                pickupPatientFromAnotherDoctor();
            }
        }
    }

    /**
     * pick a patient from another doctor
     *
     * @throws SQLException
     */
    public void pickupPatientFromAnotherDoctor() throws SQLException {
        //pick patient from another doctor
        //in this implementation
        //the effect is to update the doctor of a given
        //schedule with a certain appointment
        //show your schedule
        showSchedule();
        //prompt for other doctors id
        System.out.println("Enter Doctor ID to pick a patient from");
        int did = readInt(getScanner(), false);
        System.out.println("Other Doctors Appointments");
        //show other doctors appointment
        new Doctor(getConnection(), did).showAppointments();
        System.out.println("Select Appointment ID to take from the other doctor");
        int aid = readInt(getScanner(), false);
        //another update using join
        //here we are changing calender entry given the doctor ids
        //and the appointment id
        try (PreparedStatement ps = getConnection().prepareStatement("update appointment as a inner join schedule as s on s.id = a.schedule_id set s.doctor_id = ? where s.doctor_id = ? and a.status = ? and a.id = ?")) {
            ps.setInt(1, getUserId());
            ps.setInt(2, did);
            ps.setString(3, "Booked");
            ps.setInt(4, aid);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("Appointment Transferred successfully");
                System.out.println("");
            } else {
                System.out.println("Failed. Check your inputs");
                System.out.println("");
            }
        }
    }

    /**
     * Lists doctors appointments
     *
     * @throws SQLException
     */
    public void showAppointments() throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement("select a.id as aid,p.first_name as fname,p.last_name as lname,p.phone as phone,s.day_of_week,s.start_time,s.end_time,a.status from appointment as a  inner join schedule as s on s.id = a.schedule_id inner join doctor as d on d.id = s.doctor_id inner join patient as p on p.id = a.patient_id where d.id = ?")) {
            ps.setInt(1, getUserId());
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%2s %15s %15s %15s %11s %10s %10s %8s\n", "ID", "Patient F.Name", "Patient L.Name", "Patient Phone", "Day Of Week", "Start Time", "End Time", "Status");
                while (rs.next()) {
                    int sid = rs.getInt("aid");
                    System.out.printf("%2d %15s %15s %15s %11s %10s %10s %8s\n", sid, rs.getString("fname"), rs.getString("lname"), rs.getString("phone"), rs.getString("day_of_week"), getSdf().format(rs.getTime("start_time")), getSdf().format(rs.getTime("end_time")), rs.getString("status"));
                }
            }
            System.out.println("");
        }
    }

    public void completeAppointment() throws SQLException {
        //option to complete an appointment
        System.out.println("Enter Appointment ID to complete");
        int aid = readInt(getScanner(), false);
        //complete an appointment
        //cancelled & complete appoinments cannot be completed
        //this is an update with join
        //here we update the appointment which is not complete or cancelled
        //and the appointment must belong to a schedule  x which belongs to doctor y
        //hence a join
        try (PreparedStatement ps = getConnection().prepareStatement("update appointment as a inner join schedule as s on s.id = a.schedule_id set a.status = 'Complete' where s.doctor_id = ? and a.id = ? and a.status = 'Booked'")) {
            ps.setInt(1, getUserId());
            ps.setInt(2, aid);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                System.out.println("Appointment Completed");
            } else {
                System.out.println("Zero appointment completed!");
            }
            System.out.println("");
        }
    }

    public void deleteSchedule() {
        System.out.println("Enter Schedule ID to delete");
        //ask for schedule id to delete
        int sid = readInt(getScanner(), false);
        //delete schedule from database
        try (PreparedStatement ps = getConnection().prepareStatement("delete s from schedule as s inner join doctor as d where d.id = ? and s.id = ?")) {
            ps.setInt(1, getUserId());
            ps.setInt(2, sid);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                System.out.println("Schedule Deleted!");
            } else {
                System.out.println("No Schedule Deleted");
            }
        } catch (Exception e) {
            //error message in case schedule entry cannot be deleted
            System.out.println("Cannot Delete The Schedule. Looks like there is an appointment.");
        }
    }

    public void updateSchedule() {
        System.out.println("Enter Schedule ID to update");
        int sid = readInt(getScanner(), false);
        System.out.println("Enter week day. Valid option (Mon,Tue,Wed,Thu,Fri)");
        //request for day
        String day = getScanner().next();
        System.out.println("Enter Start Time e.g 12:00");
        //request for start time
        String start = getScanner().next();
        System.out.println("Enter End Time e.g 13:00");
        //request for end time
        String end = getScanner().next();
        if (day != null && !day.isEmpty() && start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
            //update database if all fields are present
            try (PreparedStatement st = getConnection().prepareStatement("update schedule as s set s.day_of_week = ?, start_time = ?,end_time = ? where s.doctor_id = ? and s.id = ?")) {
                st.setString(1, day);
                st.setString(2, start);
                st.setString(3, end);
                st.setInt(4, getUserId());
                st.setInt(5, sid);
                int executeUpdate = st.executeUpdate();
                if (executeUpdate > 0) {
                    System.out.println("Schedule Updated");
                    System.out.println("");
                } else {
                    System.out.println("Schedule Not Updated. Check your inputs.");
                    System.out.println("");
                }
            } catch (Exception e) {
                System.out.println("Something Went wrong. Check your inputs.");
                System.out.println("");
            }
        } else {
            System.out.println("All inputs are required!");
            System.out.println("");
        }
    }

    /**
     * Shows doctors schedule
     *
     * @throws SQLException
     */
    public void showSchedule() throws SQLException {
        System.out.println("My Schedule");
        System.out.printf("%3s %11s %10s %10s\n", "ID", "Day Of Week", "Start Time", "End Time");
        try (PreparedStatement ps = getConnection().prepareStatement("select * from schedule where doctor_id = ? order by day_of_week asc")) {
            ps.setInt(1, getUserId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("%3d %11s %10s %10s\n", rs.getInt("id"), rs.getString("day_of_week"), getSdf().format(rs.getTime("start_time")), getSdf().format(rs.getTime("end_time")));
                }
            }
        }
    }

    /**
     * Creates a schedule slot
     */
    public void createSchedule() {
        //create a new schedule entry
        System.out.println("Enter week day. Valid options (Mon,Tue,Wed,Thu,Fri)");
        //request for day
        String day = getScanner().next();
        System.out.println("Enter Start Time e.g 12:00");
        //request for start time
        String start = getScanner().next();
        System.out.println("Enter End Time e.g 13:00");
        //request for end time
        String end = getScanner().next();
        if (day != null && !day.isEmpty() && start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
            //insert a new record if all fields are entered
            try (PreparedStatement st = getConnection().prepareStatement("insert into schedule (day_of_week,start_time,end_time,doctor_id) values(?,?,?,?)")) {
                st.setString(1, day);
                st.setString(2, start);
                st.setString(3, end);
                st.setInt(4, getUserId());
                int executeUpdate = st.executeUpdate();
                if (executeUpdate > 0) {
                    System.out.println("Schedule Created");
                    System.out.println("");
                } else {
                    System.out.println("Schedule Not Created. Check your inputs.");
                    System.out.println("");
                }
            } catch (Exception e) {
                //show error message
                System.out.println("Something Went wrong. Check your inputs.");
                System.out.println("");
            }
        } else {
            //show error message
            System.out.println("All fields are required!");
            System.out.println("");
        }
    }
}
