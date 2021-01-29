package com.esp_ota_update.server.model;

import java.time.LocalDateTime;

public class Device {
    private final Integer id;

    private String name;
    private String mac;
    private String softwareNameScheme;
    private String version;
    private Integer status;
    private LocalDateTime lastSoftwareCheck;
    private LocalDateTime lastSoftwareUpdate;

    public static final int STATUS_NEW = 0;
    public static final int STATUS_UP_TO_DATE = 1;
    public static final int STATUS_NEEDS_UPDATE = 2;
    public static final int STATUS_NO_SOFTWARE = 3;
    public static final int STATUS_UPDATE_IN_PROGRESS = 4;

    public Device(int id) {
        this.id = id;

        //These are defaults values that would be overwritten on db get
        this.status = STATUS_NEW;
        this.lastSoftwareCheck = LocalDateTime.now();
        this.lastSoftwareUpdate = LocalDateTime.now();
    }

    public Device() {
        this(0);
    }

    /**
     * @param update Device
     * @return true on success
     */
    public boolean applyUpdate(Device update) {
        if (update.getId() != 0) {
            return false;
        }

        if (update.getName() != null) {
            this.name = update.getName();
        }

        if (update.getMac() != null) {
            this.mac = update.getMac();
        }

        if (update.getStatus() != null) {
            this.status = update.getStatus();
        }

        if (update.getSoftwareNameScheme() != null) {
            this.softwareNameScheme = update.getSoftwareNameScheme();
        }

        if (update.getLastSoftwareCheck() != null) {
            this.lastSoftwareCheck = update.getLastSoftwareCheck();
        }

        if (update.getLastSoftwareUpdate() != null) {
            this.lastSoftwareUpdate = update.getLastSoftwareUpdate();
        }

        return true;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        if (this.softwareNameScheme == null) {
            this.softwareNameScheme = Software.createSoftwareNameSchemeFromSoftwareName(this.name);
        }
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSoftwareNameScheme() {
        return softwareNameScheme;
    }

    public void setSoftwareNameScheme(String softwareNameScheme) {
        this.softwareNameScheme = softwareNameScheme;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getLastSoftwareCheck() {
        return lastSoftwareCheck;
    }

    public void setLastSoftwareCheck(LocalDateTime lastSoftwareCheck) {
        this.lastSoftwareCheck = lastSoftwareCheck;
    }

    public void setLastSoftwareCheck() {
        this.lastSoftwareCheck = LocalDateTime.now();
    }

    public LocalDateTime getLastSoftwareUpdate() {
        return lastSoftwareUpdate;
    }

    public void setLastSoftwareUpdate(LocalDateTime lastSoftwareUpdate) {
        this.lastSoftwareUpdate = lastSoftwareUpdate;
    }

    public void setLastSoftwareUpdate() {
        this.lastSoftwareUpdate = LocalDateTime.now();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
