package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.SecurityLevel;
import lab.space.my_house_24_user.enums.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityLevelRepository extends JpaRepository<SecurityLevel, Long> {

    Optional<SecurityLevel> findByPage(Page page);
}
