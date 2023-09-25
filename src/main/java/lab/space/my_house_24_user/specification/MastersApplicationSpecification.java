package lab.space.my_house_24_user.specification;

import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24_user.entity.MastersApplication;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Controller
public class MastersApplicationSpecification {
    public Specification<MastersApplication> getMastersApplicationByUserEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(email) && !email.equals("")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("user").get("email"), email)
                ));
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
