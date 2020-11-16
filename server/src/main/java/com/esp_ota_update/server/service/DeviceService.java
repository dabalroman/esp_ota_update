package com.esp_ota_update.server.service;

import com.esp_ota_update.server.dao.DeviceDao;
import com.esp_ota_update.server.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceDao deviceDao;

    @Autowired
    public DeviceService(@Qualifier("deviceDao") DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    public int addDevice(Device device) {
        return deviceDao.insertDevice(device);
    }

}
