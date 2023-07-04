package ra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Long id);
    Page<Product> findProductByProductNameContaining(String proName, Pageable pageable);
    Page<Product> findProductsByStatus(int status,Pageable pageable);
    Page<Product> findProductsByUsersId(Long userId,Pageable pageable);
    Page<Product> findProductsByCateId(Long cateId,Pageable pageable);
    @Query (nativeQuery = true, value = "select p.* from product p join (select  od.proId id, sum(od.quantity) m from  order_detail od join orders o on o.id=od.orderid where o.status=0 group by od.proId) s on s.id=p.id order by s.m desc limit 5")
    List<Product> featuredProduct();

}
