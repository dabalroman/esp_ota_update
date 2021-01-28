package com.esp_ota_update.server.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BinaryResponse {
    public final HttpStatus status;
    public HttpHeaders headers;
    public byte[] data = null;

    BinaryResponse(HttpStatus status, HttpHeaders headers, byte[] data) {
        this.status = status;
        this.headers = headers;
        this.data = data;
    }

    BinaryResponse(HttpStatus status) {
        this.status = status;

        this.headers = new HttpHeaders();
        this.headers.set("Content-type", "text/plain; charset=utf8");
    }

    public ResponseEntity<byte[]> responseEntity() {
        return ResponseEntity
                .status(this.status)
                .headers(this.headers)
                .body(data);
    }
}
