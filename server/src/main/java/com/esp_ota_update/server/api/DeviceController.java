package com.esp_ota_update.server.api;

import com.esp_ota_update.server.model.Device;
import com.esp_ota_update.server.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/v1/device")
@RestController
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public Response addDevice(@Valid @NonNull @RequestBody Device device) {
        deviceService.addDevice(device);

        return new Response(true, HttpStatus.OK);
    }

    @GetMapping
    public Response getAllDevices() {
        List<Device> data = deviceService.getAllDevices();
        HttpStatus httpStatus = !data.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new Response(data.toArray(), true, httpStatus);
    }

    @GetMapping(path = "{id}")
    public Response getDeviceById(@PathVariable("id") int id) {
        List<Device> data = deviceService.getDeviceById(id);
        HttpStatus httpStatus = !data.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new Response(data.toArray(), true, httpStatus);
    }

    @DeleteMapping(path = "{id}")
    public Response deleteDeviceById(@PathVariable("id") int id) {
        deviceService.deleteDevice(id);

        return new Response(true, HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public Response updateDeviceById(@PathVariable("id") int id, @Valid @NonNull @RequestBody Device device) {
        deviceService.updateDevice(id, device);

        return new Response(true, HttpStatus.OK);
    }
}
