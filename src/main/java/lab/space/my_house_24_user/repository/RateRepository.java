package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate,Long>, JpaSpecificationExecutor<Rate> {
    boolean existsByName(String name);
}
