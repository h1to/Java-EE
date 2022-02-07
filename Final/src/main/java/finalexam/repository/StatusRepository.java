package finalexam.repository;

import finalexam.entity.Status;

import javax.ejb.Singleton;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Transactional(Transactional.TxType.SUPPORTS)
public class StatusRepository {

    private static final String jdbcURL  = "jdbc:h2:~/Database";
    private static final String jdbcUsername = "chain";
    private static final String jdbcPass = "";

    private static final String INSERT = "insert into status" +
            " (status_id, status) VALUES (?, ?);";
    private static final String SELECT_ALL = "select * from status";
    private static final String SELECT_BY_ID = "select * from status where status_id=?";
    private static final String DELETE = "delete from status where status_id = ?;";
    private static final String UPDATE = "update status set status=? where status_id = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPass);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public List<Status> selectStatuses() {
        List<Status> Statuses = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long statusId = rs.getLong("status_id");
                String status = rs.getString("status");
                Statuses.add(new Status(statusId, status));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Statuses;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public Status insertStatus(Status status) {
        boolean check = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setLong(1, status.getId());
            preparedStatement.setString(2, status.getStatus());
            check = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(check)
            return status;
        else return new Status();
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public Status selectStatus(int statusId) {
        Status status = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, statusId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("status_id");
                String stat = rs.getString("status");
                status = new Status((long) id, stat);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public boolean deleteStatus(int id) {
        boolean deleted = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            deleted = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public boolean updateStatus(Status status) {
        boolean updated = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, status.getStatus());
            statement.setLong(2, status.getId());

            updated = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }
}
