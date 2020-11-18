package com.esp_ota_update.server.api;

import com.esp_ota_update.server.model.Device;
import com.esp_ota_update.server.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/device")
@RestController
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public void addDevice(@Valid @NonNull @RequestBody Device device) {
        deviceService.addDevice(device);
    }

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping(path = "{id}")
    public Device getDeviceById(@PathVariable("id") UUID id) {
        return deviceService.getDeviceById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteDeviceById(@PathVariable("id") UUID id) {
        deviceService.deleteDevice(id);
    }

    @PutMapping(path = "{id}")
    public void updateDeviceById(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Device device) {
        deviceService.updateDevice(id, device);
    }
}
