package com.tournet.tournetERP.contents.controller;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TourServiceController
 * @since : 2024-06-09
 */
import java.util.*;

import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.TourServiceDTO;
import com.tournet.tournetERP.contents.entity.TourService;
import com.tournet.tournetERP.contents.repository.TourServiceRepository;
import com.tournet.tournetERP.contents.service.TourServiceService;
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
@RequestMapping("/api/trService")
public class TourServiceController {

    @Autowired
    TourServiceRepository serviceRepository;

    @Autowired
    TourServiceService serviceService;

    @PostMapping("/selectServices")
    public ResponseEntity<?> selectServices (@RequestBody TourServiceDTO serviceReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<TourServiceDTO> serviceList = new ArrayList<TourServiceDTO>();

        if(storUser.isAuthenticated()) {
            serviceList = serviceService.findTourService(serviceReq);
        }

        return new ResponseEntity<>(serviceList, HttpStatus.OK);
    }


    @GetMapping("/searchTourService/{id}")
    public ResponseEntity<?> searchTourService(@PathVariable long id) {

        TourService tourInfo = serviceRepository.findOneByServiceUuid(id);

        return new ResponseEntity<>(tourInfo, HttpStatus.OK);
    }


    @PostMapping("/updateTourService")
    public ResponseEntity<?> updateService(@RequestBody TourService serviceReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        TourService _tourService = new TourService();

        if (storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            Optional<TourService> currentTourService = serviceRepository.
                    findByServiceUuid(serviceReq.getServiceUuid());

            if (currentTourService.isPresent()) {
                serviceReq.setModifyUser(modifyingUser);
            } else {
                serviceReq.setModifyUser(modifyingUser);
                serviceReq.setCreateUser(modifyingUser);
            }

            _tourService = serviceRepository.save(serviceReq);
        }

        return new ResponseEntity<>(_tourService, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/deleteTourService/{id}")
    public ResponseEntity<?> deleteService(@PathVariable long id) {

        String message = "";

        serviceRepository.deleteByServiceUuid(id);

        message="삭제 되었습니다.";

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);

    }


}