package com.tournet.tournetERP.auth.service;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : UserDetailsImpl
 * @since : 2024-03-04
 */

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tournet.tournetERP.auth.entity.EmpMenuAuth;
import com.tournet.tournetERP.auth.entity.MenuAuth;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.repository.EmpMenuAuthRepository;
import com.tournet.tournetERP.auth.repository.MenuAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long empUuid;

    private String username;

    private String empEmail;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private String[] menuAuths;

    public UserDetailsImpl(Long empUuid, String username, String empEmail, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.empUuid = empUuid;
        this.username = username;
        this.empEmail = empEmail;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user,
                                        EmpMenuAuthRepository empMenuAuthRepository,
                                        MenuAuthRepository menuAuthRepository) {


        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoles().name()))
                .collect(Collectors.toList());

        List<EmpMenuAuth> currentEmpMenuAuths = empMenuAuthRepository.findAllByEmpUuid(user.getEmpUuid());

        if (currentEmpMenuAuths != null) {
            for (EmpMenuAuth menuAuth : currentEmpMenuAuths) {
                Optional<MenuAuth> optionalMenuAuth = menuAuthRepository.findByMenuAuthUuid(menuAuth.getMenuAuthUuid());
                if (optionalMenuAuth.isPresent()) {
                    MenuAuth _menuAuth = optionalMenuAuth.get();
                    authorities.add(new SimpleGrantedAuthority(_menuAuth.getMenuAuthNameEng()));
                }
            }
        }
        return new UserDetailsImpl(
                user.getEmpUuid(),
                user.getUsername(),
                user.getEmpEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getEmpUuid() {
        return empUuid;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(empUuid, user.empUuid);
    }
}