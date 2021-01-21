package com.esp_ota_update.server.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    public final boolean success;
    public final int status;
    public Object[] data;

    Response(Object[] data, boolean success, HttpStatus status){
        this.success = success;
        this.data = data;
        this.status = status.value();
    }

    Response(boolean success, HttpStatus status){
        this.success = success;
        this.status = status.value();
    }

    public ResponseEntity<Response> responseEntity(){
        return ResponseEntity
                .status(this.status)
                .body(this);
    }
}
