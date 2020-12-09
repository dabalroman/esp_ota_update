package com.esp_ota_update.server.api;

import com.esp_ota_update.server.service.DeviceUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/up/")
@RestController
public class DeviceUpdateHandle {

    private final DeviceUpdateService deviceUpdateService;

    public DeviceUpdateHandle(DeviceUpdateService deviceUpdateService) {
        this.deviceUpdateService = deviceUpdateService;
    }

    @GetMapping(path = "{mac}/{currentVersion}")
    public ResponseEntity<Response> handleDeviceUpdate(@PathVariable("mac") String mac, @PathVariable("currentVersion") String currentVersion) {
//        List<Software> data = softwareService.getSoftwareById(id);
//        HttpStatus httpStatus = !data.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

//        return new Response(data.toArray(), true, httpStatus).get();
        return new Response(true, HttpStatus.I_AM_A_TEAPOT).get();
    }
}
