package kz.iitu.itse1908.murzaliev.service;

import kz.iitu.itse1908.murzaliev.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {
    private ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<kz.iitu.itse1908.murzaliev.database.Service> generateServices () {
        kz.iitu.itse1908.murzaliev.database.Service service = new kz.iitu.itse1908.murzaliev.database.Service();
        service.setServiceName("Kazahtelecom");
        service.setCommission(1.5);
        serviceRepository.save(service);

        service.setServiceName("Alma TV");
        service.setCommission(1.2);
        serviceRepository.save(service);

        service.setServiceName("Steam");
        service.setCommission(2.5);
        serviceRepository.save(service);

        service.setServiceName("Olimpet");
        service.setCommission(0.0);
        serviceRepository.save(service);

        return (List<kz.iitu.itse1908.murzaliev.database.Service>)serviceRepository.findAll();
    }

    public List<kz.iitu.itse1908.murzaliev.database.Service> getServiceList(){
        return (List<kz.iitu.itse1908.murzaliev.database.Service>)serviceRepository.findAll();
    }
}
