package com.tournet.tournetERP.auth.repository;

import java.util.List;
import java.util.Optional;
import com.tournet.tournetERP.auth.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface EmpRepository extends PagingAndSortingRepository<User,Long>,
        JpaSpecificationExecutor<User> {
    
}
