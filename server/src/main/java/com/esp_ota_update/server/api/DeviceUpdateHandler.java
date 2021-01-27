package com.esp_ota_update.server.api;

import com.esp_ota_update.server.model.Device;
import com.esp_ota_update.server.model.Software;
import com.esp_ota_update.server.model.Update;
import com.esp_ota_update.server.service.DeviceService;
import com.esp_ota_update.server.service.DeviceUpdateService;
import com.esp_ota_update.server.service.SoftwareService;
import com.esp_ota_update.server.util.MD5Checksum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings({"FieldCanBeLocal", "BooleanMethodIsAlwaysInverted"})
@RequestMapping("api/v1/up/")
@RestController
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

    @GetMapping(path = "/img")
    public ResponseEntity<byte[]> imageTest(HttpServletResponse response){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Description", "File transfer");
        headers.set("Content-Type", "application/octet-stream");
        headers.set("Content-Disposition", "attachment; filename=image.png");

        return ResponseEntity.ok().body(null);
    }

    @GetMapping
    public ResponseEntity<Response> handleDeviceUpdate(@RequestHeader Map<String, String> headers) {
        if (!this.verifyHeaders(headers)) {
            return new Response(false, HttpStatus.BAD_REQUEST).responseEntity();
        }

        System.out.println("Update request from " + headers.get(HEADER_DEVICE_MAC) + ", version "
                + headers.get(HEADER_SOFTWARE_VERSION));

        try {

            List<Device> deviceAsList = deviceService.getDeviceByMac(headers.get(HEADER_DEVICE_MAC));
            boolean unknownDevice = deviceAsList.isEmpty();

            if (unknownDevice) {
                //Self-introduce path
                Device device = new Device();
                device.setMac(headers.get(HEADER_DEVICE_MAC));
                deviceService.addDevice(device);

                System.out.println("    Introduced new device " + headers.get(HEADER_DEVICE_MAC));
                return new Response(true, HttpStatus.NOT_MODIFIED).responseEntity();
            }

            //Known device path
            Device device = deviceAsList.get(0);
            Update update;
            Software software;

            List<Update> deviceUpdateAsList = deviceUpdateService.getLatestDeviceUpdate(device.getId());
            boolean firstUpdate = deviceUpdateAsList.isEmpty();

            if (!firstUpdate) {
                Update lastUpdate = deviceUpdateAsList.get(0);
                List<Software> softwareAsList = softwareService.getSoftwareById(lastUpdate.getSoftware_to());
                Software lastSoftware = softwareAsList.get(0);

                //Update last update status
                if ((int) lastUpdate.getStatus() == Update.STATUS_PENDING) {
                    //Check if device updated successfully
                    if (Software.compareVersions(
                            headers.get(HEADER_SOFTWARE_VERSION), lastSoftware.getVersion()) == 0) {
                        lastUpdate.setStatus(Update.STATUS_OK);
                        device.setLastSoftwareUpdate();

                        System.out.println("    Successful update of " + device.getName()
                                + " to " + lastSoftware.getVersion());
                    } else {
                        lastUpdate.setStatus(Update.STATUS_ERROR);

                        System.out.println("    Unsuccessful update of " + device.getName()
                                + " to " + lastSoftware.getVersion());
                    }

                    deviceUpdateService.updateDeviceUpdate(lastUpdate);
                }
            }

            List<Software> softwareAsList = softwareService.getSoftwareByDeviceId(device.getId());
            boolean softwareNotFound = softwareAsList.isEmpty();
            if (softwareNotFound) {
                device.setLastSoftwareCheck();
                deviceService.updateDevice(device);

                System.out.println("    No software found for " + device.getName());
                return new Response(true, HttpStatus.NOT_MODIFIED).responseEntity();
            }

            software = softwareAsList.get(0);
            if (Software.compareVersions(software.getVersion(), headers.get(HEADER_SOFTWARE_VERSION)) != 1) {
                //No update for device
                device.setLastSoftwareCheck();
                deviceService.updateDevice(device);

                System.out.println("    No update found for " + device.getName()
                        + " (" + software.getVersion() + " <= " + headers.get(HEADER_SOFTWARE_VERSION) + ")");
                return new Response(true, HttpStatus.NOT_MODIFIED).responseEntity();
            }


            device.setLastSoftwareCheck();
            deviceService.updateDevice(device);
//            deviceUpdateService.updateDeviceUpdate(update);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response(true, HttpStatus.I_AM_A_TEAPOT).responseEntity();
    }

    private void scanSoftwareFolder() {
        System.out.println("File scanner started.");
        File[] files = new File(Software.SOFTWARE_DIRECTORY_PATH).listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            processSoftwareFile(file);
        }
    }

    private void processSoftwareFile(File softwareFile) {
        System.out.print("    " + softwareFile.getName() + " - ");

        String softwareName = softwareFile.getName().substring(0, softwareFile.getName().length() - 4);
        if (!Software.isValidVersionName(softwareName)) {
            System.out.println("ERR: Invalid file name.");
            return;
        }

        String softwareNameScheme = Software.extractNameFromNameString(softwareName) + "#.#.#";
        List<Device> deviceList = deviceService.getDeviceBySoftwareName(softwareNameScheme);

        if (deviceList.isEmpty()) {
            System.out.println("ERR: No matching device found.");
            return;
        }

        Device device = deviceList.get(0);
        List<Software> matchingSoftwareList = softwareService.getSoftwareByDeviceId(device.getId());
        if (matchingSoftwareList.isEmpty()) {
            //No software for this device found
            Software newSoftware = new Software();
            newSoftware.setDeviceId(device.getId());
            newSoftware.setVersion(softwareName);
            newSoftware.setFile(softwareFile.getName());
            softwareService.addSoftware(newSoftware);

            device.setStatus(Device.STATUS_NEEDS_UPDATE);
            deviceService.updateDevice(device);

            System.out.println("OK: Created software record for new device.");
            return;
        }

        try {
            for (Software s : matchingSoftwareList) {
                if (s.getVersion().equals(softwareName)) {
                    if (s.getMd5().equals(MD5Checksum.get(softwareFile.getAbsolutePath()))) {
                        System.out.println("OK. Found previous software record.");
                    } else {
                        System.out.println("ERR. Found previous software with different hash!");
                    }
                    return;
                }

                if (Software.compareVersions(s.getVersion(), softwareName) == 1) {
                    System.out.println("ERR. Found record of higher version of this software.");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("ERR. Database error.");
            return;
        }

        System.out.println("OK.");
    }

    private boolean verifyHeaders(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
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

        if (!Software.isValidVersionName(headers.get(HEADER_SDK_VERSION))
                || !Software.isValidVersionName(headers.get(HEADER_SOFTWARE_VERSION))) {
            return false;
        }

        return headers.get(HEADER_MODE).equals("sketch");
    }

    private boolean isValidMAC(String s) {
        Pattern MAC = Pattern.compile("^(?:[A-F0-9]{2}:){5}[A-F0-9]{2}$");
        return MAC.matcher(s).matches();
    }
}
