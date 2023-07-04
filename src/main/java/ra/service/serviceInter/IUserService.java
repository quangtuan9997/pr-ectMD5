package ra.service.serviceInter;

import ra.model.Users;
import ra.service.IGenericService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IGenericService<Users,Long> {
    boolean existsByUserName(String userName);
    boolean  existsByEmail(String email);
    Optional<Users> findByUserName(String userName);


}
