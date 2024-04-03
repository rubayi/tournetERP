package com.tournet.tournetERP.common.controller;

/**
 * 비상연락망 관리
 *
 * @author : rubayi
 * @fileName : EmployeeEmergencyController
 * @since : 2024-04-01
 */
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.common.dto.EmployeeEmergencyRequest;
import com.tournet.tournetERP.common.entity.EmployeeEmergency;
import com.tournet.tournetERP.common.repository.EmployeeEmergencyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/employeeEmergency")
public class EmployeeEmergencyController {

    @Autowired
    EmployeeEmergencyRepository employeeEmergencyRepository;

    @PostMapping("/selectEmployeeEmergencys")
    public ResponseEntity<Map<String, Object>> selectEmployeeEmergencys(
            @RequestBody EmployeeEmergency employeeEmergencyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<EmployeeEmergency> currentEmployeeEmergencys = employeeEmergencyRepository.findAllByOrderByEmerUuidDesc();

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("employeeEmergencys", currentEmployeeEmergencys);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @GetMapping("/selectEmployeeEmergencyByEmpUuid/{id}")
    public ResponseEntity<Map<String, Object>> selectEmployeeEmergencyByEmpUuid(@PathVariable int id) {

        List<EmployeeEmergency> currentEmployeeEmergency = employeeEmergencyRepository.findByEmpUuid(id);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("employeeEmergency", currentEmployeeEmergency);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/createEmployeeEmergency")
    public ResponseEntity<?> createEmployeeEmergency(@RequestBody EmployeeEmergency employeeEmergencyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();


        employeeEmergencyRepository.save(employeeEmergencyReq);

        return ResponseEntity.ok(new MessageResponse("등록이 완료 되었습니다."));
    }

    @PostMapping("/updateEmployeeEmergency")
    public ResponseEntity<Map<String, Object>> updateEmployeeEmergency(@RequestBody EmployeeEmergency employeeEmergencyReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        Optional<EmployeeEmergency> currentEmployeeEmergency = employeeEmergencyRepository.findByEmerUuid(employeeEmergencyReq.getEmerUuid());

        String message = "";

        if (currentEmployeeEmergency.isPresent()) {

            employeeEmergencyRepository.save(employeeEmergencyReq);

            message = "수정 되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteemployeeEmergency/{id}")
    public ResponseEntity<?> deleteemployeeEmergency(@PathVariable int id) {

        employeeEmergencyRepository.deleteByEmerUuid(id);

        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }


}