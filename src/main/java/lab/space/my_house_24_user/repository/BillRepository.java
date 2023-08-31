package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.Bill;
import lab.space.my_house_24_user.enums.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {
    List<Bill> findAllByStatus(BillStatus status);
}
