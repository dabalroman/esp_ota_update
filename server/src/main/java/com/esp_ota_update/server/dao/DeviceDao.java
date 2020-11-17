package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Fake dao
//to be replaced with h2
public interface DeviceDao {

    int insertDevice(UUID id, Device device);

    default int insertDevice(Device device) {
        UUID id = UUID.randomUUID();
        return insertDevice(id, device);
    }

    List<Device> selectAllDevices();

    Optional<Device> selectDeviceById(UUID id);

    int deleteDeviceById(UUID id);

    int updateDeviceById(UUID id, Device device);
}
