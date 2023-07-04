package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.response.ResponseMessage;
import ra.model.Cart;
import ra.model.Users;
import ra.security.userPrincipal.UserDetailService;
import ra.service.serviceInter.ICartService;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/cart")
@CrossOrigin("*")
public class CartController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private UserDetailService userDetailService;
    @PostMapping("/create")
    public ResponseEntity<?> createCart(){
       Users user=userDetailService.getUserFromAuthentication();
        Optional<Cart> c=cartService.findByUsersId(user.getId());
        if (c.isPresent()){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Failed","cart already available",c));
        }
        Cart cart=new Cart();
        cart.setUsers(user);
        return new  ResponseEntity<>(cartService.save(cart), HttpStatus.CREATED);
    }

}
