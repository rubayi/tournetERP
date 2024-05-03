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

    Optional<CreditCardMng> findByCdCdUuid(long id);

    @Query("SELECT u FROM CreditCardMng u " +
            "WHERE (:cdcdUuid IS NULL OR u.cdCdUuid = :cdcdUuid) " +
            "AND (:mngNameKor IS NULL OR u.mngNameKor LIKE CONCAT('%', :mngNameKor, '%')) " +
            "AND (:mngNameEng IS NULL OR u.mngNameEng LIKE CONCAT('%', :mngNameEng, '%')) " +
            "AND (:cardNumber IS NULL OR u.cardNumber LIKE CONCAT('%', :cardNumber, '%')) " +
            "OR (:cdcdUuid IS NULL AND :mngNameKor IS NULL AND :mngNameEng IS NULL AND :cardNumber IS NULL) " +
            "ORDER BY u.modifiedDt DESC")
    List<CreditCardMng> findAllByOrderByModifiedDtDesc(
            @Param("cdcdUuid") Long cdcdUuid,
            @Param("mngNameKor") String mngNameKor,
            @Param("mngNameEng") String mngNameEng,
            @Param("cardNumber") String cardNumber
            
    );

    @Modifying
    void deleteByCdCdUuid(@Param("id") long creditCardUuid);


}