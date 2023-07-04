package ra.service.serviceInter;

import org.springframework.data.repository.query.Param;
import ra.model.OrderDetail;
import ra.service.IGenericService;

import java.util.List;

public interface IOrderDetailService extends IGenericService<OrderDetail,Long> {
    List<OrderDetail> findAllByOrder_Id(Long orderId);
    List<OrderDetail> revenue(Long userId,int month);
}
