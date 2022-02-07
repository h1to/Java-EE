package finalexam.service;

import finalexam.AOP.*;
import finalexam.entity.*;
import finalexam.entity.Sale;
import finalexam.repository.*;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jms.*;
import java.util.List;

@Stateless
public class Services {
    @EJB
    private SaleRepository saleRepository;
    @EJB
    private UserRepository userRepository;
    @EJB
    private ItemRepository itemRepository;
    @EJB
    private StatusRepository statusRepository;
    @EJB
    private ProductRepository productRepository;

    @Resource(name = "messageQueue")
    private Queue messageQueue;
    @Resource(name = "agentQueue")
    private Queue chainQueue;
    @Resource(name = "commonQueue")
    private Queue commonQueue;
    @Resource
    private ConnectionFactory connectionFactory;


    @Interceptors(DiscountBefore.class)
    public List<Sale> getAllSale() {
        return saleRepository.getAllSales();
    }
    @Interceptors(DiscountBefore.class)
    public Sale addSale(Sale sale) {
        return saleRepository.addSale(sale);
    }
    public Sale getSaleById(int saleId) {
        return saleRepository.getSaleById(saleId);
    }
    @Interceptors(DiscountBefore.class)
    public boolean deleteSaleById(int id) {
        return saleRepository.deleteSaleById(id);
    }
    public boolean updateSale(Sale sale) {
        return saleRepository.updateSale(sale);
    }


    public User insertUser(User user){
        return userRepository.insertUser(user);
    }
    public List<User> selectUsers() {
        return userRepository.selectUsers();
    }
    public User selectUser(int userId)  {
        return userRepository.selectUser(userId);
    }
    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }
    public boolean updateUser(User user){
        return userRepository.updateUser(user);
    }


    public Item insertItem(Item item) {
        return itemRepository.insertItem(item);
    }
    public List<Item> selectItems () {
        return itemRepository.selectItems();
    }
    public Item selectItem (int id) {
        return itemRepository.selectItem(id);
    }
    public boolean deleteItem(int id) {
        return itemRepository.deleteItem(id);
    }
    public boolean updateItem (Item item) {
        return itemRepository.updateItem(item);
    }


    public Status insertStatus(Status status) {
        return statusRepository.insertStatus(status);
    }
    public List<Status> selectStatuses () {
        return statusRepository.selectStatuses();
    }
    public Status selectStatus (int id) {
        return statusRepository.selectStatus(id);
    }
    public boolean deleteStatus(int id) {
        return statusRepository.deleteStatus(id);
    }
    public boolean updateStatus (Status status) {
        return statusRepository.updateStatus(status);
    }

    public Product insertProduct(Product product) {
        return productRepository.insertProduct(product);
    }
    public List<Product> selectProducts () {
        return productRepository.selectProducts();
    }
    public Product selectProduct (int id) {
        return productRepository.selectProduct(id);
    }
    public boolean deleteProduct (int id) {
        return productRepository.deleteProduct(id);
    }
    public boolean updateProduct (Product product) {
        return productRepository.updateProduct(product);
    }
    public String addItem (long prodId, long itemId) {
        return productRepository.addItem(prodId, itemId);
    }

    public String sendJmsMessage(String message) {
        try (final Connection connection = connectionFactory.createConnection();
             final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
             final MessageProducer producer = session.createProducer(messageQueue)) {
            connection.start();
            final Message jmsMessage = session.createTextMessage(message);
            producer.send(jmsMessage);
            return "successfylly sended";
        } catch (final Exception e) {
            throw new RuntimeException("Caught exception from JMS when sending a message", e);
        }
    }

    public String getMessage() {
        try (final Connection connection = connectionFactory.createConnection();
             final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
             final MessageConsumer messageConsumer = session.createConsumer(messageQueue)) {

            connection.start();

            final Message jmsMessage = messageConsumer.receive(1000);

            if (jmsMessage == null) {
                return "jmsMessage is null";
            }

            TextMessage textMessage = (TextMessage) jmsMessage;
            if (textMessage == null) {
                return "Empty textMessage";
            }
            return textMessage.getText();
        } catch (final Exception e) {
            throw new RuntimeException("Caught exception from JMS when receiving a message", e);
        }
    }
}
