package com.esp_ota_update.server.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceMapper implements RowMapper<Device> {

    @Override
    public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
        Device device = new Device(rs.getInt("id"));

        device.setMac(rs.getString("mac"));
        device.setName(rs.getString("name"));
        device.setSoftwareNameScheme(rs.getString("software_name_scheme"));
        device.setStatus(rs.getInt("status"));
        device.setLastChecked(rs.getTimestamp("last_checked").toLocalDateTime());
        device.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());

        return device;
    }
}
