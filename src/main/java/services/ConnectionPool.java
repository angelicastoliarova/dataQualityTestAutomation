package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "admin";
    private static final ConnectionPool instance = new ConnectionPool();

    private final BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(5);

    private ConnectionPool() {

        Connection con = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        try {
            con = DriverManager
                    .getConnection(DB_URL, USER, PASS);
            pool.add(con);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
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

