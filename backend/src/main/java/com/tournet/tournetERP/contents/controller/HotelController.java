package com.tournet.tournetERP.contents.controller;

/**
 * 호텛 기본 정보 관리
 *
 * @author : rubayi
 * @fileName : HotelController
 * @since : 2024-04-01
 */
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.contents.dto.CompanyDTO;
import com.tournet.tournetERP.contents.dto.HotelDTO;
import com.tournet.tournetERP.contents.entity.Hotel;
import com.tournet.tournetERP.contents.repository.HotelRepository;
import com.tournet.tournetERP.contents.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.transaction.Transactional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;

    @PostMapping("/selectHotels")
    public ResponseEntity<?> selectHotels (@RequestBody HotelDTO searchhotelReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<HotelDTO> hotelList = new ArrayList<HotelDTO>();
        //compRepository.findAllByOrderByModifiedDtDesc();
        if(storUser.isAuthenticated()) {
            hotelList = hotelService.findHotelList(searchhotelReq);
        }
        return new ResponseEntity<>(hotelList, HttpStatus.OK);
    }


    @GetMapping("/searchHotelByTour/{id}")
    public ResponseEntity<?> searchHotelByTour(
            @PathVariable long id) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        HotelDTO hotelInfo = new HotelDTO();

        if(storUser.isAuthenticated()) {
            Optional<Hotel> currentHotel = hotelRepository.findByTourUuid(id);

            if (currentHotel.isPresent()) {
                hotelInfo = hotelService.findHotelAddInfo(currentHotel.get());
            }
        }
        return new ResponseEntity<>(hotelInfo, HttpStatus.OK);
    }

    @PostMapping("/updateHotel")
    public ResponseEntity<?> updateHotel(@RequestBody Hotel hotelReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Hotel updatedHotel = new Hotel();
        if(storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            Optional<Hotel> currentHotel = hotelRepository.findByHotelUuid(hotelReq.getHotelUuid());

            if (currentHotel.isPresent()) {
                hotelReq.setModifiedUser(modifyingUser);
            } else {
                hotelReq.setModifiedUser(modifyingUser);
                hotelReq.setCreatedUser(modifyingUser);
            }

            updatedHotel = hotelRepository.save(hotelReq);
        }

        return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/deleteHotel/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable long id) {

        hotelRepository.deleteByHotelUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }


}
