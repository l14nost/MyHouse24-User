package lab.space.my_house_24_user.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lab.space.my_house_24_user.entity.Message;
import lab.space.my_house_24_user.model.message.MessageMainPageRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;


@Builder
@EqualsAndHashCode
public class MessageSpecification implements Specification<Message> {
    private MessageMainPageRequest mainPageRequest;
    private Long id;


    @Override
    public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String title = mainPageRequest.keyWord();
        if (title == null){
            title = "";
        }
        query.distinct(true);
        Predicate predicate = criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%"+title+"%"),
                criteriaBuilder.equal(root.get("users").get("id"), id));
        query.orderBy(criteriaBuilder.desc(root.get("id")));
        return predicate;


    }
}
