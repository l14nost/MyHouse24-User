package lab.space.my_house_24_user.repository;

import lab.space.my_house_24_user.entity.BankBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankBookRepository extends JpaRepository<BankBook,Long> {
    List<BankBook> findAllByApartmentIsNull();

    boolean existsByNumber(String number);

    Optional<BankBook> findBankBookByNumber(String number);
}
