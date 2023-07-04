package ra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.Orders;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Orders,Long> {
    Page<Orders> findAll(Pageable pageable);

    Optional<Orders> findById(Long id);
    Page<Orders> findOrdersByStatus(Boolean status,Pageable pageable);
}
