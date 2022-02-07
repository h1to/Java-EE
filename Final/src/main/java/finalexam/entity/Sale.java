package finalexam.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Transactional
@Cacheable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sale {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sale_id", nullable = false)
    private Long saleId;

    @NotNull
    @Column(name = "percentage", nullable = false)
    private int percentage;

    public Sale(){}

    public Sale(Long saleId, int percentage) {
        this.saleId = saleId;
        this.percentage = percentage;
    }
}
