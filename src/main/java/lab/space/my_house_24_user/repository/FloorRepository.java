package lab.space.my_house_24_user.repository;


import lab.space.my_house_24_user.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<Floor,Long> {
    List<Floor> findAllByHouse_Id(Long id);

}
