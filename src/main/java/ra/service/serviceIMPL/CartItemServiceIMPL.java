package ra.service.serviceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.CartItem;
import ra.repository.ICartItemRepository;
import ra.service.serviceInter.ICartItemService;

import java.util.List;
import java.util.Optional;
@Service
public class CartItemServiceIMPL implements ICartItemService {
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Override
    public Page<CartItem> findAll(Pageable pageable) {
        return cartItemRepository.findAll(pageable);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public boolean deleteById(Long id) {
        cartItemRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public List<CartItem> findCartItemByCartId(Long cartId) {
        return cartItemRepository.findCartItemByCartId(cartId);
    }

    @Override
    public void deleteByCartId(Long cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
    }
}
