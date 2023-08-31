package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House,Long>, JpaSpecificationExecutor<House> {
}
