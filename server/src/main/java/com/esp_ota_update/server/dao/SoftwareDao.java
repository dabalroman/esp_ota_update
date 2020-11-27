package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Software;

import java.util.List;

public interface SoftwareDao {
    int insertSoftware(Software device);

    List<Software> selectAllSoftware();

    List<Software> selectSoftwareById(int id);

    int deleteSoftwareById(int id);

    int updateSoftwareById(int id, Software device);
}
