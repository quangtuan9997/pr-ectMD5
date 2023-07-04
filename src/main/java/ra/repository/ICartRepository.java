package ra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.Cart;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart,Long> {
    Page<Cart> findAll(Pageable pageable);
    Optional<Cart> findById(Long id);
    Optional<Cart> findByUsersId(Long userId);
}
