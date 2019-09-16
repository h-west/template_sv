package io.hsjang.template.common;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;

import io.hsjang.template.common.exception.HException;

public enum ResponseCode {
    OK(HttpStatus.OK,"OK"),
    PARAM_CHECK(HttpStatus.BAD_REQUEST,"Bad Request!! {}"),
    ETC(HttpStatus.INTERNAL_SERVER_ERROR,"Internal Server Error");

    HttpStatus httpStatus;
    String message;
    ResponseCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HException ex(){
        throw new HException(httpStatus, message);
    }

    public HException exParams(String... params){
        throw new HException(httpStatus, MessageFormatter.arrayFormat(message, params).getMessage());
    }

}