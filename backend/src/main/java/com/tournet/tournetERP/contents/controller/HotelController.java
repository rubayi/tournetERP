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
import com.tournet.tournetERP.contents.dto.HotelDTO;
import com.tournet.tournetERP.contents.entity.Hotel;
import com.tournet.tournetERP.contents.repository.HotelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @PostMapping("/selectHotels")
    public ResponseEntity<Map<String, Object>> selectHotels (@RequestBody HotelDTO searchhotelReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<Hotel> listHotel = new ArrayList<Hotel>();
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("listHotel", listHotel);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/createHotel")
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotelReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        if(storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            hotelReq.setCreatedUser(modifyingUser);
            hotelRepository.save(hotelReq);

        }
        return ResponseEntity.ok(new MessageResponse("등록이 완료 되었습니다."));
    }

    @PutMapping("/updateHotel/{id}")
    public ResponseEntity<Map<String, Object>> updateHotel(@RequestBody Hotel hotelReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        String message = "";

        if(storUser.isAuthenticated()) {

            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());


            Optional<Hotel> currentHotel = hotelRepository.findByHotelUuid(hotelReq.getHotelUuid());

            if (currentHotel.isPresent()) {

                hotelReq.setModifiedUser(modifyingUser);
                hotelRepository.save(hotelReq);

                message = "수정 되었습니다.";
            }
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deletehotel/{id}")
    public ResponseEntity<?> deletehotel(@PathVariable long id) {

        hotelRepository.deleteByHotelUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }


}
