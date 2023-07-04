package ra.service.serviceInter;

import ra.model.CartItem;
import ra.service.IGenericService;

import java.util.List;

public interface ICartItemService extends IGenericService<CartItem,Long> {
    List<CartItem> findCartItemByCartId(Long cartId);
    void deleteByCartId(Long cartId);
}
