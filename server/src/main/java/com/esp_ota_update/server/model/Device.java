package com.esp_ota_update.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class Device {
    private final int id;

    @NotBlank
    private final String name;

    private final String mac;

    private final String softwareNameScheme;

    private final int status;

    private final LocalDateTime lastChecked;

    private final LocalDateTime lastUpdated;


    public Device(
            int id,
            String name,
            String mac,
            String softwareNameScheme,
            int status,
            LocalDateTime lastChecked,
            LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.mac = mac;
        this.softwareNameScheme = softwareNameScheme;
        this.status = status;
        this.lastChecked = lastChecked;
        this.lastUpdated = lastUpdated;
    }

    public Device(
            @JsonProperty("name") String name,
            @JsonProperty("software_name_scheme") String softwareNameScheme) {
        this.id = -1;
        this.name = name;
        this.mac = "";
        this.softwareNameScheme = softwareNameScheme;
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


}
