package kz.iitu.itse1908.murzaliev.service;

import kz.iitu.itse1908.murzaliev.database.Payment;
import kz.iitu.itse1908.murzaliev.database.Provider;
import kz.iitu.itse1908.murzaliev.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void generatePayment(List<kz.iitu.itse1908.murzaliev.database.Service> services, List<Provider> providers) {
        Payment payment = new Payment();
        payment.setSum_in(new BigDecimal("20000"));
        payment.setSum(new BigDecimal("20000"));
        payment.setStatus(true);
        payment.setDate(new Date());
        payment.setServiceId(services.get(3));
        payment.setProviderId(providers.get(1));
        paymentRepository.save(payment);
    }

    public Payment sendPayment(kz.iitu.itse1908.murzaliev.database.Service service, Provider provider) {
        Payment payment = new Payment();
        payment.setSum_in(new BigDecimal("20000"));
        payment.setSum(new BigDecimal("20000"));
        payment.setStatus(false);
        payment.setDate(new Date());
        payment.setServiceId(service);
        payment.setProviderId(provider);
        return payment;
    }
}
