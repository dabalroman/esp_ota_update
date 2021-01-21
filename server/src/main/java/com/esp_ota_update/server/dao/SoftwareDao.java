package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Software;

import java.util.List;

public interface SoftwareDao {
    int insertSoftware(Software software);

    List<Software> selectAllSoftware();

    List<Software> selectSoftwareById(int id);

    List<Software> selectSoftwareByDeviceId(int deviceId);

    int deleteSoftwareById(int id);

    int updateSoftware(Software software);
}
