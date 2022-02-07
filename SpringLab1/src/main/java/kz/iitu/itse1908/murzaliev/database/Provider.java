package kz.iitu.itse1908.murzaliev.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "provider_id", nullable = false, insertable = false, updatable = false)
    private Long providerId;

    @NonNull
    @Column(name = "provider_name", nullable = false)
    private String providerName;
}
