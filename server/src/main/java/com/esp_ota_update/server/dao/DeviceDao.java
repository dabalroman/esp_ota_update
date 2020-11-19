package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceDao {

    int insertDevice(Device device);

    List<Device> selectAllDevices();

    Optional<Device> selectDeviceById(int id);

    int deleteDeviceById(int id);

    int updateDeviceById(int id, Device device);
}
