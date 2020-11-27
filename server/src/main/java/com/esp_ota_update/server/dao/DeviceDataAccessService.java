package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Device;
import com.esp_ota_update.server.model.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SqlResolve")
@Repository("H2-Device")
public class DeviceDataAccessService implements DeviceDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DeviceDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertDevice(Device device) {
        final String sql =
                "INSERT INTO device (name, mac, software_name_scheme, status, last_checked, last_updated) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                device.getName(),
                device.getMac(),
                device.getSoftwareNameScheme(),
                device.getStatus(),
                device.getLastChecked(),
                device.getLastUpdated()
        );
    }

    @Override
    public List<Device> selectAllDevices() {
        final String sql = "SELECT id, name, mac, software_name_scheme, status, last_checked, last_updated FROM device";

        return jdbcTemplate.query(sql, new DeviceMapper());
    }

    @Override
    public List<Device> selectDeviceById(int id) {
        final String sql = "SELECT id, name, mac, software_name_scheme, status, last_checked, last_updated"
                + " FROM device WHERE id = ?";

        return jdbcTemplate.query(sql, new DeviceMapper(), id);
    }

    @Override
    public int deleteDeviceById(int id) {
        final String sql = "DELETE FROM device WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateDeviceById(int id, Device device) {
        final String sql = "UPDATE device SET name = ?, mac = ?, software_name_scheme = ?, status = ?, "
                + "last_checked = ?, last_updated = ? WHERE ID = ?";

        return jdbcTemplate.update(
                sql,
                device.getName(),
                device.getMac(),
                device.getSoftwareNameScheme(),
                device.getStatus(),
                device.getLastChecked(),
                device.getLastUpdated(),
                id
        );
    }
}

