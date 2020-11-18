package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Device;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("H2")
public class H2DeviceDataAccessService implements DeviceDao {

    @Override
    public int insertDevice(UUID id, Device device) {
        return 0;
    }

    @Override
    public List<Device> selectAllDevices() {
        return List.of(new Device(UUID.randomUUID(), "FROM H2"));
    }

    @Override
    public Optional<Device> selectDeviceById(UUID id) {
        return Optional.empty();
    }

    @Override
    public int deleteDeviceById(UUID id) {
        return 0;
    }

    @Override
    public int updateDeviceById(UUID id, Device device) {
        return 0;
    }
}
