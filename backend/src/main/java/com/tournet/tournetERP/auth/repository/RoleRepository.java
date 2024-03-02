package com.tournet.tournetERP.auth.repository;

import com.tournet.tournetERP.auth.entity.ERole;
import com.tournet.tournetERP.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoles(ERole name);

    Role findFirstByRoles(ERole name);
}