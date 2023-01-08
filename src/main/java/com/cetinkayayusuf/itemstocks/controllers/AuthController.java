package com.cetinkayayusuf.itemstocks.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.cetinkayayusuf.itemstocks.auth.jwt.JwtUtils;
import com.cetinkayayusuf.itemstocks.auth.services.UserDetailsImpl;
import com.cetinkayayusuf.itemstocks.business.abstracts.RefreshTokenService;
import com.cetinkayayusuf.itemstocks.business.abstracts.UserService;
import com.cetinkayayusuf.itemstocks.controllers.dtos.auth.*;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.RoleDao;
import com.cetinkayayusuf.itemstocks.dataAccess.abstracts.UserDao;
import com.cetinkayayusuf.itemstocks.entities.concretes.ERole;
import com.cetinkayayusuf.itemstocks.entities.concretes.RefreshToken;
import com.cetinkayayusuf.itemstocks.entities.concretes.Role;
import com.cetinkayayusuf.itemstocks.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserService userService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        Optional<User> userResult = userService.getByUsername(userDetails.getUsername());
        if (userResult.isPresent()) {
            return ResponseEntity.ok(new JwtAuthResponse(
                    "Bearer " + jwt,
                    refreshTokenService.createRefreshToken(userResult.get()),
                    userDetails.getUsername(),
                    roles));
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userDao.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userDao.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        List<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleDao.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleDao.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleDao.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userDao.save(user);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> rolesList = user.getRoles().stream().map(item -> item.getName().toString()).toList();

        return ResponseEntity.ok(new JwtAuthResponse(
                "Bearer " + jwt,
                refreshTokenService.createRefreshToken(user),
                user.getUsername(),
                rolesList));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody JwtRefreshRequest refreshRequest) {
        Optional<RefreshToken> token = refreshTokenService.getByUsername(refreshRequest.getUsername());
        if (token.isPresent() && token.get().getToken().equals(refreshRequest.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(token.get())) {

            User user = token.get().getUser();
            String jwtToken = jwtUtils.generateJwtToken(user.getUsername());

            JwtRefreshResponse response = new JwtRefreshResponse();
            response.setUsername(user.getUsername());
            response.setAccessToken("Bearer " + jwtToken);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("refresh token is not valid.", HttpStatus.UNAUTHORIZED);
        }
    }
}