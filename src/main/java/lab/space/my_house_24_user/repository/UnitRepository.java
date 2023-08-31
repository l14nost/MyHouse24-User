package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    boolean existsByName(String name);
}
