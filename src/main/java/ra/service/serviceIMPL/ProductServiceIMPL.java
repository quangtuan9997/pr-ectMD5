package ra.service.serviceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.Product;
import ra.repository.IProductRepository;
import ra.service.serviceInter.IProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceIMPL implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deleteById(Long id) {
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> searchAllByProductName(String proName, Pageable pageable) {
        return productRepository.findProductByProductNameContaining(proName, pageable);
    }

    @Override
    public Page<Product> findProductsByStatus(int status, Pageable pageable) {
        return productRepository.findProductsByStatus(status, pageable);
    }

    @Override
    public Page<Product> findProductsByUsersId(Long userId, Pageable pageable) {
        return productRepository.findProductsByUsersId(userId,pageable);
    }

    @Override
    public Page<Product> findProductsByCateId(Long cateId, Pageable pageable) {
        return productRepository.findProductsByCateId(cateId,pageable);
    }

    @Override
    public List<Product> featuredProduct() {
        return productRepository.featuredProduct();
    }

}
