package com.esp_ota_update.server.api;

import com.esp_ota_update.server.service.DeviceUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings({"FieldCanBeLocal", "BooleanMethodIsAlwaysInverted"})
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
            return new Response(false, HttpStatus.BAD_REQUEST).getResponseEntity();
        }

//        List<Software> data = softwareService.getSoftwareById(id);
//        HttpStatus httpStatus = !data.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

//        return new Response(data.toArray(), true, httpStatus).get();
        return new Response(true, HttpStatus.I_AM_A_TEAPOT).getResponseEntity();
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

        if (!this.isValidMAC(headers.get(HEADER_AP_MAC)) || !this.isValidMAC(headers.get(HEADER_STA_MAC)))
            return false;

        if (!this.isValidVersion(HEADER_SDK_VERSION) || !this.isValidVersion(HEADER_SOFTWARE_VERSION))
            return false;

        return headers.get(HEADER_MODE).equals("sketch");
    }

    private boolean isValidMAC(String s) {
        Pattern MAC = Pattern.compile("^(?:[A-F0-9]{2}:){5}[A-F0-9]{2}$");
        return MAC.matcher(s).matches();
    }

    private boolean isValidVersion(String s){
        Pattern MAC = Pattern.compile("^\\d+\\.\\d+\\.\\d+$");
        return MAC.matcher(s).matches();
    }
}
