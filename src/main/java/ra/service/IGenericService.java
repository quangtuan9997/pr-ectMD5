package ra.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGenericService<T,E> {
    Page<T> findAll(Pageable pageable);
    T save(T t);
    boolean deleteById(E id);
    Optional<T>  findById(E id);
}
