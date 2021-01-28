package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Update;
import com.esp_ota_update.server.model.UpdateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SqlResolve")
@Repository("H2-Update")
public class UpdateDataAccessService implements UpdateDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UpdateDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertDeviceUpdate(Update update) {
        final String sql =
                "INSERT INTO `update` (software_from, software_to, device_id, timestamp, status) " +
                        "VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                update.getSoftware_from(),
                update.getSoftware_to(),
                update.getDeviceId(),
                update.getTimestamp(),
                update.getStatus()
        );
    }

    @Override
    public int updateDeviceUpdate(Update update) {
        final String sql = "UPDATE `update` SET software_from = ?, software_to = ?, device_id = ?, "
                + "timestamp = ?, status = ? WHERE ID = ?";

        return jdbcTemplate.update(
                sql,
                update.getSoftware_from(),
                update.getSoftware_to(),
                update.getDeviceId(),
                update.getTimestamp(),
                update.getStatus(),
                update.getId()
        );
    }

    @Override
    public List<Update> selectLatestDeviceUpdateById(int deviceId) {
        final String sql = "SELECT id, timestamp, device_id, software_from, software_to, status"
                + " FROM `update` WHERE device_id = ? ORDER BY id DESC LIMIT 1";

        return jdbcTemplate.query(sql, new UpdateMapper(), deviceId);
    }
}


