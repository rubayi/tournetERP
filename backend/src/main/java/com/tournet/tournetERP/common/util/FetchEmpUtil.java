package com.tournet.tournetERP.common.util;

import com.tournet.tournetERP.auth.entity.User;
import com.tournet.tournetERP.auth.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchEmpUtil {
 
    @Autowired
    EmpRepository empRepository;

    public String fetchEmpKor(long empUuid) {
        User emp = empRepository.findFirstByEmpUuid(empUuid);
        if (emp != null) {
            return emp.getEmpKor();
        } else {
            return null;
        }
    }

    public String fetchEmpEng(long empUuid) {
        User emp = empRepository.findFirstByEmpUuid(empUuid);
        if (emp != null) {
            return emp.getEmpEng();
        } else {
            return null;
        }
    }
}
