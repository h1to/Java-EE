package finalexam.database;

import finalexam.entity.*;
import finalexam.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FillDB {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Database");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        List<Sale> sales = new ArrayList<>();
        for(int i=1; i<10; i++) {
            Sale sale = new Sale();
            sale.setPercentage(i*10);
            sales.add(sale);
            entityManager.persist(sale);
        }

        List<User> userList = new ArrayList<>();
        for(int i = 0; i< sales.size(); i++) {
            User user = new User();
            user.setUserName("User " + (i+1));
            user.setUserAge(BigDecimal.valueOf(20+i));
            user.setSale(sales.get(i));
            user.setLastSession(LocalDate.of(2021, 1, i+20));
            userList.add(user);
            entityManager.persist(user);
        }

        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            Item item = new Item();
            item.setName("Item " + (i + 1));
            item.setPrice(new BigDecimal((i+1)*100));
            items.add(item);
            entityManager.persist(item);
        }

        Status status = new Status();
        status.setStatus("Newly launched");
        entityManager.persist(status);

        Status status2 = new Status();
        status2.setStatus("Manufactured");
        entityManager.persist(status2);

        Status status3 = new Status();
        status3.setStatus("On delivery");
        entityManager.persist(status3);

        List<Status> statuses = new ArrayList<>();
        statuses.add(status);
        statuses.add(status2);
        statuses.add(status3);

        int statusIndex = 0;
        for (int i = 0; i < 5; i++) {
            Product product = new Product();
            product.setCustomer(userList.get(i));
            if (statusIndex > 2) {
                statusIndex = 0;
            }
            product.setProductStatus(statuses.get(statusIndex));
            statusIndex++;
            product.setSpecifications("Specification " + (i+1));

            ArrayList<Item> items2 = new ArrayList<>();
            for (int k = 0; k < i+1; k++) {
                items2.add(items.get(i+k));
            }
            product.setItems(items2);
            product.setPrice(new ProductRepository().getProductPrice(product));
            entityManager.persist(product);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
