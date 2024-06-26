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
    @PostMapping("/selectEmps")
    public ResponseEntity<List<User>> getEmps() {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        if (storUser.isAuthenticated()) {
            List<User> emps = new ArrayList<User>();
            emps.addAll(empRepository.findAllByOrderByEmpBeginDtDesc());

            return new ResponseEntity<>(emps, HttpStatus.OK);
        }
        return null;
    }

    /**
     *  직원 회사별 조회
     *
     * @param id
     * @return
     */
    @PostMapping("/searchEmpByComp/{id}")
    public ResponseEntity<List<UserResponse>> searchEmpByComp(@PathVariable int id) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<UserResponse> selectedUsers = new ArrayList<UserResponse>();

        if (storUser.isAuthenticated()) {
            selectedUsers = userService.findEmpsListByComp(id);

            return new ResponseEntity<>(selectedUsers, HttpStatus.OK);

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

        if (storUser.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) storUser.getPrincipal();
            selectedUsers = userService.findEmpsList(empReq);

            Map<String, Object> response = new HashMap<>();
            response.put("selectedUsers", selectedUsers);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        return null;
    }

    /**
 * 직원 목록 조회
 *
 * @param empDiv, empKor, empEng
 * @return
 */
@PostMapping("/selectEmpsByCondition")
public ResponseEntity<List<UserResponse>> selectEmpsByCondition(@RequestBody UserRequest empReq) {

    // Long empDiv = empReq.getSearchEmpDiv();
    // String empKor = empReq.getSearchEmpKor();
    // String empEng = empReq.getSearchEmpEng();

    Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
    if (storUser.isAuthenticated()) {
        List<UserResponse> emps = new ArrayList<UserResponse>();

        emps = userService.findEmpsList(empReq);
        // if (empDiv != 0) {
        //     if (empKor != "" ) {
        //         if (empEng != "" ) {
        //             emps.addAll(empRepository.findByEmpDivAndEmpKorContainingAndEmpEngContaining(empDiv, empKor, empEng));
        //         } else {
        //             emps.addAll(empRepository.findByEmpDivAndEmpKorContaining(empDiv, empKor));
        //         }
        //     } else if (empEng != "" ) {
        //         emps.addAll(empRepository.findByEmpDivAndEmpEngContaining(empDiv, empEng));
        //     } else {
        //         emps.addAll(empRepository.findByEmpDiv(empDiv));
        //     }
        // } else if (empKor != "" ) {
        //     if (empEng != "") {
        //         emps.addAll(empRepository.findByEmpKorContainingAndEmpEngContaining(empKor, empEng));
        //     } else {
        //         emps.addAll(empRepository.findByEmpKorContaining(empKor));
        //     }
        // } else if (empEng != "") {
        //     emps.addAll(empRepository.findByEmpEngContaining(empEng));
        // } else {
        //     emps.addAll(empRepository.findAllByOrderByEmpBeginDtDesc());
        // }

        return new ResponseEntity<>(emps, HttpStatus.OK);
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
    public ResponseEntity<?> updateEmp(@RequestBody UserRequest empReq) {

        //수정자 정보
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        User resEmp = new User();
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
                _emp.setEmpDiv(empReq.getEmpDiv());
                _emp.setUsername(empReq.getUsername());
                _emp.setEmpImg(empReq.getEmpImg());
                _emp.setEmpTitle(empReq.getEmpTitle());
                _emp.setEmpWorkPhone(empReq.getEmpWorkPhone());
                _emp.setEmpExtenNum(empReq.getEmpExtenNum());
                _emp.setEmpFax(empReq.getEmpFax());
                _emp.setEmpDiv(empReq.getEmpDiv());
                _emp.setEmpTitle(empReq.getEmpTitle());
                _emp.setEmpRole(empReq.getEmpRole());
                _emp.setEmpPhone(empReq.getEmpPhone());
                _emp.setEmpEmail(empReq.getEmpEmail());
                _emp.setEmpEmailBook(empReq.getEmpEmailBook());
                _emp.setEmpAddress(empReq.getEmpAddress());
                _emp.setEmpOffice(empReq.getEmpOffice());
                _emp.setEmpComp(empReq.getEmpComp());
                _emp.setEmpExtenNum(empReq.getEmpExtenNum());
                _emp.setEmpFax(empReq.getEmpFax());
                _emp.setEmpMemo(empReq.getEmpMemo());
                _emp.setEmpStatus(empReq.getEmpStatus());
                _emp.setBackColor(empReq.getBackColor());
                _emp.setFontColor(empReq.getFontColor());
                _emp.setEmpBeginDt(empReq.getEmpBeginDt());
                _emp.setEmpEndDt(empReq.getEmpEndDt());
                _emp.setEmpMemo(empReq.getEmpMemo());
                _emp.setEmpStatus(empReq.getEmpStatus());
                _emp.setCar25seats(empReq.getCar25seats());
                _emp.setCar15seats(empReq.getCar15seats());
                _emp.setCarPersonal(empReq.getCarPersonal());

                //_emp.setModifyUser(modifyingUser);

                if (empReq.getPassword() != null && !empReq.getPassword().equals("")) {
                    _emp.setPassword(encoder.encode(empReq.getPassword()));
                }

                resEmp = empRepository.save(_emp);
            } else {
                System.out.println("수정할 데이터가 없습니다.!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                User _emp = new User();
                _emp.setEmpKor(empReq.getEmpKor());
                _emp.setEmpEng(empReq.getEmpEng());
                _emp.setUsername(empReq.getUsername());
                _emp.setEmpImg(empReq.getEmpImg());
                _emp.setEmpDiv(empReq.getEmpDiv());
                _emp.setEmpTitle(empReq.getEmpTitle());
                _emp.setEmpWorkPhone(empReq.getEmpWorkPhone());
                _emp.setEmpExtenNum(empReq.getEmpExtenNum());
                _emp.setEmpFax(empReq.getEmpFax());
                _emp.setEmpDiv(empReq.getEmpDiv());
                _emp.setEmpTitle(empReq.getEmpTitle());
                _emp.setEmpRole(empReq.getEmpRole());
                _emp.setEmpPhone(empReq.getEmpPhone());
                _emp.setEmpEmail(empReq.getEmpEmail());
                _emp.setEmpEmailBook(empReq.getEmpEmailBook());
                _emp.setEmpAddress(empReq.getEmpAddress());
                _emp.setEmpOffice(empReq.getEmpOffice());
                _emp.setEmpComp(empReq.getEmpComp());
                _emp.setEmpExtenNum(empReq.getEmpExtenNum());
                _emp.setEmpFax(empReq.getEmpFax());
                _emp.setEmpMemo(empReq.getEmpMemo());
                _emp.setEmpStatus(empReq.getEmpStatus());
                _emp.setBackColor(empReq.getBackColor());
                _emp.setFontColor(empReq.getFontColor());
                _emp.setEmpBeginDt(empReq.getEmpBeginDt());
                _emp.setEmpEndDt(empReq.getEmpEndDt());
                _emp.setEmpMemo(empReq.getEmpMemo());
                _emp.setEmpStatus(empReq.getEmpStatus());
                _emp.setCar25seats(empReq.getCar25seats());
                _emp.setCar15seats(empReq.getCar15seats());
                _emp.setCarPersonal(empReq.getCarPersonal());

                if (empReq.getPassword() != null && !empReq.getPassword().equals("")) {
                    _emp.setPassword(encoder.encode(empReq.getPassword()));
                }

                resEmp = empRepository.save(_emp);
            }
        }

        return new ResponseEntity<>(resEmp, HttpStatus.OK);
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
