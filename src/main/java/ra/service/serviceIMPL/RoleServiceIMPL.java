package ra.service.serviceIMPL;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.RoleName;
import ra.model.Roles;
import ra.repository.IRoleRepository;
import ra.service.serviceInter.IRoleService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceIMPL implements IRoleService {

    private final IRoleRepository roleRepository;


    @Override
    public Optional<Roles> findByRoleName(RoleName name) {
        return roleRepository.findByRoleName(name);
    }

    @Override
    public Page<Roles> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Roles save(Roles roles) {
        return roleRepository.save(roles);
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Optional<Roles> findById(Long id) {
        return roleRepository.findById(id);
    }
}