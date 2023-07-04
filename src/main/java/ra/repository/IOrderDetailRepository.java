package ra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ra.model.OrderDetail;

import java.util.List;
import java.util.Optional;
@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    Page<OrderDetail> findAll(Pageable pageable);
    Optional<OrderDetail> findById(Long id);
    List<OrderDetail> findAllByOrder_Id(Long orderId);
    @Query(nativeQuery = true,value = "select * from order_detail od join orders  o on od.orderid=o.id\n" +
            "join product p on od.proId=p.id where p.userid=:userid and o.status=1 and month(o.createAt)=:month")
    List<OrderDetail> revenue(@Param("userid")Long userid, @Param("month")int month);
}
