package lab.space.my_house_24_user.entity;

import jakarta.persistence.*;
import lab.space.my_house_24_user.enums.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ArticleType type;

    @OneToMany(mappedBy = "articles")
    private List<CashBox> cashBoxList = new ArrayList<>();

}
