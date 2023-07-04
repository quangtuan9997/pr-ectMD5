package ra.service.serviceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.OrderDetail;
import ra.repository.IOrderDetailRepository;
import ra.service.serviceInter.IOrderDetailService;

import java.util.List;
import java.util.Optional;
@Service
public class OrderDetailServiceIMPL implements IOrderDetailService {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Override
    public Page<OrderDetail> findAll(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public boolean deleteById(Long id) {
        orderDetailRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public List<OrderDetail> findAllByOrder_Id(Long orderId) {
        return orderDetailRepository.findAllByOrder_Id(orderId);
    }

    @Override
    public List<OrderDetail> revenue(Long userid,int month) {
        return orderDetailRepository.revenue(userid,month);
    }

}
