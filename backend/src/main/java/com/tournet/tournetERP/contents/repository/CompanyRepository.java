package com.tournet.tournetERP.contents.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : CompanyRepository
 * @since : 2024-03-05
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.contents.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Optional<Company> findByCompUuid(int id);

    List<Company> findAllByOrderByModifiedDtDesc();

    void deleteByCompUuid(int compUuid);


}
