package lab.space.my_house_24_user.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lab.space.my_house_24_user.entity.Bill;
import lab.space.my_house_24_user.model.bill.BillRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Controller
public class BillSpecification {
    public Specification<Bill> getBillByRequest(BillRequest request, String email) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(request.apartmentIdQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("bankBook").get("apartment").get("id"), request.apartmentIdQuery())
                ));
            }
            if (nonNull(request.numberQuery()) && !Objects.equals(request.numberQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("number"), "%" + request.numberQuery() + "%")
                ));
            }
            if (nonNull(request.dateQuery()) && !Objects.equals(request.dateQuery(), "")) {
                Expression<String> formattedDateTime = criteriaBuilder.function(
                        "DATE_FORMAT",
                        String.class,
                        root.get("createAt"),
                        criteriaBuilder.literal("%d.%m.%Y")
                );
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(formattedDateTime, request.dateQuery())
                ));
            }
            if (nonNull(request.statusQuery())) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("status"), request.statusQuery())
                ));
            }
            if (nonNull(request.payedQuery()) && !Objects.equals(request.payedQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("payed").as(String.class), "%" + request.payedQuery() + "%")
                ));
            }
            if (nonNull(request.priceQuery()) && !Objects.equals(request.priceQuery(), "")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("totalPrice").as(String.class), "%" + request.priceQuery() + "%")
                ));
            }
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("bankBook").get("apartment").get("user").get("email"), email)
            ));
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("draft"), true)
            ));
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
