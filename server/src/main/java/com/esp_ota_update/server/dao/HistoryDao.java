package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.History;

import java.util.List;

public interface HistoryDao {
    List<History> selectHistoryByDeviceId(int deviceId);
}
