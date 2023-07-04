package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.dto.request.SignInForm;
import ra.dto.request.SignUpForm;
import ra.dto.response.JwtResponse;
import ra.dto.response.ResponseMessage;
import ra.model.Cart;
import ra.model.RoleName;
import ra.model.Roles;
import ra.model.Users;
import ra.security.jwt.JwtProvider;
import ra.security.userPrincipal.UserPrincipal;
import ra.service.serviceInter.ICartService;
import ra.service.serviceInter.IRoleService;
import ra.service.serviceInter.IUserService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final ICartService cartService;
    private final JwtProvider jwtProvider;


    @PostMapping("/signUp")
    public ResponseEntity<ResponseMessage> doSignUp(@RequestBody SignUpForm signUpForm) {
        boolean isExistUsername = userService.existsByUserName(signUpForm.getUsername());
        if (isExistUsername) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("This username is already existed!")
                            .data("")
                            .build()
            );
        }

        boolean isExistEmail = userService.existsByEmail(signUpForm.getEmail());
        if (isExistEmail) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("This phone email is already existed!")
                            .data("")
                            .build()
            );
        }


        Set<Roles> roles = new HashSet<>();

        if (signUpForm.getRoles() == null || signUpForm.getRoles().isEmpty()) {
            Roles role = roleService.findByRoleName(RoleName.USER)
                    .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
            roles.add(role);
        } else {
            signUpForm.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(RoleName.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(adminRole);
                    case "sm":
                        Roles smRole = roleService.findByRoleName(RoleName.SM)
                                .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(smRole);
                    case "seller":
                        Roles sellerRole = roleService.findByRoleName(RoleName.SELLER)
                                .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(sellerRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(RoleName.USER)
                                .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(userRole);

                }
            });
        }

        Users user = Users.builder()
                .fullName(signUpForm.getFullName())
                .userName(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .email(signUpForm.getEmail())
                .roles(roles)
                .build();

        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Account created successfully!")
                        .data(userService.save(user))
                        .build()
        );
    }


    @PostMapping("/signIn")
    public ResponseEntity<?> doSignIn(@RequestBody SignInForm signInForm) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            signInForm.getUsername(),
                            signInForm.getPassword())
                    );

            String token = jwtProvider.generateToken(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return new ResponseEntity<>(
                    JwtResponse.builder()
                            .status("OK")
                            .type("Bearer")
                            .fullName(userPrincipal.getFullName())
                            .token(token)
                            .roles(userPrincipal.getAuthorities())
                            .build(), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Invalid username or password!")
                            .data("")
                            .build(), HttpStatus.UNAUTHORIZED);
        }
    }

}
