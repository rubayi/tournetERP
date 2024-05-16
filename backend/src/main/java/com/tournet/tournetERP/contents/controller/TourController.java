package com.tournet.tournetERP.contents.controller;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TourController
 * @since : 2024-04-05
 */
import java.util.*;

import com.tournet.tournetERP.account.entity.CreditCardMng;
import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.TourDTO;
import com.tournet.tournetERP.contents.entity.Company;
import com.tournet.tournetERP.contents.entity.Tour;
import com.tournet.tournetERP.contents.repository.TourRepository;
import com.tournet.tournetERP.contents.service.CompanyService;
import com.tournet.tournetERP.contents.service.TourInfoService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    public ResponseEntity<?> selectTours (@RequestBody TourDTO searchtourReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<TourDTO> listTour = new ArrayList<TourDTO>();
        if(storUser.isAuthenticated()) {
            listTour = tourInfoService.findtoursList(searchtourReq);
        }

        return new ResponseEntity<>(listTour, HttpStatus.OK);
    }

    @PostMapping("/updateTour")
    public ResponseEntity<?> updateTour(@RequestBody Tour tourReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Tour tourInfo = new Tour();

        if (storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            Optional<Tour> currentCreditCardMng = tourRepository.
                    findByTourUuid(tourReq.getTourUuid());

            if (currentCreditCardMng.isPresent()) {
                tourReq.setModifyUser(modifyingUser);
            } else {
                tourReq.setModifyUser(modifyingUser);
                tourReq.setCreateUser(modifyingUser);
            }

            tourInfo = tourRepository.save(tourReq);
        }

        return new ResponseEntity<>(tourInfo, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/deletetour/{id}")
    public ResponseEntity<?> deletetour(@PathVariable long id) {

        tourRepository.deleteByTourUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }

    @GetMapping("/getTour/{id}")
    public ResponseEntity<?> searchCompany(@PathVariable long id) {

        Tour tourInfo = tourRepository.findOneByTourUuid(id);

        return new ResponseEntity<>(tourInfo, HttpStatus.OK);
    }


}
