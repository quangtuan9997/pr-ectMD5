package ra.service.serviceInter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.Orders;
import ra.service.IGenericService;

import java.util.List;

public interface IOrderService extends IGenericService<Orders, Long> {
    Page<Orders> findOrdersByStatus(Boolean status, Pageable pageable);
}
