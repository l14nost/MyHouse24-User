package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section,Long> {

    List<Section> findAllByHouse_Id(Long id);
}
