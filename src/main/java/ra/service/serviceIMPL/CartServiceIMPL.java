package ra.service.serviceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.Cart;
import ra.repository.ICartRepository;
import ra.service.serviceInter.ICartService;

import java.util.Optional;

@Service
public class CartServiceIMPL implements ICartService {
    @Autowired
    private ICartRepository cartRepository;
    @Override
    public Page<Cart> findAll(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public boolean deleteById(Long id) {
        cartRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Optional<Cart> findByUsersId(Long userId) {
        return cartRepository.findByUsersId(userId);
    }
}
