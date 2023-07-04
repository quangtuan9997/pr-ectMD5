package ra.service.serviceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.Orders;
import ra.repository.IOrderRepository;
import ra.service.serviceInter.IOrderService;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceIMPL implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Override
    public Page<Orders> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public boolean deleteById(Long id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return orderRepository.findById(id);
    }


    @Override
    public Page<Orders> findOrdersByStatus(Boolean status,Pageable pageable) {
        return orderRepository.findOrdersByStatus(status ,pageable);
    }
}
