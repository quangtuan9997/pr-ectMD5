package ra.service.serviceInter;

import ra.model.Cart;
import ra.service.IGenericService;

import java.util.Optional;

public interface ICartService extends IGenericService<Cart,Long> {
    Optional<Cart> findByUsersId(Long userId);
}
