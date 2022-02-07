package Reposiroty;

import javax.ejb.Stateful;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Stateful
public class DownloadRepository {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    private Connection conn = null;
    private Statement stmt = null;

    public void Repository() throws ClassNotFoundException, SQLException {
        // STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);

        // STEP 2: Open a connection
        System.out.println("Connecting to a selected database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected database successfully...");

        // STEP 3: Execute a query
        stmt = conn.createStatement();

        stmt.executeUpdate(CreateDatabase.createDatabaseSQL());
        Statement finalStmt = stmt;
        CreateDatabase.loadData().forEach(sql -> {
            try {
                finalStmt.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

    }

    public List<String> getAllDemoName() throws SQLException {
        String sql = "select * from demo";
        ResultSet rs = stmt.executeQuery(sql);
        List<String> names = new ArrayList<>();
        while (rs.next()) {
            // Retrieve by column name
            String first = rs.getString("Name");

            names.add(first);
        }
        return names;
    }

    public DownloadRepository() {
    }
}
