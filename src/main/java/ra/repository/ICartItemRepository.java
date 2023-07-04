package ra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ra.model.CartItem;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem,Long> {
    Page<CartItem> findAll(Pageable pageable);
    Optional<CartItem> findById(Long id);
    List<CartItem> findCartItemByCartId(Long cartId);

//    @Query("delete from CartItem where cart.id=:cartId")
//   void delByCartId(@Param("cartId") Long cartId);
//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true,value = "delete from cart_item where cart_id = ?1")
//    void deleteCartItemsByCartId(Long cartId);
    @Modifying
    @Transactional
    void deleteAllByCartId(Long idCard);
}
