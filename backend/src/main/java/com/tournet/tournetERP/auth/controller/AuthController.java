package com.tournet.tournetERP.auth.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tournet.tournetERP.auth.DTO.JwtResponse;
import com.tournet.tournetERP.auth.DTO.LoginRequest;
import com.tournet.tournetERP.auth.DTO.MessageResponse;
import com.tournet.tournetERP.auth.DTO.SignupRequest;
import com.tournet.tournetERP.auth.entity.ERole;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.entity.Role;
import com.tournet.tournetERP.auth.repository.EmpRepository;
import com.tournet.tournetERP.auth.repository.RoleRepository;
import com.tournet.tournetERP.security.jwt.JwtUtils;
import com.tournet.tournetERP.security.services.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
    EmpRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

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

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getEmpUuid(),
                userDetails.getUsername(),
                userDetails.getEmpEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("이미 등록 된 사용자명 입니다."));
        }

        if (userRepository.existsByEmpEmail(signUpRequest.getEmpEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("이미 등록 된 이메일 입니다."));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmpEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoles(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("없는 사용자 권한 입니다."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoles(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("없는 사용자 권한 입니다."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByRoles(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("없는 사용자 권한 입니다."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRoles(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("없는 사용자 권한 입니다."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("등록 되었습니다."));
    }
}