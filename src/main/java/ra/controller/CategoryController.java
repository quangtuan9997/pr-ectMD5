package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.model.Category;
import ra.service.serviceInter.ICategoryService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @GetMapping("/auth/findAllCate")
    @PreAuthorize("hasAuthority('SM')")
    public Page<Category> findAll(Pageable pageable){
      return   categoryService.findAll(pageable);
    }
    @PostMapping("auth/cate/create")
    @PreAuthorize("hasAuthority('SM')")
    public ResponseEntity<?> createCate(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }
    @PutMapping("/auth/cate/up/{id}")
    @PreAuthorize("hasAuthority('SM')")
    public ResponseEntity<?>  updateCate(@PathVariable("id") Long id,@RequestBody Category category) {
       Optional<Category> c = categoryService.findById(id);
        if (c.isPresent()) {
           c.get().setName(category.getName());
//            c.get().setId(id);
            return new ResponseEntity<>(categoryService.save(c.get()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('SM')")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
        Optional<Category> c = categoryService.findById(id);
        if (c.isPresent()){
            categoryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
