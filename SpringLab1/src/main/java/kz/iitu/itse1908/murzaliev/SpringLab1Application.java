package kz.iitu.itse1908.murzaliev;

import kz.iitu.itse1908.murzaliev.database.Provider;
import kz.iitu.itse1908.murzaliev.database.Service;
import kz.iitu.itse1908.murzaliev.repository.ServiceRepository;
import kz.iitu.itse1908.murzaliev.service.PaymentService;
import kz.iitu.itse1908.murzaliev.service.ProviderService;
import kz.iitu.itse1908.murzaliev.service.ServiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@EntityScan("kz.iitu.itse1908.murzaliev.database")
@SpringBootApplication
public class SpringLab1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringLab1Application.class, args);
    }

    CommandLineRunner commandLineRunner(PaymentService paymentService, ServiceService serviceService, ProviderService providerService) {
        return args -> {
            List<Service> services = serviceService.generateServices();
            List<Provider> providers = providerService.generateProviders();
            paymentService.generatePayment(services, providers);


            providerService.getStatusTransaction(paymentService.sendPayment(services.get(1), providers.get(1)));
            serviceService.getServiceList().forEach(s -> {
                System.out.println(s.toString());
            });
        };
    }
}
