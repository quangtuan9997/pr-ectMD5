package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.OrderDetail;
import ra.model.Orders;
import ra.model.Product;
import ra.security.userPrincipal.UserDetailService;
import ra.service.serviceInter.IOrderDetailService;
import ra.service.serviceInter.IOrderService;
import ra.service.serviceInter.IProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IProductService productService;
    @Autowired
    private UserDetailService userDetailService;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    private ResponseEntity<?> createOrder(@RequestBody Orders orders){
        return new ResponseEntity<>(orderService.save(orders), HttpStatus.CREATED);
    }
    @PutMapping("/up")
    @PreAuthorize("hasAuthority('USER')")
    private ResponseEntity<?> update(@RequestBody Orders orders ){
        Optional<Orders> o=orderService.findById(orders.getId());
        if (o.isPresent() && o.get().isStatus()==false){
            o.get().setEmail(orders.getEmail()!=null?orders.getEmail():o.get().getEmail());
            o.get().setAddress(orders.getAddress()!=null?orders.getAddress():o.get().getAddress());
            o.get().setPhone(orders.getPhone()!=null?orders.getPhone():o.get().getPhone());
            o.get().setTotal(orders.getTotal()!=0?orders.getTotal():o.get().getTotal());
            return new ResponseEntity<>(orderService.save(o.get()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/upStatus/{id}")
    @PreAuthorize("hasAuthority('SM')")
    private ResponseEntity<?> upStatus(@PathVariable("id")Long OrdereId){
        Optional<Orders> o=orderService.findById(OrdereId);
        List<OrderDetail> listDetail=orderDetailService.findAllByOrder_Id(OrdereId);

        if (o.isPresent()){
            if (o.get().isStatus()==false){
        for (OrderDetail od:listDetail){
            Product p=productService.findById(od.getPro().getId()).get();
            if (p.getQuantity()<od.getQuantity()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
                p.setQuantity(p.getQuantity() - od.getQuantity());
                productService.save(p);

        }
            o.get().setStatus(true);
            return new ResponseEntity<>(orderService.save(o.get()),HttpStatus.OK);
        }
            for (OrderDetail od:listDetail){
                Product p=productService.findById(od.getPro().getId()).get();
                p.setQuantity(p.getQuantity() + od.getQuantity());
                productService.save(p);
                o.get().setStatus(false);
                return new ResponseEntity<>(orderService.save(o.get()),HttpStatus.OK);
                }
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/searchByStatus")
    public ResponseEntity<?> searchByStatus(Pageable pageable){
        Page<Orders> listSearch=orderService.findOrdersByStatus(false,pageable);
        if (listSearch.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listSearch,HttpStatus.OK);
    }
}
