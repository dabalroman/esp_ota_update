package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Device;

import java.util.UUID;

public interface DeviceDao {

    int insertDevice(UUID id, Device device);

    default int insertDevice(Device device) {
        UUID id = UUID.randomUUID();
        return insertDevice(id, device);
    }

}
