package finalexam.repository;

import finalexam.entity.*;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Transactional(Transactional.TxType.SUPPORTS)
public class ProductRepository {
    @Inject
    private UserRepository userRepository;
    @Inject
    private StatusRepository statusRepository;
    @Inject
    private ItemRepository itemRepository;
    private static final String jdbcURL  = "jdbc:h2:~/Database";
    private static final String jdbcUsername = "chain";
    private static final String jdbcPass = "";

    private static final String INSERT = "insert into product" +
            " (prod_id, user_id, specifications, status_id, prod_price) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_ALL = "select * from product";
    private static final String SELECT_BY_ID = "select * from product where prod_id = ?";
    private static final String DELETE = "delete from product where prod_id = ?;";
    private static final String UPDATE = "update product set prod_id=?, user_id=?, specifications=?, status_id=?, prod_price=? where prod_id = ?;";

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
    public List<Product> selectProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("prod_id");
                String specifications = rs.getString("specifications");
                BigDecimal price = new BigDecimal(rs.getString("prod_price"));

                int userId = rs.getInt("user_id");
                List<User> users = userRepository.selectUsers();
                User user = users.stream().filter(u -> u.getUserId() == userId).findFirst().get();

                int statusId = rs.getInt("status_id");
                List<Status> statuses = statusRepository.selectStatuses();
                Status status = statuses.stream().filter(s -> s.getId() == statusId).findFirst().get();

                PreparedStatement preparedStatement1 = connection.prepareStatement("select * from product_item where PRODUCT_PROD_ID = " + id);
                ResultSet resultSet = preparedStatement1.executeQuery();

                ArrayList<Long> itemIds = new ArrayList<Long>();
                while(resultSet.next()) {
                    itemIds.add(resultSet.getLong("ITEMS_ITEM_ID"));
                }
                List<Item> items = itemRepository.selectItems();
                ArrayList<Item> productItems= new ArrayList<>();
                for (int k = 0; k < itemIds.size(); k++) {
                    long idd = itemIds.get(k);
                    productItems.add(items.stream().filter(i -> i.getId() == idd).findFirst().get());
                }

                products.add(new Product(id, user, productItems, specifications, status, price));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public Product insertProduct(Product product) {
        boolean check = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setLong(1, product.getId());
            preparedStatement.setLong(2, product.getCustomer().getUserId());
            preparedStatement.setString(3,product.getSpecifications());
            preparedStatement.setLong(4, product.getProductStatus().getId());
            preparedStatement.setBigDecimal(5, product.getPrice());
            check = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(check)
            return product;
        else return new Product();
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public BigDecimal getProductPrice (Product product) {
        List<Item> items = product.getItems();
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < items.size(); i++) {
            sum = sum.add(items.get(i).getPrice());
        }
        BigDecimal oneP = sum.divide(new BigDecimal("100"));
        BigDecimal sub = oneP.multiply(new BigDecimal(product.getCustomer().getSale().getPercentage()));
        BigDecimal price = sum.subtract(sub);
        return price;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public Product selectProduct(int prodId) {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, prodId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String specification = rs.getString("specifications");
                BigDecimal price = rs.getBigDecimal("prod_price");
                long id = rs.getLong("prod_id");
                int userId = rs.getInt("user_id");
                User user = userRepository.selectUser(userId);
                int statusId = rs.getInt("status_id");
                Status status = statusRepository.selectStatus(statusId);

                PreparedStatement preparedStatement1 = connection.prepareStatement("select * from product_item where PRODUCT_PROD_ID = " + id);
                ResultSet resultSet = preparedStatement1.executeQuery();

                ArrayList<Long> itemIds = new ArrayList<Long>();
                while(resultSet.next()) {
                    itemIds.add(resultSet.getLong("ITEMS_ITEM_ID"));
                }
                List<Item> items = itemRepository.selectItems();
                ArrayList<Item> productItems= new ArrayList<>();
                for (int k = 0; k < itemIds.size(); k++) {
                    long idd = itemIds.get(k);
                    productItems.add(items.stream().filter(i -> i.getId() == idd).findFirst().get());
                }

                product = new Product(id, user, productItems, specification, status, price);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }

    @Transactional(rollbackOn = SQLException.class,
            dontRollbackOn = EntityExistsException.class)
    public boolean deleteProduct(int id) {
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
    public boolean updateProduct(Product product) {
        boolean updated = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE);) {
            statement.setLong(1, product.getId());
            statement.setLong(2, product.getCustomer().getUserId());
            statement.setString(3, product.getSpecifications());
            statement.setLong(4, product.getProductStatus().getId());
            statement.setBigDecimal(5, product.getPrice());
            statement.setLong(6, product.getId());

            updated = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    public String addItem (long productId, long itemid) {
        boolean added = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("insert into product_item values(?, ?);");) {
            statement.setLong(1, productId);
            statement.setLong(2, itemid);

            added = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (added) {
            return "Added to product ID = " + productId;
        }
        else {
            return "Not added!";
        }
    }

}
