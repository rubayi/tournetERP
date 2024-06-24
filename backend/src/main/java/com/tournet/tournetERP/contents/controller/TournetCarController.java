package com.tournet.tournetERP.contents.controller;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TournetCarController
 * @since : 2024-06-21
 */
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.TournetCarDTO;
import com.tournet.tournetERP.contents.entity.TournetCar;
import com.tournet.tournetERP.contents.repository.TournetCarRepository;
import com.tournet.tournetERP.contents.service.TournetCarService;
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
@RequestMapping("/api/tournetCar")
public class TournetCarController {

    @Autowired
    TournetCarRepository tournetCarRepository;

    @Autowired
    TournetCarService tournetCarService;

    @PostMapping("/selectTournetCars")
    public ResponseEntity<?> selectTournetCars (@RequestBody TournetCarDTO tournetCarReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<TournetCarDTO> tournetCarList = new ArrayList<TournetCarDTO>();

        if(storUser.isAuthenticated()) {
            tournetCarList = tournetCarService.findTournetCarList(tournetCarReq);
        }

        return new ResponseEntity<>(tournetCarList, HttpStatus.OK);
    }

    @PostMapping("/updateTrnCar")
    public ResponseEntity<?> updateTrnCar(@RequestBody TournetCar trnCarReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        TournetCar updatedtournetCar = new TournetCar();

        if(storUser.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            Optional<TournetCar> currentTournetCar = tournetCarRepository.findByTrnCarUuid(trnCarReq.getTrnCarUuid());

            if (currentTournetCar.isPresent()) {
                trnCarReq.setModifiedBy(modifyingUser.getEmpUuid());
            } else {
                trnCarReq.setModifiedBy(modifyingUser.getEmpUuid());
                trnCarReq.setCreatedBy(modifyingUser.getEmpUuid());
            }
            updatedtournetCar = tournetCarRepository.save(trnCarReq);
        }
        return new ResponseEntity<>(updatedtournetCar, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/deleteTrnCar/{id}")
    public ResponseEntity<?> deleteTrnCar(@PathVariable long id) {

        tournetCarRepository.deleteByTrnCarUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));

    }

    @GetMapping("/searchTrnCar/{id}")
    public ResponseEntity<?> searchTrnCar(@PathVariable long id) {

        TournetCar hotelRoomInfo = tournetCarRepository.findByTrnCarUuid(id).get();

        return new ResponseEntity<>(hotelRoomInfo, HttpStatus.OK);
    }


}
