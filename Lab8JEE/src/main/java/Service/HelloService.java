package Service;

import Reposiroty.DownloadRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class HelloService {
    public HelloService() {
    }

    @EJB
    private DownloadRepository repository;

    public HelloService(DownloadRepository repository) {
        this.repository = repository;
    }

    public List<String> getHello() throws SQLException, ClassNotFoundException {
        repository.Repository();
        return repository.getAllDemoName();
    }
}
