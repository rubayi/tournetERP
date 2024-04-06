package com.tournet.tournetERP.contents.controller;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TourController
 * @since : 2024-04-05
 */
import java.util.*;

import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.TourDTO;
import com.tournet.tournetERP.contents.entity.Tour;
import com.tournet.tournetERP.contents.repository.TourRepository;
import com.tournet.tournetERP.contents.service.CompanyService;
import com.tournet.tournetERP.contents.service.TourInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tour")
public class TourController {

    @Autowired
    TourRepository tourRepository;

    @Autowired
    TourInfoService tourInfoService;

    @PostMapping("/selectTours")
    public ResponseEntity<Map<String, Object>> selectTours (@RequestBody TourDTO searchtourReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<Tour> listTour = new ArrayList<Tour>();
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("listTour", listTour);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @PostMapping("/updateTour")
    public ResponseEntity<Map<String, Object>> updateTour(@RequestBody Tour tourReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Optional<Tour> currentTour = tourRepository.findByTourUuid(tourReq.getTourUuid());

        String message = "";
        if (storUser.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            Long modifier = userDetails.getEmpUuid();

            if (currentTour.isPresent()) {

                tourReq.setModifiedBy(modifier);
                message = "수정 되었습니다.";
            } else {
                tourReq.setModifiedBy(modifier);
                tourReq.setCreatedBy(modifier);
                message = "등록 되었습니다.";
            }
            tourRepository.save(tourReq);
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deletetour/{id}")
    public ResponseEntity<?> deletetour(@PathVariable long id) {

        String message = "";

        tourRepository.deleteByTourUuid(id);

        message="삭제 되었습니다.";

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);

    }


}