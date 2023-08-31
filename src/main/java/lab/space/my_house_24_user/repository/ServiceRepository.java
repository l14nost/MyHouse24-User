package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>, JpaSpecificationExecutor<Service> {
    boolean existsByName(String name);

    List<Service> findAllByIsActiveOrderById(Boolean isActive);
}
