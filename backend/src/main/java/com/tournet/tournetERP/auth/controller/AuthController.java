package com.tournet.tournetERP.auth.controller;

/**
 * 인증
 *
 * @author : rubayi
 * @fileName : AuthController
 * @since : 2024-02-14
 */

import java.util.*;
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
import com.tournet.tournetERP.security.exception.TokenRefreshException;
import com.tournet.tournetERP.security.dto.TokenRefreshRequest;
import com.tournet.tournetERP.security.dto.TokenRefreshResponse;
import com.tournet.tournetERP.security.entity.RefreshToken;
import com.tournet.tournetERP.security.jwt.JwtUtils;
import com.tournet.tournetERP.security.services.RefreshTokenService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getAllroles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/getCurrentUser")
    @ResponseBody
    public UserDetailsImpl getCurrentUser() {
        UserDetailsImpl username=null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = (UserDetailsImpl) principal;
        }
        return username;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest signUpRequest) {

        /**S: 수정자 정보 **/
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();
        /**E: 수정자 정보**/

        String message = "";

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            message = "이미 등록 된 사용자명 입니다.";
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("이미 등록 된 사용자명 입니다."));
        }

        if (userRepository.existsByEmpEmail(signUpRequest.getEmpEmail())) {
            message = "이미 등록 된 이메일 입니다.";
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
                signUpRequest.getEmpWorkPhone(),
                signUpRequest.getEmpDiv(),
                signUpRequest.getEmpTitle(),
                signUpRequest.getEmpPhone(),
                signUpRequest.getEmpEmailBook(),
                signUpRequest.getEmpAddress(),
                signUpRequest.getEmpOffice(),
                signUpRequest.getEmpComp(),
                signUpRequest.getEmpExtenNum(),
                signUpRequest.getEmpFax(),
                signUpRequest.getEmpMemo(),
                signUpRequest.getEmpStatus(),
                userDetails.getEmpUuid(),
                signUpRequest.getEmpImg(),
                signUpRequest.getEmpRole(),
                signUpRequest.getBackColor(),
                signUpRequest.getFontColor(),
                signUpRequest.getEmpBeginDt(),
                signUpRequest.getEmpEndDt(),
                signUpRequest.getCar25seats(),
                signUpRequest.getCar15seats(),
                signUpRequest.getCarPersonal()
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

        message = "사용자 등록이 완료 되었습니다.";
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }


    @PostMapping("/createEmp")
    public ResponseEntity<?> registerUserInFirst(@Valid @RequestBody UserRequest signUpRequest) {

        String message = "";

        // Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmpEmail(signUpRequest.getEmpEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

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

        message = "사용자 등록이 완료 되었습니다.";
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public void logout(){
        SecurityContextHolder.clearContext();
    }
}