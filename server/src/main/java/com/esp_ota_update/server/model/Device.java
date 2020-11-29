package com.esp_ota_update.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class Device {
    private final int id;

    @NotBlank
    private String name;

    private String mac;

    private String softwareNameScheme;

    private Integer status;

    private LocalDateTime lastChecked;

    private LocalDateTime lastUpdated;

    public Device(int id) {
        this.id = id;
    }

    public Device(@JsonProperty("name") String name) {
        this.id = -1;
        this.name = name;
        this.mac = "";
        this.softwareNameScheme = "";
        this.status = 0;
        this.lastChecked = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
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

    public LocalDateTime getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(LocalDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
