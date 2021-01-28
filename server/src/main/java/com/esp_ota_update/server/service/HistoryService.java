package com.esp_ota_update.server.service;

import com.esp_ota_update.server.dao.HistoryDao;
import com.esp_ota_update.server.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryDao historyDao;

    @Autowired
    public HistoryService(@Qualifier("H2-History") HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    public List<History> getHistoryByDeviceId(int deviceId) {
        return historyDao.selectHistoryByDeviceId(deviceId);
    }
}
