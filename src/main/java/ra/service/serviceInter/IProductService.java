package ra.service.serviceInter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ra.model.Product;
import ra.service.IGenericService;

import java.util.List;

public interface IProductService extends IGenericService<Product,Long> {
    Page<Product> searchAllByProductName(String proName, Pageable pageable);
    Page<Product> findProductsByStatus(int status,Pageable pageable);
    Page<Product> findProductsByUsersId(Long userId,Pageable pageable);
    Page<Product> findProductsByCateId(Long cateId,Pageable pageable);
    List<Product> featuredProduct();
}
