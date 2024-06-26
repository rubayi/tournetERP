package com.tournet.tournetERP.contents.controller;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetImgController
 * @since : 2024-06-25
 */
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.contents.entity.TournetImg;
import com.tournet.tournetERP.contents.repository.TournetImgRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tournetImg")
public class TournetImgController {

    @Autowired
    TournetImgRepository tournetImgRepository;


    @PostMapping("/selectTrnImgs")
    public ResponseEntity<?> selectTournetImgs (@RequestBody TournetImg tournetImgReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<TournetImg> tournetImgList = new ArrayList<TournetImg>();

        if(storUser.isAuthenticated()) {
            tournetImgList = tournetImgRepository.findAllByOrderByImgOrdAsc(tournetImgReq.getTrnCarUuid());
        }
        return new ResponseEntity<>(tournetImgList, HttpStatus.OK);
    }

    @PostMapping("/updateTrnImg")
    public ResponseEntity<?> updateTournetImg(@RequestBody TournetImg tournetImgReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        TournetImg updatedtournetImg = new TournetImg();

        if(storUser.isAuthenticated()) {

            updatedtournetImg = tournetImgRepository.save(tournetImgReq);
        }
        return new ResponseEntity<>(updatedtournetImg, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/deleteTrnImg/{id}")
    public ResponseEntity<?> deleteTrnImg(@PathVariable long id) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        if(storUser.isAuthenticated()) {
            tournetImgRepository.deleteByTrnImgUuid(id);
        }
        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));

    }


    /**
     * searchTrnCar by tnrImgUuid
     * @param id
     * @return
     */
    @GetMapping("/searchTrnCar/{id}")
    public ResponseEntity<?> searchTrnCar(@PathVariable long id) {

        TournetImg hotelRoomInfo = tournetImgRepository.findByTrnImgUuid(id).get();

        return new ResponseEntity<>(hotelRoomInfo, HttpStatus.OK);
    }

}