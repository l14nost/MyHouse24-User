package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.Role;
import lab.space.my_house_24_user.enums.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByJobTitle(JobTitle jobTitle);
}
