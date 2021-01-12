package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Device;

import java.util.List;

public interface DeviceDao {

    int insertDevice(Device device);

    List<Device> selectAllDevices();

    List<Device> selectDeviceById(int id);

    int deleteDeviceById(int id);

    int updateDeviceById(Device device);
}
