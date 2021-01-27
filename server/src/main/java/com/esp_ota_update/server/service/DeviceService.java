package com.esp_ota_update.server.service;

import com.esp_ota_update.server.dao.DeviceDao;
import com.esp_ota_update.server.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceDao deviceDao;

    @Autowired
    public DeviceService(@Qualifier("H2-Device") DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    public void addDevice(Device device) {
        deviceDao.insertDevice(device);
    }

    public List<Device> getAllDevices() {
        return deviceDao.selectAllDevices();
    }

    public List<Device> getDeviceById(int id) {
        return deviceDao.selectDeviceById(id);
    }

    public List<Device> getDeviceBySoftwareName(String softwareName) {
        return deviceDao.selectDeviceBySoftwareName(softwareName);
    }

    public List<Device> getDeviceByMac(String mac) { return deviceDao.selectDeviceByMac(mac); }

    public void deleteDevice(int id) {
        deviceDao.deleteDeviceById(id);
    }

    public void updateDevice(Device device) {
        deviceDao.updateDeviceById(device);
    }
}
