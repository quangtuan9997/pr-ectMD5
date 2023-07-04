package ra.security.userPrincipal;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.model.Users;
import ra.service.serviceInter.IUserService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Users user = userService.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed -> NOT FOUND USE at username: " + username));

        return UserPrincipal.build(user);
    }
    public Users getUserFromAuthentication(){
        UserPrincipal userPrincipal= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findById(userPrincipal.getId()).get();
    }
    }

