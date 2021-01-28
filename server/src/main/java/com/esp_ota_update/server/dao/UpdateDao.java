package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Update;

import java.util.List;

public interface UpdateDao {
    List<Update> selectLatestDeviceUpdateById(int deviceId, boolean successful);

    int insertDeviceUpdate(Update update);

    int updateDeviceUpdate(Update update);
}
