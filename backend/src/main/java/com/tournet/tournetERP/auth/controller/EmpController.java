package com.tournet.tournetERP.auth.controller;

/**
 * 직원 관리
 *
 * @author : rubayi
 * @fileName : EmpController
 * @since : 2024-03-04
 */


import com.tournet.tournetERP.auth.dto.UserRequest;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.repository.EmpRepository;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/empTn")
public class EmpController {

    @Autowired
    EmpRepository empRepository;

    /**
     * 직원 목록 조회
     *
     * @return
     */
    @GetMapping("/selectEmps")
    public ResponseEntity<List<User>> getEmps() {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        if(storUser.isAuthenticated()) {
            List<User> emps = new ArrayList<User>();
            emps.addAll(empRepository.findAllByOrderByEmpBeginDtDesc());

            return new ResponseEntity<>(emps, HttpStatus.OK);
        }
        return null;
    }

    /**
     *  직원 조회
     *
     * @param empReq
     * @return
     */
    @PostMapping("/searchEmpByCondition")
    public ResponseEntity<List<User>> searchEmpByCondition(@RequestBody UserRequest empReq) {


        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        if(storUser.isAuthenticated()) {
            String empStatus = empReq.getEmpStatus();
            String empKor = empReq.getEmpKor();
            String empEng = empReq.getEmpEng();
            String username = empReq.getUsername();

            List<User> selectedUsers = empRepository.findByEmpStatusOrEmpKorOrEmpEngOrUsernameOrderByEmpBeginDtDesc(
                    empStatus.isEmpty() ? null : empStatus,
                    empKor.isEmpty() ? null : empKor,
                    empEng.isEmpty() ? null : empEng,
                    username.isEmpty() ? null : username
            );

            return new ResponseEntity<>(selectedUsers, HttpStatus.OK);
        }
        return null;
    }


    /**
     *  직원 개별 조회
     *
     * @param id
     * @return
     */
    @GetMapping("/searchEmpBy/{id}")
    public ResponseEntity<User> searchEmpById(@PathVariable int id) {


        User selectedUsers = empRepository.findFirstByEmpUuid(id);

        return new ResponseEntity<>(selectedUsers, HttpStatus.OK);
    }


    @PutMapping("/updateEmp")
    public ResponseEntity<Map<String, Object>> updateEmp(@RequestBody UserRequest empReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        int id = empReq.getEmpUuid();

        Optional<User> currentEmp = empRepository.findByEmpUuid(id);

        String message = "";
        if (currentEmp.isPresent()) {
            User _emp = currentEmp.get();

            _emp.setEmpKor(empReq.getEmpKor());
            _emp.setEmpEng(empReq.getEmpEng());
            _emp.setEmpWorkType(empReq.getEmpWorkType());
            _emp.setEmpDiv(empReq.getEmpDiv());
            _emp.setEmpTitle(empReq.getEmpTitle());
            _emp.setEmpWorkPhone(empReq.getEmpWorkPhone());
            _emp.setEmpDiv(empReq.getEmpDiv());
            _emp.setEmpTitle(empReq.getEmpTitle());
            _emp.setEmpPhone(empReq.getEmpPhone());
            _emp.setEmpEmailBook(empReq.getEmpEmailBook());
            _emp.setEmpAddress1(empReq.getEmpAddress1());
            _emp.setEmpAddress2(empReq.getEmpAddress2());
            _emp.setEmpCity(empReq.getEmpCity());
            _emp.setEmpState(empReq.getEmpState());
            _emp.setEmpCountry(empReq.getEmpCountry());
            _emp.setEmpZip(empReq.getEmpZip());
            _emp.setEmpDob(empReq.getEmpDob());
            _emp.setEmpDobType(empReq.getEmpDobType());
            _emp.setEmpMemo(empReq.getEmpMemo());
            _emp.setEmpStatus(empReq.getEmpStatus());

            empRepository.save(_emp);
            message = "수정 되었습니다.";
        } else {
            message = "수정이 완료 되지 않았습니다.";
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteComcode/{id}")
    public ResponseEntity<?> deleteCode(@PathVariable int id) {
        empRepository.deleteByEmpUuid(id);

        return ResponseEntity.ok("삭제 되었습니다");
    }


}