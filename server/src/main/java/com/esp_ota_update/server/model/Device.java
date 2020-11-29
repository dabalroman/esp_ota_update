package com.esp_ota_update.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class Device {
    private final Integer id;

    @NotBlank
    private String name;

    private String mac;

    private String softwareNameScheme;

    private Integer status;

    private LocalDateTime lastSoftwareCheck;

    private LocalDateTime lastSoftwareUpdate;

    public Device(int id) {
        this.id = id;
    }

    public Device(@JsonProperty("name") String name) {
        this.id = null;
        this.name = name;
        this.mac = null;
        this.softwareNameScheme = null;
        this.status = null;
        this.lastSoftwareCheck = LocalDateTime.now();
        this.lastSoftwareUpdate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDateTime getLastSoftwareUpdate() {
        return lastSoftwareUpdate;
    }

    public void setLastSoftwareUpdate(LocalDateTime lastSoftwareUpdate) {
        this.lastSoftwareUpdate = lastSoftwareUpdate;
    }
}
