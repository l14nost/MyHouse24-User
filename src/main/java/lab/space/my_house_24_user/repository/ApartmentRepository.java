package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment,Long>, JpaSpecificationExecutor<Apartment> {
    List<Apartment> findAllByNumberAndHouseId(Integer number, Long id);

    List<Apartment> findAllByHouse_Id(Long id);
    List<Apartment> findAllBySection_Id(Long id);
    List<Apartment> findAllByFloor_Id(Long id);

}
