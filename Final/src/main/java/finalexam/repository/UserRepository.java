package finalexam.repository;
import finalexam.entity.*;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Transactional(Transactional.TxType.SUPPORTS)
public class UserRepository {
    @EJB
    private SaleRepository saleRepository;

    private static final String jdbcURL  = "jdbc:h2:~/Database";
    private static final String jdbcUsername = "chain";
    private static final String jdbcPass = "";

    private static final String INSERT = "insert into user" +
            " (user_id, user_name,user_age, last_session, sale_id) VALUES " + "(?, ?, ?, ?, ?);";
    private static final String SELECT_ALL = "select * from user";
    private static final String SELECT_BY_ID =
            "select user_name, user_age, last_session, sale.sale_id, percentage FROM user, sale where user.sale_id = sale.sale_id and user_id = ?;";
    private static final String DELETE = "delete from user where user_id = ?;";
    private static final String UPDATE = "update user set user_name=?, user_age=?, last_session=?, sale_id = ? where user_id = ?;";

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
    public User insertUser(User user){
        boolean check = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            List<Sale> sales = saleRepository.getAllSales();
            Sale sale = sales.stream().filter(d -> d.getSaleId() == user.getSale().getSaleId()).findFirst().get();

            if(sale != null) {
                preparedStatement.setLong(1, user.getUserId());
                preparedStatement.setString(2, user.getUserName());
                preparedStatement.setBigDecimal(3, user.getUserAge());
                preparedStatement.setDate(5, Date.valueOf(user.getLastSession()));
                preparedStatement.setLong(6, user.getSale().getSaleId());
                user.setSale(sale);
            }
            check = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(check)
            return user;
        else return new User();
    }


    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public List<User> selectUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long userId = rs.getLong("user_id");
                String userName = rs.getString("user_name");
                BigDecimal user_age = rs.getBigDecimal("user_age");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate lastSession = LocalDate.parse
                        (rs.getDate("last_session").toLocalDate().format(formatter),formatter);
                long saleId = rs.getLong("sale_id");
                List<Sale> sales = saleRepository.getAllSales();
                Sale sale = sales.stream().filter(d -> d.getSaleId() == saleId).findFirst().get();
                users.add(new User(userId, userName, user_age, lastSession, sale));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }


    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public User selectUser(int userId)  {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("user_name");
                BigDecimal user_age = rs.getBigDecimal("user_age");
                LocalDate lastSession = rs.getDate("last_session").toLocalDate();
                long sale_id= rs.getLong("sale_id");
                int percentage = rs.getInt("percentage");
                Sale sale = new Sale(sale_id, percentage);
                user = new User((long) userId, userName, user_age, lastSession, sale);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }


    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public boolean deleteUser(int id) {
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
    public boolean updateUser(User user){
        boolean updated = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, user.getUserName());
            statement.setBigDecimal(2, user.getUserAge());
            statement.setDate(3, Date.valueOf(user.getLastSession()));
            statement.setLong(4, user.getSale().getSaleId());
            statement.setLong(5, user.getUserId());
            updated = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }
}
