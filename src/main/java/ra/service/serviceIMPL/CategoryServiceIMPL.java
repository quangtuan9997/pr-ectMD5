package ra.service.serviceIMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.Category;
import ra.repository.ICategoryRepository;
import ra.service.serviceInter.ICategoryService;

import java.util.Optional;

@Service
public class CategoryServiceIMPL implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable) ;
    }

    @Override
    public Category  save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public boolean deleteById(Long id) {
        categoryRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id) ;
    }
}
