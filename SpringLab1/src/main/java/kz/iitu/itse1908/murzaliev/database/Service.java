package kz.iitu.itse1908.murzaliev.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @Column(name = "service_id", nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private Long serviceId;

    @Column(name = "service_name")
    @NonNull
    private String serviceName;

    @Column(name = "commission", nullable = false)
    private Double commission;
}
