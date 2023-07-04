package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.ChangePass;
import ra.dto.response.ResponseMessage;
import ra.model.RoleName;
import ra.model.Roles;
import ra.model.Users;
import ra.security.userPrincipal.UserDetailService;
import ra.service.serviceInter.IRoleService;
import ra.service.serviceInter.IUserService;

import java.util.Optional;
import java.util.Set;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class adminController {

    @Autowired
    IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/findAllUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<Users>> findAllUser(Pageable p){
        Page<Users> list= userService.findAll(p);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PutMapping("/up")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateUser(@RequestBody Users users){
        Long userid=userDetailService.getUserFromAuthentication().getId();
        Optional<Users> us=userService.findById(userid);
        if (us.isPresent()){
            us.get().setEmail(users.getEmail()!=null?users.getEmail():us.get().getEmail());
            us.get().setAddress(users.getAddress()!=null?users.getAddress():us.get().getAddress());
            us.get().setPhone(users.getPhone()!=null?users.getPhone():us.get().getPhone());
            us.get().setFullName(users.getFullName()!=null?users.getFullName():us.get().getFullName());
            us.get().setAvatar(users.getAvatar()!=null?users.getAvatar():us.get().getAvatar());
            return new ResponseEntity<>(userService.save(us.get()),HttpStatus.OK);
        }
return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/setRoleSM/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> setRoleSM(@PathVariable("id")Long userId){
        Roles roles = roleService.findByRoleName(RoleName.SM).get();
        Users u = userService.findById(userId).get();
        Set<Roles> role=u.getRoles();
        for (Roles r:role) {
          if(r.getId()==2) {
              u.getRoles().remove(roles);

              return new ResponseEntity<>(userService.save(u), HttpStatus.OK);
          }
        }
      u.getRoles().add(roles);

        return new ResponseEntity<>(userService.save(u), HttpStatus.OK);
    }
    @PostMapping("/setRoleSeller")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> setRoleSeller(){
        Long userId=userDetailService.getUserFromAuthentication().getId();
        Users u=userService.findById(userId).get();
        Roles roles=roleService.findByRoleName(RoleName.SELLER).get();
        Set<Roles> role=u.getRoles();
        for (Roles r:role) {
            if(r.getId()==3) {
                u.getRoles().remove(roles);
                return new ResponseEntity<>(userService.save(u), HttpStatus.OK);
            }
        }
        u.getRoles().add(roles);
        return new ResponseEntity<>(userService.save(u),HttpStatus.OK);
    }
    @PutMapping("/changePass")
    public ResponseEntity<?> changePass(@RequestBody ChangePass changePass){
        Users user=userDetailService.getUserFromAuthentication();
        String pass=user.getPassword();
        if(!passwordEncoder.matches(changePass.getOldPass(),pass)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseMessage("Failed","Old Password done's matches",null));
        }
        user.setPassword(passwordEncoder.encode(changePass.getNewPass()));
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
