package finalexam.repository;

import finalexam.entity.Item;

import javax.ejb.Singleton;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Transactional(Transactional.TxType.SUPPORTS)
public class ItemRepository {
    private static final String jdbcURL  = "jdbc:h2:~/Database";
    private static final String jdbcUsername = "chain";
    private static final String jdbcPass = "";

    private static final String INSERT = "insert into item" +
            " (item_id, item_name, item_price) VALUES (?, ?, ?);";
    private static final String SELECT_ALL = "select * from item";
    private static final String SELECT_BY_ID = "select item_id, item_name, item_price from item where item_id = ?";
    private static final String DELETE = "delete from item where item_id = ?;";
    private static final String UPDATE = "update item set item_name=?, item_price=? where status_id = ?;";

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
    public List<Item> selectItems() {
        List<Item> Items = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long item_id = rs.getLong("item_id");
                String name = rs.getString("item_name");
                BigDecimal price = new BigDecimal(rs.getString("item_price"));
                Items.add(new Item(item_id, name, price));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Items;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public Item insertItem(Item item) {
        boolean check = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setLong(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setBigDecimal(3,item.getPrice());
            check = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(check)
            return item;
        else return new Item();
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public Item selectItem(int itemId) {
        Item item = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, itemId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("item_name");
                BigDecimal price = rs.getBigDecimal("item_price");
                item = new Item((long) itemId, name, price);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return item;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public boolean deleteItem(int id) {
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
    public boolean updateItem(Item item) {
        boolean updated = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE);) {
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setLong(3, item.getId());

            updated = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }
}
