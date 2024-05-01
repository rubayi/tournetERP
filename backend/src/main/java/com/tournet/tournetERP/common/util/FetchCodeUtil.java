package com.tournet.tournetERP.common.util;

import com.tournet.tournetERP.common.entity.ComCode;
import com.tournet.tournetERP.common.repository.ComCodeRepository;
import com.tournet.tournetERP.contents.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : FetchCodeUtil
 * @since : 2024-04-05
 */
@Component
public class FetchCodeUtil {

    @Autowired
    ComCodeRepository comcodeRepository;

    public String fetchCodeKr(long codeUuid) {
        ComCode comcode = comcodeRepository.findFirstByCodeUuid(codeUuid);
        if (comcode != null) {
            return comcode.getCodeKr();
        } else {
            return null;
        }
    }

    public String uprFetchCodeKr(long codeUuid) {
        ComCode comcode = comcodeRepository.findFirstUprNameByCodeUuid(codeUuid);
        if (comcode != null) {
            return comcode.getCodeKr();
        } else {
            return null;
        }
    }

    public String uprFetchCodeEn(long codeUuid) {
        ComCode comcode = comcodeRepository.findFirstUprNameByCodeUuid(codeUuid);
        if (comcode != null) {
            return comcode.getCodeEn();
        } else {
            return null;
        }
    }

    public String fetchCodeEn(long codeUuid) {
        ComCode comcode = comcodeRepository.findFirstByCodeUuid(codeUuid);
        if (comcode != null) {
            return comcode.getCodeEn();
        } else {
            return null;
        }
    }
}
