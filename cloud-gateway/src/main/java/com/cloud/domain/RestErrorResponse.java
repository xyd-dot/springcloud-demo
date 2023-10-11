package com.cloud.domain;

import java.io.Serializable;

/**
 * @description:
 * @since: 2023/10/10 19:19
 * @author: sdw
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
