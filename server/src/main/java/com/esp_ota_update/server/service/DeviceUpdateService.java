package com.esp_ota_update.server.service;

import com.esp_ota_update.server.dao.UpdateDao;
import com.esp_ota_update.server.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceUpdateService {

    private final UpdateDao updateDao;

    @Autowired
    public DeviceUpdateService(@Qualifier("H2-Update") UpdateDao updateDao) {
        this.updateDao = updateDao;
    }

    public List<Update> getLatestDeviceUpdate(int deviceId) {
        return updateDao.selectLatestDeviceUpdateById(deviceId);
    }

}
