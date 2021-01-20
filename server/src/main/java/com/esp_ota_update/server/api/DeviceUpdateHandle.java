package com.esp_ota_update.server.api;

import com.esp_ota_update.server.service.DeviceUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SuppressWarnings("FieldCanBeLocal")
@RequestMapping("api/v1/up/")
@RestController
public class DeviceUpdateHandle {

    private final DeviceUpdateService deviceUpdateService;

    private final String HEADER_USER_AGENT = "user-agent";
    private final String HEADER_STA_MAC = "x-esp8266-sta-mac";
    private final String HEADER_AP_MAC = "x-esp8266-ap-mac";
    private final String HEADER_FREE_SPACE = "x-esp8266-free-space";
    private final String HEADER_SKETCH_SIZE = "x-esp8266-sketch-size";
    private final String HEADER_SKETCH_MD5 = "x-esp8266-sketch-md5";
    private final String HEADER_CHIP_SIZE = "x-esp8266-chip-size";
    private final String HEADER_SDK_VERSION = "x-esp8266-sdk-version";
    private final String HEADER_SOFTWARE_VERSION = "x-esp8266-version";
    private final String HEADER_MODE = "x-esp8266-mode";

    public DeviceUpdateHandle(DeviceUpdateService deviceUpdateService) {
        this.deviceUpdateService = deviceUpdateService;
    }

    @GetMapping
    public ResponseEntity<Response> handleDeviceUpdate(@RequestHeader Map<String, String> headers) {
        if(!this.verifyHeaders(headers)){
            return new Response(false, HttpStatus.BAD_REQUEST).get();
        }

//        List<Software> data = softwareService.getSoftwareById(id);
//        HttpStatus httpStatus = !data.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

//        return new Response(data.toArray(), true, httpStatus).get();
        return new Response(true, HttpStatus.I_AM_A_TEAPOT).get();
    }

    private boolean verifyHeaders(Map<String, String> headers) {
        for(Map.Entry<String, String> entry : headers.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        if (headers.get(HEADER_USER_AGENT) == null
                || headers.get(HEADER_STA_MAC) == null
                || headers.get(HEADER_AP_MAC) == null
                || headers.get(HEADER_FREE_SPACE) == null
                || headers.get(HEADER_SKETCH_SIZE) == null
                || headers.get(HEADER_SKETCH_MD5) == null
                || headers.get(HEADER_CHIP_SIZE) == null
                || headers.get(HEADER_SDK_VERSION) == null
                || headers.get(HEADER_SOFTWARE_VERSION) == null
                || headers.get(HEADER_MODE) == null) {
            return false;
        }

        if (!headers.get(HEADER_USER_AGENT).equals("ESP8266-http-Update"))
            return false;

        if (this.isNotValidMAC(headers.get(HEADER_AP_MAC)) || this.isNotValidMAC(headers.get(HEADER_STA_MAC)))
            return false;

        return headers.get(HEADER_MODE).equals("sketch");
    }

    private boolean isNotValidMAC(String s) {
        if (s.length() != 17) {
            return true;
        }

        for (int i = 0; i < 17; i++) {
            int current = s.charAt(i);
            if ((i + 1) % 3 == 0) {
                if (current != ':') {
                    return true;
                }
            } else {
                if (!(current >= '0' && current <= '9' || current >= 'A' && current <= 'F')) {
                    return true;
                }
            }
        }

        return false;
    }


}
