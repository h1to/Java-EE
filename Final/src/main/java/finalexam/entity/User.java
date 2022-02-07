package finalexam.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Transactional
@Cacheable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull
    @Column(name = "user_age", nullable = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal userAge;

    @NotNull
    @Column(name = "last_session", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate lastSession;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="sale_id")
    private Sale sale;

    public User(){}

    public User(Long userId, String userName, BigDecimal userAge, LocalDate lastSession, Sale sale) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.lastSession = lastSession;
        this.sale = sale;
    }
}
