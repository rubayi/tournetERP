package com.tournet.tournetERP.account.repository;

/**
 * 프리페이드 카드 관리
 *
 * @author : rubayi
 * @fileName : CreditCardMngRepository
 * @since : 2024-04-08
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.account.entity.CreditCardMng;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


@Repository
public interface CreditCardMngRepository extends JpaRepository<CreditCardMng,Long> {

    Optional<CreditCardMng> findByCreditCardUuid(long id);

    List<CreditCardMng> findAllByOrderByModifiedDtDesc();

    @Modifying
    void deleteByCreditCardUuid(@Param("id") long creditCardUuid);


}