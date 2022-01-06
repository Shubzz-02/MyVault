package com.shubzz.myvault.controllers;


import com.shubzz.myvault.modes.BannedToken;
import com.shubzz.myvault.modes.ERole;
import com.shubzz.myvault.modes.Role;
import com.shubzz.myvault.modes.User;
import com.shubzz.myvault.payload.request.LoginRequest;
import com.shubzz.myvault.payload.request.SignupRequest;
import com.shubzz.myvault.payload.response.JwtResponse;
import com.shubzz.myvault.payload.response.MessageResponse;
import com.shubzz.myvault.repository.BannedTokenRepository;
import com.shubzz.myvault.repository.RoleRepository;
import com.shubzz.myvault.repository.UserRepository;
import com.shubzz.myvault.security.jwt.JwtUtils;
import com.shubzz.myvault.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BannedTokenRepository bannedTokenRepository;


    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, Errors error) {
        if (error.hasErrors())
            return ResponseEntity.ok(new MessageResponse("Enter Valid values!", 0));
        //new MessageResponse("Enter valid values.", 3009));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

//        BannedToken b = new BannedToken();
//        b.setUsername("shuzzz");
//        b.setToken("sadjabdhawjkdjkhjdwka");
//
//        bannedTokenRepository.save(b);


        /// add https://www.bezkoder.com/spring-boot-refresh-token-jwt/
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    @PostMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, Errors error) {

        if (error.hasErrors())
            return ResponseEntity.ok(new MessageResponse("Enter Valid values!", 0));

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!", 3001));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!", 3002));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

//        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!", 3000));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String auth) {

//        if (errors.hasErrors())
//            return ResponseEntity.ok(new MessageResponse("Some Error Occurred!", 0));

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BannedToken bannedToken = new BannedToken();
        bannedToken.setUsername(userDetails.getUsername());
        bannedToken.setToken(auth.substring(7, auth.length()));
        bannedTokenRepository.save(bannedToken);
        return ResponseEntity.ok(new MessageResponse("User Logout Successfully", 2000));
    }


}
