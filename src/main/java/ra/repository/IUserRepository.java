package ra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.Category;
import ra.model.Product;
import ra.model.Users;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<Users ,Long> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Optional<Users> findByUserName(String userName);
      Page<Users> findAll(Pageable pageable);
    Optional<Users> findById(Long id);

}
