package com.tournet.tournetERP.contents.controller;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : HotelRoomController
 * @since : 2024-06-20
 */
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.contents.dto.HotelRoomDTO;
import com.tournet.tournetERP.contents.dto.TourDTO;
import com.tournet.tournetERP.contents.entity.HotelRoom;
import com.tournet.tournetERP.contents.entity.Tour;
import com.tournet.tournetERP.contents.repository.HotelRoomRepository;
import com.tournet.tournetERP.contents.service.HotelRoomService;
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
@RequestMapping("/api/hotelRoom")
public class HotelRoomController {

    @Autowired
    HotelRoomRepository hotelRoomRepository;

    @Autowired
    HotelRoomService hotelRoomService;

    @PostMapping("/selectHotelRooms")
    public ResponseEntity<?> selectHotelRooms (@RequestBody HotelRoomDTO hotelRoomReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<HotelRoomDTO> hotelRoomList = new ArrayList<HotelRoomDTO>();

        if (storUser.isAuthenticated()) {
            hotelRoomList = hotelRoomService.findHotelRoomList(hotelRoomReq);
        }

        return new ResponseEntity<>(hotelRoomList, HttpStatus.OK);
    }

    @PostMapping("/updateHotelRoom")
    public ResponseEntity<?> updateHotelRoom(@RequestBody HotelRoom hotelRoomReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        HotelRoom updatedHotelRoom = new HotelRoom();

        if (storUser.isAuthenticated()) {
            updatedHotelRoom = hotelRoomRepository.save(hotelRoomReq);
        }

        return new ResponseEntity<>(updatedHotelRoom, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/deleteHotelRoom/{id}")
    public ResponseEntity<?> deleteHotelRoom(@PathVariable long id) {

        hotelRoomRepository.deleteByRoomUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));

    }

    @GetMapping("/searchHotelRoom/{id}")
    public ResponseEntity<?> searchHotelRoom(@PathVariable long id) {

        HotelRoom hotelRoomInfo = hotelRoomRepository.findOneByRoomUuid(id);

        return new ResponseEntity<>(hotelRoomInfo, HttpStatus.OK);
    }


}
