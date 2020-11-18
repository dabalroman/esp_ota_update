package com.esp_ota_update.server.service;

import com.esp_ota_update.server.dao.DeviceDao;
import com.esp_ota_update.server.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceDao deviceDao;

    @Autowired
    public DeviceService(@Qualifier("H2") DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    public int addDevice(Device device) {
        return deviceDao.insertDevice(device);
    }

    public List<Device> getAllDevices() {
        return deviceDao.selectAllDevices();
    }

    public Optional<Device> getDeviceById(UUID id) {
        return deviceDao.selectDeviceById(id);
    }

    public int deleteDevice(UUID id){
        return deviceDao.deleteDeviceById(id);
    }

    public int updateDevice(UUID id, Device device){
        return deviceDao.updateDeviceById(id, device);
    }

}
