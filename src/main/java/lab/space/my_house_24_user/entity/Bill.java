package lab.space.my_house_24_user.entity;

import jakarta.persistence.*;
import lab.space.my_house_24_user.enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String number;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private BillStatus status;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    private Boolean isActive;

    @Column(nullable = false)
    private Boolean autoPayed;

    @Column(nullable = false)
    private BigDecimal payedCashBox;

    @Column(nullable = false)
    private BigDecimal historyPayedCashBox;

    @Column(nullable = false)
    private BigDecimal payed;

    @Column(nullable = false)
    private Boolean draft;

    @ManyToOne
    @JoinColumn(name = "bank_book_id", nullable = false)
    private BankBook bankBook;

    @Column(nullable = false)
    private LocalDateTime periodOf;

    @Column(nullable = false)
    private LocalDateTime periodTo;

    @ManyToOne
    private Rate rate;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    List<ServiceBill> serviceBillList = new ArrayList<>();

}
