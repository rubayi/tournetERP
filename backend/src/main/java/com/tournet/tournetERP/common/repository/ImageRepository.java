package com.tournet.tournetERP.common.repository;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ImageRepository
 * @since : 2024-04-01
 */
import java.util.List;
import java.util.Optional;

import com.tournet.tournetERP.common.entity.Image;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
@CacheConfig(cacheNames = { "mainimages" })
public interface ImageRepository extends JpaRepository<Image, Long> {

    @CachePut(cacheNames = "mainimages", key = "#imgUuid")
    Image save(Image imagereq);

    @CacheEvict(cacheNames = "mainimages", allEntries = true)
    void deleteByImgUuid(long id);

    Optional<Image> findByImgUuid(long id);


    @Query(" SELECT i " +
            " FROM Image i " +
            " WHERE i.grpIdx =  :grpIdx " +
            " ORDER BY i.imgOrd asc " +
            " Limit 1 ")
    List<Image> findImagesByGrpIdxOrderByCreatedDtDesc(String grpIdx);

    @Query(" SELECT i " +
            " FROM Image i " +
            " WHERE i.grpIdx =  :grpIdx " +
            " And i.mainYn = :mainYn " +
            " ORDER BY i.imgOrd asc " +
            " Limit 1 ")
    List<Image> findImagesByGrpIdxMainYnOrderByCreatedDtDesc(String grpIdx, String mainYn);
}