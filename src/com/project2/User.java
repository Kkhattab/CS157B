package com.project2;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public abstract class User {

    private final SimpleDateFormat sdf;
    private final Connection connection;
    private final int userId;
    private final Scanner scanner;

    /**
     * @param connection
     * @param userId     this is either patients id or doctors id
     */
    public User(Connection connection, int userId) {
        this.sdf = new SimpleDateFormat("HH:mm");
        this.scanner = new Scanner(System.in);
        this.connection = connection;
        this.userId = userId;
    }

    /**
     * loop with users functionalities
     *
     * @throws SQLException
     */
    public abstract void execute() throws SQLException;

    /**
     * @return the sdf
     */
    public SimpleDateFormat getSdf() {
        return sdf;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the scanner
     */
    public Scanner getScanner() {
        return scanner;
    }
}
