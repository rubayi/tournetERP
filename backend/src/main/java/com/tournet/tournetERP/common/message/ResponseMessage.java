package com.tournet.tournetERP.common.message;

import lombok.Data;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : ResponseMessage
 * @since : 2024-04-03
 */
@Data
public class ResponseMessage {

    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }
}
