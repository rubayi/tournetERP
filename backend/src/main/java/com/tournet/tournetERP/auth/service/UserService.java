package com.tournet.tournetERP.auth.service;

import com.tournet.tournetERP.auth.dto.UserRequest;
import com.tournet.tournetERP.auth.dto.UserResponse;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.repository.EmpJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : UserService
 * @since : 2024-03-14
 */
@Service
@Transactional
public class UserService {

    @Autowired
    EmpJpaRepository empRepository;
    public List<UserResponse> findEmpsList(UserRequest empReq) {

        ModelMapper modelMapper=new ModelMapper();

        long empStatus = empReq.getEmpStatus();
        String empKor = empReq.getEmpKor();
        String empEng = empReq.getEmpEng();
        String username = empReq.getUsername();

        List<User> selectedUsers = empRepository.findByEmpStatusOrEmpKorOrEmpEngOrUsernameOrderByModifiedDtDesc(
                empStatus == 0 ? 0 : empStatus,
                empKor.isEmpty() ? null : empKor,
                empEng.isEmpty() ? null : empEng,
                username.isEmpty() ? null : username
        );

        List<UserResponse> userResList = selectedUsers.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setEmpUuid(user.getEmpUuid());
                    userResponse.setUsername(user.getUsername());
                    userResponse.setEmpKor(user.getEmpKor());
                    userResponse.setEmpEng(user.getEmpEng());
                    userResponse.setEmpImg(user.getEmpImg());
                    
                    userResponse.setEmpWorkTypeName(user.getEmpWorkTypeName().getCodeKr() != "" ? user.getEmpWorkTypeName().getCodeKr() : "");
                    userResponse.setEmpDivName(user.getEmpDivName().getCodeKr() != "" ? user.getEmpDivName().getCodeKr() : "");
                    userResponse.setEmpTitleName(user.getEmpTitleName().getCodeKr() != "" ? user.getEmpTitleName().getCodeKr() : "");
                    userResponse.setEmpRoleName(user.getEmpRoleName().getCodeKr() != "" ? user.getEmpRoleName().getCodeKr() : "");
                    userResponse.setEmpDobTypeName(user.getEmpDobTypeName().getCodeKr() != "" ? user.getEmpDobTypeName().getCodeKr() : "");
                    userResponse.setEmpStatusName(user.getEmpStatusName().getCodeKr() != "" ? user.getEmpStatusName().getCodeKr() : "");


                    userResponse.setEmpWorkType(user.getEmpWorkType());
                    userResponse.setEmpDiv(user.getEmpDiv());
                    userResponse.setEmpTitle(user.getEmpTitle());
                    userResponse.setEmpRole(user.getEmpRole());
                    userResponse.setEmpDobType(user.getEmpDobType());
                    userResponse.setEmpStatus(user.getEmpStatus());

                    userResponse.setEmpPhone(user.getEmpPhone());
                    userResponse.setEmpWorkPhone(user.getEmpWorkPhone());
                    userResponse.setEmpEmailBook(user.getEmpEmailBook());
                    userResponse.setEmpAddress1(user.getEmpAddress1());
                    userResponse.setEmpAddress2(user.getEmpAddress2());
                    userResponse.setEmpCity(user.getEmpCity());
                    userResponse.setEmpState(user.getEmpState());
                    userResponse.setEmpZip(user.getEmpZip());
                    userResponse.setEmpDob(user.getEmpDob());
                    userResponse.setEmpMemo(user.getEmpMemo());
                    userResponse.setBackColor(user.getBackColor());
                    userResponse.setFontColor(user.getFontColor());

                    userResponse.setEmpBeginDt(user.getEmpBeginDt());
                    userResponse.setModifiedDt(user.getModifiedDt());
                    userResponse.setEmpEmail(user.getEmpEmail());
                   
                    return userResponse;
                })
                .collect(Collectors.toList());
//        List<UserResponse> userResList = null;
        return userResList;
    }


    public UserResponse findByEmpUuid(int id) {
        ModelMapper modelMapper=new ModelMapper();

        UserResponse findUser = modelMapper.map(empRepository.findFirstByEmpUuid(id), UserResponse.class);

        return findUser;
    }
}
