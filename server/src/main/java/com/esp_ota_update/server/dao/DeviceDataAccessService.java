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
    public Optional<Device> selectDeviceById(UUID id) {
        return DB.stream()
                .filter(device -> device.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteDeviceById(UUID id) {
        Optional<Device> device = selectDeviceById(id);

        if(device.isPresent()){
            DB.remove(device.get());
            return 1;
        }

        return 0;
    }

    @Override
    public int updateDeviceById(UUID id, Device deviceToUpdate) {
        return selectDeviceById(id)
                .map(device -> {
                    int deviceIndex = DB.indexOf(device);
                    if (deviceIndex >= 0) {
                        DB.set(deviceIndex, new Device(id, deviceToUpdate.getName()));
                        return 1;
                    }

                    return 0;
                }).orElse(0);
    }
}
