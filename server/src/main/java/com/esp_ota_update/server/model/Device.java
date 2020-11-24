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

    private int status;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }

    public String getSoftwareNameScheme() {
        return softwareNameScheme;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getLastChecked() {
        return lastChecked;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setSoftwareNameScheme(String softwareNameScheme) {
        this.softwareNameScheme = softwareNameScheme;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLastChecked(LocalDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
