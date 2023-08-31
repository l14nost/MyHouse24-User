package lab.space.my_house_24_user.entity;

import jakarta.persistence.*;
import lab.space.my_house_24_user.enums.BankBookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "bank_book")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private BankBookStatus bankBookStatus;

    @OneToOne
    private Apartment apartment;
}
