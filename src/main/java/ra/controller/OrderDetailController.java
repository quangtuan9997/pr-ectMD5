package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.CartItem;
import ra.model.OrderDetail;
import ra.model.Orders;
import ra.security.userPrincipal.UserDetailService;
import ra.service.serviceInter.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RequestMapping("/api/auth/orderDetail")
@RestController
public class OrderDetailController {
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICartItemService cartItemService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private UserDetailService userDetailService;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    private ResponseEntity<?> createItem(){
        Long userId= userDetailService.getUserFromAuthentication().getId();
        Orders or=new Orders();
        or.setStatus(false);
        or.setUsers(userService.findById(userId).get());
        or.setCreateAt(String.valueOf(java.time.LocalDate.now()));
        Orders ors= orderService.save(or);
        List<CartItem> orderDetail=cartItemService.findCartItemByCartId(cartService.findByUsersId(userId).get().getId());
        for (CartItem o: orderDetail){
            OrderDetail od=new OrderDetail();
            od.setQuantity(o.getQuantity());
            od.setPrice(o.getPrice());
            od.setPro(o.getPro());
            od.setOrder(ors);
            orderDetailService.save(od);
            or.setTotal(or.getTotal()+ o.getPrice()*o.getQuantity());
        }
        cartItemService.deleteByCartId(orderDetail.get(0).getCart().getId());

        return new  ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/up")
    @PreAuthorize("hasAuthority('USER')")
    private ResponseEntity<?> updateItem(@RequestBody OrderDetail orderDetail){
      Long orderId= orderDetailService.findById(orderDetail.getId()).get().getOrder().getId() ;
       Optional<Orders> or=orderService.findById(orderId);
        if (or.get().isStatus()==false) {
            Optional<OrderDetail> o = orderDetailService.findById(orderDetail.getId());
            if (o.isPresent()) {
                o.get().setPrice(o.get().getPrice());
                o.get().setQuantity(orderDetail.getQuantity());
                return new ResponseEntity<>(orderDetailService.save(o.get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteItem(@PathVariable("id") Long id){
        Optional<Orders> or=orderService.findById(orderDetailService.findById(id).get().getOrder().getId());
        if (or.get().isStatus()==false) {
            return new ResponseEntity<>(orderDetailService.deleteById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND );
    }
        @GetMapping("/proSellMonth/{month}")
        @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<?> revenue(@PathVariable int month){
        Long userId=userDetailService.getUserFromAuthentication().getId();
      List<OrderDetail> listOrder=orderDetailService.revenue (userId,month);
      if (listOrder.size()==0){
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(listOrder,HttpStatus.OK) ;
    }
}
