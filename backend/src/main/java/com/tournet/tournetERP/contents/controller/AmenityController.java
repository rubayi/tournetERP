package com.tournet.tournetERP.contents.controller;

import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.AmenityDTO;
import com.tournet.tournetERP.contents.entity.Amenity;
import com.tournet.tournetERP.contents.repository.AmenityRepository;
import com.tournet.tournetERP.contents.service.AmenityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/amenity")
public class AmenityController {
    
    @Autowired
    AmenityRepository amenityRepository;

    @Autowired
    AmenityService amenityService;

    @PostMapping("/selectAmenities")
    public ResponseEntity<?> selectAmenities(@RequestBody AmenityDTO searchAmenityReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<AmenityDTO> amenityList = new ArrayList<AmenityDTO>();
        //compRepository.findAllByOrderByModifiedDtDesc();
        if (storUser.isAuthenticated()) {
            amenityList = amenityService.findAmenityList(searchAmenityReq);
        }
        return new ResponseEntity<>(amenityList, HttpStatus.OK);
    }
    
    @GetMapping("/searchAmenityByTour/{id}")
    public ResponseEntity<?> searchAmenityByTour(
            @PathVariable long id) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        AmenityDTO amenity = new AmenityDTO();

        if (storUser.isAuthenticated()) {
            Optional<Amenity> amenityData = amenityRepository.findById(id);

            if (amenityData.isPresent()) {

                amenity = amenityService.findAmenityAddInfo(amenityData.get());
            }
        }
        return new ResponseEntity<>(amenity, HttpStatus.OK);
    }

    @PostMapping("/updateAmenity")
    public ResponseEntity<Map<String, Object>> updateAmenity(@RequestBody Amenity amenityReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        String message = "";

        if (storUser.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();
            User mofiyUser = new User();
            mofiyUser.setEmpUuid(userDetails.getEmpUuid());

            Optional<Amenity> currentAmenity = amenityRepository.findByAmenityUuid(amenityReq.getAmenityUuid());
            if (currentAmenity.isPresent()) {
                amenityReq.setModifiedUser(mofiyUser);
                message = "수정 되었습니다.";
            } else {
                amenityReq.setModifiedUser(mofiyUser);
                amenityReq.setCreatedUser(mofiyUser);
                message = "등록 되었습니다.";
            }
            amenityRepository.save(amenityReq);
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @Transactional
    @GetMapping("/deleteAmenity/{id}")
    public ResponseEntity<?> deleteAmenity(@PathVariable long id) {

        amenityRepository.deleteByAmenityUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }
}
