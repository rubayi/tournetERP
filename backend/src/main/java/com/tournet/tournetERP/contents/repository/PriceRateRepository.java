package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : PriceRateRepository
 * @since : 2024-06-14
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.PriceRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;



@Repository
public interface PriceRateRepository extends JpaRepository<PriceRate,Long> {

    Optional<PriceRate> findByRateUuid(long id);

    List<PriceRate> findAllByOrderByRateUuidDesc();

    @Modifying
    void deleteByRateUuid(@Param("id") long rateUuid);


    PriceRate findFirstByRateUuid(long id);
}
