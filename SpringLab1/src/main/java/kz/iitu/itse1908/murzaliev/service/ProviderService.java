package kz.iitu.itse1908.murzaliev.service;

import kz.iitu.itse1908.murzaliev.database.Payment;
import kz.iitu.itse1908.murzaliev.database.Provider;
import kz.iitu.itse1908.murzaliev.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {
    private ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public List<Provider> generateProviders () {
        Provider provider = new Provider();
        provider.setProviderName("Cyberplat");
        providerRepository.save(provider);

        provider.setProviderName("Kassa24");
        providerRepository.save(provider);

        return (List<Provider>) providerRepository.findAll();
    }

    public Boolean getStatusTransaction(Payment payment) {
        payment.setStatus(true);
        return true;
    }
}
