package com.tournet.tournetERP.auth.service;

import com.tournet.tournetERP.auth.dto.UserRequest;
import com.tournet.tournetERP.auth.dto.UserResponse;
import com.tournet.tournetERP.auth.entity.EmpMenuAuth;
import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.repository.EmpMenuAuthRepository;
import com.tournet.tournetERP.auth.repository.EmpRepository;
import com.tournet.tournetERP.common.entity.ComCode;
import com.tournet.tournetERP.common.repository.ComCodeRepository;
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
    EmpRepository empRepository;


    @Autowired
    ComCodeRepository comcodeRepository;

    public List<UserResponse> findEmpsList(UserRequest empReq) {

        long empStatus = empReq.getEmpStatus();
        String empKor = empReq.getEmpKor();
        String empEng = empReq.getEmpEng();
        String username = empReq.getUsername();

        List<User> selectedUsers = empRepository.findByEmpStatusOrEmpKorOrEmpEngOrUsernameOrderByModifiedDtDesc(
                empStatus == 0 ? null : empStatus,
                empKor.isEmpty() ? null : empKor,
                empEng.isEmpty() ? null : empEng,
                username.isEmpty() ? null : username);

        List<UserResponse> userResList = selectedUsers.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setEmpUuid(user.getEmpUuid());
                    userResponse.setUsername(user.getUsername());
                    userResponse.setEmpKor(user.getEmpKor());
                    userResponse.setEmpEng(user.getEmpEng());
                    userResponse.setEmpImg(user.getEmpImg());
                    userResponse.setEmpDivName(fetchCodeKr(user.getEmpDiv()));
                    userResponse.setEmpTitleName(fetchCodeKr(user.getEmpTitle()));
                    userResponse.setEmpRoleName(fetchCodeKr(user.getEmpRole()));
                    userResponse.setEmpStatusName(fetchCodeKr(user.getEmpStatus()));
                    userResponse.setEmpCountryName(fetchCodeKr(user.getEmpOffice()));
                    userResponse.setEmpCountryName(fetchCodeKr(user.getEmpComp()));
                    userResponse.setEmpDiv(user.getEmpDiv());
                    userResponse.setEmpTitle(user.getEmpTitle());
                    userResponse.setEmpRole(user.getEmpRole());
                    userResponse.setEmpStatus(user.getEmpStatus());
                    userResponse.setEmpPhone(user.getEmpPhone());
                    userResponse.setEmpWorkPhone(user.getEmpWorkPhone());
                    userResponse.setEmpEmailBook(user.getEmpEmailBook());
                    userResponse.setEmpAddress(user.getEmpAddress());
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

    public UserResponse findByEmpUuid(long id) {
        ModelMapper modelMapper=new ModelMapper();

        UserResponse findUser = modelMapper.map(empRepository.findFirstByEmpUuid(id), UserResponse.class);

        return findUser;
    }

    private String fetchCodeKr(long codeUuid) {
        ComCode comcode = comcodeRepository.findFirstByCodeUuid(codeUuid);
        if (comcode != null) {
            return comcode.getCodeKr();
        } else {
            return null;
        }
    }
}
