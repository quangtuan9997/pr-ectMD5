package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.Cart;
import ra.model.CartItem;
import ra.model.Product;
import ra.security.userPrincipal.UserDetailService;
import ra.service.serviceInter.ICartItemService;
import ra.service.serviceInter.ICartService;
import ra.service.serviceInter.IProductService;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/cartitem")
public class CartItemController {
    @Autowired
   private ICartItemService cartItemService;
    @Autowired
   private IProductService productService;
    @Autowired
   private UserDetailService userDetailService;
    @Autowired
    private ICartService cartService;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createItem(@RequestBody CartItem cartItem) {
       Optional<Product> pro=productService.findById(cartItem.getPro().getId());
       if (pro.get().getStatus()==2){
        Long userId=userDetailService.getUserFromAuthentication().getId();
        Optional<Cart> cart=cartService.findByUsersId(userId);
            List<CartItem> listc = cartItemService.findCartItemByCartId(cart.get().getId());
            for (CartItem c : listc) {
                if (c.getPro().getId() == cartItem.getPro().getId()) {
                    cartItem.setId(c.getId());
                    cartItem.setPrice(c.getPrice());
                    cartItem.setQuantity(cartItem.getQuantity() + c.getQuantity());
                    cartItem.setCart(cart.get());
                    return new ResponseEntity<>(cartItemService.save(cartItem), HttpStatus.OK);
                }
            }
            float price=productService.findById(cartItem.getPro().getId()).get().getPrice();
            cartItem.setPrice(price);
           cartItem.setCart(cart.get());
            return new ResponseEntity<>(cartItemService.save(cartItem), HttpStatus.CREATED);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/up")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> update(@RequestBody CartItem cartItem){
        Optional<CartItem> c=cartItemService.findById(cartItem.getId());
        if (c.isPresent()){
            c.get().setQuantity(cartItem.getQuantity());

            return new ResponseEntity<>(cartItemService.save(c.get()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteItem(@PathVariable("id") Long id){
        cartItemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/findAllByCartId/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> findByCartId(@PathVariable("id")Long cartId){
        List<CartItem> cartItemList=cartItemService.findCartItemByCartId(cartId);
        if (!cartItemList.isEmpty()){
            return new ResponseEntity<>(cartItemList,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/delAllByCartId/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteAllByCartId(@PathVariable("id") Long cartId){
        cartItemService.deleteByCartId(cartId);
      return new ResponseEntity<>(HttpStatus.OK);
    }
}
