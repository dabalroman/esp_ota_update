package com.esp_ota_update.server.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceMapper implements RowMapper<Device> {

    @Override
    public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
        Device device = new Device(rs.getInt("id"));

        device.setName(rs.getString("name"));
        device.setLastSoftwareCheck(rs.getTimestamp("last_software_check").toLocalDateTime());
        device.setLastSoftwareUpdate(rs.getTimestamp("last_software_update").toLocalDateTime());

        device.setMac(rs.getString("mac"));
        if(rs.wasNull()){
            device.setMac(null);
        }

        device.setSoftwareNameScheme(rs.getString("software_name_scheme"));
        if(rs.wasNull()){
            device.setSoftwareNameScheme(null);
        }

        device.setStatus(rs.getInt("status"));
        if(rs.wasNull()){
            device.setStatus(null);
        }

        return device;
    }
}
