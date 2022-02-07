package kz.iitu.itse1908.murzaliev.database;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Payment {
    @Id
    @Column(name = "payment_id", nullable = false, insertable = false, updatable = false)
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    @NonNull
    @JoinColumn(name = "service_id")
    @ManyToOne
    private Service serviceId;

    @NonNull
    @JoinColumn(name = "provider_id")
    @ManyToOne
    private Provider providerId;

    @NonNull
    @Column(name = "date", nullable = false)
    private Date date;

    @NonNull
    @Column(name = "sum_in", nullable = false)
    private BigDecimal sum_in;

    @NonNull
    @Column(name = "sum", nullable = false)
    private BigDecimal sum;

    @NonNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @Autowired
    public void setServiceId(Service serviceId) {
        this.serviceId = serviceId;
    }

    @Autowired
    public void setProviderId(Provider providerId) {
        this.providerId = providerId;
    }
}
