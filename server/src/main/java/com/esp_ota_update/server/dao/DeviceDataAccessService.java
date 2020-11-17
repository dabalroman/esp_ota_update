package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Device;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("deviceDao")
public class DeviceDataAccessService implements DeviceDao {

    private static List<Device> DB = new ArrayList<>();

    @Override
    public int insertDevice(UUID id, Device device) {
        DB.add(new Device(id, device.getName()));
        return 0;
    }

    @Override
    public List<Device> selectAllDevices() {
        return DB;
    }

    @Override
    public Optional<Device> selectPersonById(UUID id) {
        return DB.stream()
                .filter(device -> device.getId().equals(id))
                .findFirst();
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
