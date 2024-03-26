package com.tournet.tournetERP.auth.controller;

/**
 * 직원 관리
 *
 * @author : rubayi
 * @fileName : EmpController
 * @since : 2024-03-04
 */


import com.tournet.tournetERP.auth.dto.UserRequest;
import com.tournet.tournetERP.auth.dto.UserResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.repository.EmpRepository;
import com.tournet.tournetERP.auth.service.UserDetailsImpl;
import com.tournet.tournetERP.auth.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/empTn")
public class EmpController {

    @Autowired
    EmpRepository empRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

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
    public ResponseEntity<Map<String, Object>> searchEmpByCondition(@RequestBody UserRequest empReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<UserResponse> selectedUsers = new ArrayList<UserResponse>();

        if(storUser.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();
            selectedUsers = userService.findEmpsList(empReq);

            Map<String, Object> response = new HashMap<>();
            response.put("selectedUsers", selectedUsers);

            return new ResponseEntity<>(response, HttpStatus.OK);

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
    public ResponseEntity<UserResponse> searchEmpById(@PathVariable int id) {
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        UserResponse selectedUsers = new UserResponse();

        if(storUser.isAuthenticated()) {
            selectedUsers = userService.findByEmpUuid(id);
        }

        return new ResponseEntity<>(selectedUsers, HttpStatus.OK);
    }

    @PostMapping("/updateEmp")
    public ResponseEntity<Map<String, Object>> updateEmp(@RequestBody UserRequest empReq) {

        //수정자 정보
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        String message = "";
        if (storUser != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();

            User modifyingUser = new User();
            modifyingUser.setEmpUuid(userDetails.getEmpUuid());

            long id = (long) empReq.getEmpUuid();

            Optional<User> currentEmp = empRepository.findByEmpUuid(id);

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
                _emp.setEmpRole(empReq.getEmpRole());
                _emp.setEmpPhone(empReq.getEmpPhone());
                _emp.setEmpEmail(empReq.getEmpEmail());
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

                _emp.setBackColor(empReq.getBackColor());
                _emp.setFontColor(empReq.getFontColor());
                _emp.setEmpDobType(empReq.getEmpDobType());
                _emp.setEmpMemo(empReq.getEmpMemo());
                _emp.setEmpStatus(empReq.getEmpStatus());

                //_emp.setModifyUser(modifyingUser);

                if (empReq.getPassword() != null && !empReq.getPassword().equals("")) {
                    _emp.setPassword(encoder.encode(empReq.getPassword()));
                }

                empRepository.save(_emp);
                message = "수정 되었습니다.";
            } else {
                message = "수정이 완료 되지 않았습니다.";
            }
        } else {
            message = "다시 로그인 해 주십시오.";
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("message", message);
        return new ResponseEntity<>(resMap, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteEmp/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        if(storUser.isAuthenticated()) {
            empRepository.deleteByEmpUuid(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    @DeleteMapping("/deleteComcode/{id}")
    public ResponseEntity<?> deleteCode(@PathVariable int id) {
        empRepository.deleteByEmpUuid(id);

        return ResponseEntity.ok("삭제 되었습니다");
    }


}
