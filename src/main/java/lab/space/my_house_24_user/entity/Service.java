package lab.space.my_house_24_user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne
    private Unit unit;

    @OneToMany
    private List<MeterReading> meterReadingList = new ArrayList<>();

    @OneToMany(mappedBy = "service")
    private List<ServiceBill> serviceBillList = new ArrayList<>();

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                ", unit=" + (unit != null ? unit.getId() : "null") +
                '}';
    }

}
