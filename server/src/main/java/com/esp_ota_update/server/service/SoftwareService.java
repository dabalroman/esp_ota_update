package com.esp_ota_update.server.service;

import com.esp_ota_update.server.dao.SoftwareDao;
import com.esp_ota_update.server.model.Software;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareService {

    private final SoftwareDao softwareDao;

    @Autowired
    public SoftwareService(@Qualifier("H2-Software") SoftwareDao softwareDao) {
        this.softwareDao = softwareDao;
    }

    public void addSoftware(Software software) {
        softwareDao.insertSoftware(software);
    }

    public List<Software> getAllSoftware() {
        return softwareDao.selectAllSoftware();
    }

    public List<Software> getSoftwareById(int id) {
        return softwareDao.selectSoftwareById(id);
    }

    public List<Software> getSoftwareByDeviceIdAndVersion(int deviceId, String version) {
        return softwareDao.selectSoftwareByDeviceIdAndVersion(deviceId, version);
    }

    public List<Software> getLatestSoftwareByDeviceId(int deviceId) {
        return softwareDao.selectLatestSoftwareByDeviceId(deviceId);
    }

    public void deleteSoftware(int id) {
        softwareDao.deleteSoftwareById(id);
    }

    public void updateSoftware(Software software) {
        softwareDao.updateSoftware(software);
    }
}
