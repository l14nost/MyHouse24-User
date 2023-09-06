package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findUserByEmail(String email);

    Long countByUserStatus(UserStatus status);

    Boolean existsByEmail(String email);
}
