package io.hsjang.template.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HException extends ResponseStatusException{

    private static final long serialVersionUID = 1L;
    
    public HException(HttpStatus status){
        super(status);
    }

    public HException(HttpStatus status, String message){
        super(status,message);
    }

    public HException(HttpStatus status, String message, String... params){
        super(status,message);
    }
}