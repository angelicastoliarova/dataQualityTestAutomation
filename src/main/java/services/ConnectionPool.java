package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final ConnectionPool instance = new ConnectionPool();

    private final BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(5);

    private ConnectionPool() {

        Connection con = null;

        try {
            java.sql.DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("");
        } catch (SQLException e) {

            e.printStackTrace();
        }

        try {

            for (int i = 1; i <= pool.remainingCapacity(); i++) {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/notebook?useSSL=false", "root",
                        "admin");
                pool.add(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws InterruptedException {
        return pool.take();
    }

    public void returnConnection(Connection connection) throws SQLException, InterruptedException {
        if (connection == null) {
            return;
        }
        connection.setAutoCommit(true);
        connection.setReadOnly(true);

        pool.put(connection);
    }

    public void closePool() {

        for (Connection con : pool) {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }

    }

    public static ConnectionPool getInstance() {
        return instance;
    }
}

