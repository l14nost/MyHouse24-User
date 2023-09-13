package lab.space.my_house_24_user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "cash_box")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(length = 20, nullable = false)
    private String number;

    @Column(nullable = false)
    private Boolean draft;

    @Column(nullable = false)
    private Boolean type;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal historyMoneyUsed;

    @Column(nullable = false)
    private BigDecimal moneyUsed;

    @Column(length = 1000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article articles;

    @ManyToOne
    private BankBook bankBook;

}
