package finalexam.repository;

import finalexam.entity.Sale;

import javax.ejb.Singleton;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Transactional(Transactional.TxType.SUPPORTS)
public class SaleRepository {

    private static final String jdbcURL  = "jdbc:h2:~/Database";
    private static final String jdbcUsername = "chain";
    private static final String jdbcPass = "";

    private static final String INSERT = "insert into sale" +
            " (sale_id, percentage) VALUES (?, ?);";
    private static final String SELECT_ALL = "select * from sale";
    private static final String SELECT_BY_ID = "select sale_id, percentage from sale where sale_id=?";
    private static final String DELETE = "delete from sale where sale_id = ?;";
    private static final String UPDATE = "update sale set percentage=? where sale_id = ?;";

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
    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long discountId = rs.getLong("sale_id");
                int percentage = rs.getInt("percentage");
                sales.add(new Sale(discountId, percentage));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sales;
    }
    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public Sale addSale(Sale sale) {
        boolean check = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setLong(1, sale.getSaleId());
            preparedStatement.setInt(2, sale.getPercentage());
            check = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(check)
            return sale;
        else return new Sale();
    }

    public Sale getSaleById(int SaleId) {
        Sale sale = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, SaleId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int percentage = rs.getInt("percentage");
                sale = new Sale((long) SaleId, percentage);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sale;
    }
    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public boolean deleteSaleById(int id) {
        boolean deleted = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE);) {
            statement.setLong(1, id);
            deleted = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public boolean updateSale(Sale sale) {
        boolean updated = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setInt(1, sale.getPercentage());


            updated = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }
}
