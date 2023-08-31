package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long>, JpaSpecificationExecutor<MeterReading> {
    List<MeterReading> findAllByApartment_IdAndService_id(Long idApartment, Long idService);
    List<MeterReading> findAllByApartment_Id(Long idApartment);
}
