package com.esp_ota_update.server.api;

import com.esp_ota_update.server.model.Device;
import com.esp_ota_update.server.model.Software;
import com.esp_ota_update.server.model.Update;
import com.esp_ota_update.server.service.DeviceService;
import com.esp_ota_update.server.service.DeviceUpdateService;
import com.esp_ota_update.server.service.SoftwareService;
import com.esp_ota_update.server.util.MD5Checksum;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings({"FieldCanBeLocal", "BooleanMethodIsAlwaysInverted"})
@RequestMapping("api/v1/up")
@RestController
@Configuration
@EnableScheduling
public class DeviceUpdateHandler {

    private final DeviceUpdateService deviceUpdateService;
    private final DeviceService deviceService;
    private final SoftwareService softwareService;

    private final String HEADER_USER_AGENT = "user-agent";
    private final String HEADER_DEVICE_MAC = "x-esp8266-sta-mac";
    private final String HEADER_AP_MAC = "x-esp8266-ap-mac";
    private final String HEADER_FREE_SPACE = "x-esp8266-free-space";
    private final String HEADER_SKETCH_SIZE = "x-esp8266-sketch-size";
    private final String HEADER_SKETCH_MD5 = "x-esp8266-sketch-md5";
    private final String HEADER_CHIP_SIZE = "x-esp8266-chip-size";
    private final String HEADER_SDK_VERSION = "x-esp8266-sdk-version";
    private final String HEADER_SOFTWARE_VERSION = "x-esp8266-version";
    private final String HEADER_MODE = "x-esp8266-mode";

    public DeviceUpdateHandler(
            DeviceUpdateService deviceUpdateService,
            DeviceService deviceService,
            SoftwareService softwareService
    ) {
        this.deviceUpdateService = deviceUpdateService;
        this.deviceService = deviceService;
        this.softwareService = softwareService;
    }

    @GetMapping
    public ResponseEntity<byte[]> handleDeviceUpdate(@RequestHeader Map<String, String> headers) {
        if (!this.verifyHeaders(headers)) {
            System.out.println("\nUpdate request denied - wrong headers.");
            return new BinaryResponse(HttpStatus.FORBIDDEN).responseEntity();
        }

        scanSoftwareFolder();

        System.out.println("\nUpdate request from " + headers.get(HEADER_DEVICE_MAC) + ", version "
                + headers.get(HEADER_SOFTWARE_VERSION));

        try {
            List<Device> deviceAsList = deviceService.getDeviceByMac(headers.get(HEADER_DEVICE_MAC));
            boolean unknownDevice = deviceAsList.isEmpty();

            if (unknownDevice) {
                //Self-introduce path
                Device device = new Device();
                device.setMac(headers.get(HEADER_DEVICE_MAC));
                device.setName(Software.extractNameFromNameString(headers.get(HEADER_SOFTWARE_VERSION)));
                device.setVersion(Software.extractVersionFromNameString(headers.get(HEADER_SOFTWARE_VERSION)));
                deviceService.addDevice(device);

                System.out.println("    OK. Introduced new device " + device.getName());
                return new BinaryResponse(HttpStatus.NOT_MODIFIED).responseEntity();
            }

            //Known device path
            Device device = deviceAsList.get(0);
            System.out.println("    Device recognized as " + device.getName());

            List<Update> updateAsList = deviceUpdateService.getLatestDeviceUpdate(device.getId());
            boolean firstUpdate = updateAsList.isEmpty();

            //Check if device was updated successfully
            if (!firstUpdate) {
                Update lastUpdate = updateAsList.get(0);
                List<Software> lastSoftwareAsList = softwareService.getSoftwareById(lastUpdate.getSoftware_to());
                Software lastSoftware = lastSoftwareAsList.get(0);

                if ((int) lastUpdate.getStatus() == Update.STATUS_PENDING) {
                    if (Software.compareVersions(
                            headers.get(HEADER_SOFTWARE_VERSION), lastSoftware.getVersion()) == 0) {
                        lastUpdate.setStatus(Update.STATUS_OK);
                        device.setStatus(Device.STATUS_UP_TO_DATE);
                        device.setVersion(Software.extractVersionFromNameString(lastSoftware.getVersion()));
                        device.setLastSoftwareUpdate();

                        System.out.println("    INFO: Successful update to version " + lastSoftware.getVersion());
                    } else {
                        lastUpdate.setStatus(Update.STATUS_ERROR);

                        System.out.println("    INFO: Unsuccessful update to version " + lastSoftware.getVersion());
                    }

                    deviceUpdateService.updateDeviceUpdate(lastUpdate);
                }
            }

            //Check if any software is available
            List<Software> softwareAsList = softwareService.getSoftwareByDeviceId(device.getId());
            boolean softwareNotFound = softwareAsList.isEmpty();
            if (softwareNotFound) {
                device.setLastSoftwareCheck();
                device.setStatus(Device.STATUS_NO_SOFTWARE);
                deviceService.updateDevice(device);

                System.out.println("    WARNING: No software found.");
                return new BinaryResponse(HttpStatus.NOT_MODIFIED).responseEntity();
            }

            Update newUpdate = new Update();
            Software latestAvailableSoftware = softwareAsList.get(0);

            updateAsList = deviceUpdateService.getLatestSuccessfulDeviceUpdate(device.getId());

            if (!firstUpdate && !updateAsList.isEmpty()) {
                //Check if update is available
                Update latestSuccessfulUpdate = updateAsList.get(0);
                if (Software.compareVersions(
                        latestAvailableSoftware.getVersion(), headers.get(HEADER_SOFTWARE_VERSION)) != 1) {

                    //No update for device
                    device.setLastSoftwareCheck();
                    deviceService.updateDevice(device);

                    System.out.println("    OK. No update found (" + latestAvailableSoftware.getVersion()
                            + " <= " + headers.get(HEADER_SOFTWARE_VERSION) + ")");
                    return new BinaryResponse(HttpStatus.NOT_MODIFIED).responseEntity();
                }

                List<Software> currentlyInstalledSoftwareAsList =
                        softwareService.getSoftwareById(latestSuccessfulUpdate.getSoftware_to());
                Software currentlyInstalledSoftware = currentlyInstalledSoftwareAsList.get(0);
                newUpdate.setSoftware_from(currentlyInstalledSoftware.getId());
            } else {
                newUpdate.setSoftware_from(null);
            }

            //Update device
            newUpdate.setDeviceId(device.getId());
            newUpdate.setSoftware_to(latestAvailableSoftware.getId());
            deviceUpdateService.addDeviceUpdate(newUpdate);

            device.setLastSoftwareCheck();
            deviceService.updateDevice(device);

            byte[] binaries = latestAvailableSoftware.getBinaries();

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "application/octet-stream; charset=utf8");
            responseHeaders.set("Content-Disposition", "attachment; filename=update.bin");
            responseHeaders.set("Content-Length", String.valueOf(binaries.length));
            responseHeaders.set("x-MD5", latestAvailableSoftware.getMd5());

            System.out.println("    OK. Updating device to version " + latestAvailableSoftware.getVersion());
            return new BinaryResponse(HttpStatus.OK, responseHeaders, binaries).responseEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BinaryResponse(HttpStatus.I_AM_A_TEAPOT).responseEntity();
    }

    @Scheduled(fixedDelay = 300000, initialDelay = 5000)
    public void scanSoftwareFolder() {
        System.out.println("\nFile scanner started.");
        File[] files = new File(Software.SOFTWARE_DIRECTORY_PATH).listFiles();

        if (files != null) {
            for (File file : files) {
                processSoftwareFile(file);
            }
        }
    }

    private void processSoftwareFile(File softwareFile) {
        System.out.print("    " + softwareFile.getName() + " - ");

        String softwareName = softwareFile.getName().substring(0, softwareFile.getName().length() - 4);
        if (!Software.isValidVersionName(softwareName)) {
            System.out.println("ERR: Invalid file name.");
            return;
        }

        String softwareNameScheme = Software.createSoftwareNameSchemeFromSoftwareName(
                Software.extractNameFromNameString(softwareName)
        );
        List<Device> deviceList = deviceService.getDeviceBySoftwareName(softwareNameScheme);

        if (deviceList.isEmpty()) {
            System.out.println("ERR: No matching device found.");
            return;
        }

        int highestSoftwareVersionId = 0;
        String highestSoftwareVersionName = "";
        boolean sameHash = false;

        Device device = deviceList.get(0);
        List<Software> matchingSoftwareList = softwareService.getSoftwareByDeviceId(device.getId());
        if (!matchingSoftwareList.isEmpty()) {
            try {
                for (Software s : matchingSoftwareList) {
                    int versionCompareResult = Software.compareVersions(s.getVersion(), softwareName);
                    sameHash = s.getMd5().equals(MD5Checksum.get(softwareFile.getAbsolutePath()));

                    if (versionCompareResult == 0) {
                        //Found previous version
                        if (sameHash) {
                            System.out.println("OK. Found previous record of this software version.");

                            if (device.getLastSoftwareUpdate().isBefore(s.getCreatedAt())) {
                                device.setStatus(Device.STATUS_NEEDS_UPDATE);
                                deviceService.updateDevice(device);
                            }
                        } else {
                            System.out.println("ERR: Found previous software with different hash!");
                        }
                        return;
                    }

                    if (versionCompareResult == 1) {
                        //Found higher version
                        System.out.println(
                                "ERR: Found record of higher version (" + s.getVersion() + ") of this software."
                        );
                        return;
                    }

                    //Keep track of highest software version
                    if (highestSoftwareVersionName.equals("")
                            || Software.compareVersions(s.getVersion(), highestSoftwareVersionName) == 1) {
                        highestSoftwareVersionId = s.getId();
                        highestSoftwareVersionName = s.getVersion();
                    }
                }
            } catch (Exception e) {
                System.out.println("ERR: Database error.");
                return;
            }
        }

        if (sameHash) {
            System.out.print("WARNING: Software with same hash already exists in database; ");
        }

        //New version or no software for device found
        Software newSoftware = new Software();
        newSoftware.setDeviceId(device.getId());
        newSoftware.setVersion(softwareName);
        newSoftware.setFile(softwareFile.getName());

        if (!highestSoftwareVersionName.equals("")) {
            newSoftware.setPreviousVersionId(highestSoftwareVersionId);
        }

        softwareService.addSoftware(newSoftware);

        device.setStatus(Device.STATUS_NEEDS_UPDATE);
        deviceService.updateDevice(device);

        System.out.println("OK. Created new software record.");
    }

    private boolean verifyHeaders(Map<String, String> headers) {
        System.out.println("\nRequest headers:");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            System.out.println("    " + entry.getKey() + ": " + entry.getValue());
        }

        if (headers.get(HEADER_USER_AGENT) == null
                || headers.get(HEADER_DEVICE_MAC) == null
                || headers.get(HEADER_AP_MAC) == null
                || headers.get(HEADER_FREE_SPACE) == null
                || headers.get(HEADER_SKETCH_SIZE) == null
                || headers.get(HEADER_SKETCH_MD5) == null
                || headers.get(HEADER_CHIP_SIZE) == null
                || headers.get(HEADER_SDK_VERSION) == null
                || headers.get(HEADER_SOFTWARE_VERSION) == null
                || headers.get(HEADER_MODE) == null) {
            return false;
        }

        if (!headers.get(HEADER_USER_AGENT).equals("ESP8266-http-Update"))
            return false;

        if (!this.isValidMAC(headers.get(HEADER_AP_MAC)) || !this.isValidMAC(headers.get(HEADER_DEVICE_MAC))) {
            return false;
        }

        if (!Software.isValidVersionName(headers.get(HEADER_SOFTWARE_VERSION))) {
            return false;
        }

        return headers.get(HEADER_MODE).equals("sketch");
    }

    private boolean isValidMAC(String s) {
        Pattern MAC = Pattern.compile("^(?:[A-F0-9]{2}:){5}[A-F0-9]{2}$");
        return MAC.matcher(s).matches();
    }
}
