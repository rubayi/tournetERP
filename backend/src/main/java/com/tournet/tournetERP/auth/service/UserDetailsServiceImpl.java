package com.tournet.tournetERP.auth.service;

/**
 * UserDetailsServiceImpl
 *
 * @author : rubayi
 * @fileName : UserDetailsServiceImpl
 * @since : 2024-03-04
 */
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    EmpRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자명의 직원이 없습니다.: " + username));

        return UserDetailsImpl.build(user);
    }

}
