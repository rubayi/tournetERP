package com.tournet.tournetERP.auth.controller;

/**
 * 인증
 *
 * @author : rubayi
 * @fileName : AuthController
 * @since : 2024-02-14
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tournet.tournetERP.auth.dto.JwtResponse;
import com.tournet.tournetERP.auth.dto.LoginRequest;
import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.dto.UserRequest;
import com.tournet.tournetERP.auth.entity.ERole;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.entity.Role;
import com.tournet.tournetERP.auth.repository.EmpRepository;
import com.tournet.tournetERP.auth.repository.RoleRepository;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.security.entity.RefreshToken;
import com.tournet.tournetERP.security.jwt.JwtUtils;
import com.tournet.tournetERP.security.services.RefreshTokenService;
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

    @Autowired
    RefreshTokenService refreshTokenService;

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

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getEmpUuid());

        return ResponseEntity.ok(new JwtResponse(jwt,
                refreshToken.getToken(),
                userDetails.getEmpUuid(),
                userDetails.getUsername(),
                userDetails.getEmpEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest signUpRequest) {
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
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmpKor(),
                signUpRequest.getEmpEng(),
                signUpRequest.getEmpWorkType(),
                signUpRequest.getEmpWorkPhone(),
                signUpRequest.getEmpDiv(),
                signUpRequest.getEmpTitle(),
                signUpRequest.getEmpPhone(),
                signUpRequest.getEmpEmailBook(),
                signUpRequest.getEmpAddress1(),
                signUpRequest.getEmpAddress2(),
                signUpRequest.getEmpCity(),
                signUpRequest.getEmpState(),
                signUpRequest.getEmpCountry(),
                signUpRequest.getEmpZip(),
                signUpRequest.getEmpDob(),
                signUpRequest.getEmpDobType(),
                signUpRequest.getEmpMemo(),
                signUpRequest.getEmpStatus()
                );

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