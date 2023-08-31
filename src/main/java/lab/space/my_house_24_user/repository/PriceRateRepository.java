package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.PriceRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRateRepository extends JpaRepository<PriceRate, Long> {
}
