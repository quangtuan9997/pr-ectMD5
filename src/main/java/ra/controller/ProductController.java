package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.Product;
import ra.model.Users;
import ra.security.userPrincipal.UserDetailService;
import ra.service.serviceInter.IProductService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private UserDetailService userDetailService;
    @GetMapping("/findAll")
    public Page<Product> findAll(Pageable pageable){
        return productService.findAll(pageable);
    }
    @GetMapping("/findAllByUserId")
    public ResponseEntity<?> findAllByUserId(Pageable pageable){
        Long userId=userDetailService.getUserFromAuthentication().getId();
       Page<Product> listPro=  productService.findProductsByUsersId(userId,pageable);
        if (listPro==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listPro,HttpStatus.OK);
    }
    @GetMapping("/findStatus")
    public Page<Product> findAllStatus(Pageable pageable){
        return productService.findProductsByStatus(2,pageable);
    }
    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody Product product){
        Users user=userDetailService.getUserFromAuthentication();
        product.setUsers(user);
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }
    @PutMapping("/up")
    public ResponseEntity<?> update(@RequestBody Product product){
        Optional<Product> p=productService.findById(product.getId());
        if (p.isPresent()){
            p.get().setCate(product.getCate()!=null?product.getCate():p.get().getCate());
            p.get().setDescription(product.getDescription()!=null?product.getDescription():p.get().getDescription());
            p.get().setProductName(product.getProductName()!=null?product.getProductName():p.get().getProductName());
            p.get().setPrice(product.getPrice()!=0?product.getPrice():p.get().getPrice());
            p.get().setTitle(product.getTitle()!=null?product.getTitle():p.get().getTitle());
            p.get().setUsers(product.getUsers()!=null?product.getUsers():p.get().getUsers());
            p.get().setQuantity(product.getQuantity()!=0?product.getQuantity():p.get().getQuantity());
            return new ResponseEntity<>(productService.save(p.get()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping ("/search/{proName}")
    public ResponseEntity<?> searchByName(@PathVariable("proName") String proName, Pageable pageable){
        Page<Product> listSearch= productService.searchAllByProductName(proName.trim(), pageable);
        if (listSearch.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listSearch,HttpStatus.OK);
    }
    @GetMapping("/searchByCate/{cateId}")
    public ResponseEntity<?> searchByCate(@PathVariable("cateId")Long cateId,Pageable pageable){
        Page<Product> listSearch=productService.findProductsByCateId(cateId,pageable);
        if (listSearch.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listSearch,HttpStatus.OK);
    }
    @GetMapping("/featuredProduct")
    public ResponseEntity<?> featuredProduct(){
        List<Product> listFeatured=productService.featuredProduct();
        if (listFeatured.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listFeatured,HttpStatus.OK);
    }
}
