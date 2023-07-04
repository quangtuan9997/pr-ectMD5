package ra.service.serviceInter;

import ra.model.RoleName;
import ra.model.Roles;
import ra.service.IGenericService;

import java.util.Optional;

public interface IRoleService extends IGenericService<Roles,Long> {
    Optional<Roles> findByRoleName(RoleName name);

}
