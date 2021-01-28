package com.esp_ota_update.server.model;

import com.esp_ota_update.server.util.MD5Checksum;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Software {
    public static final String VERSION_REGEX = "^([A-z_]*)_(\\d+\\.\\d+\\.\\d+)$";
    public static final String SOFTWARE_DIRECTORY_PATH = "C:\\localhost\\espota\\";

    private final Integer id;
    private Integer deviceId;
    private Integer previousVersionId;
    private String version;
    private String file;
    private String md5;
    private LocalDateTime createdAt;

    //JSON constructor
    public Software() {
        this(0);
    }

    public Software(int id) {
        this.id = id;

        //These are defaults values that would be overwritten on db get
        this.createdAt = LocalDateTime.now();
        this.version = "0.0.0";
    }


    /**
     * @param a Version string A
     * @param b Version string B
     * @return 1 on A > B, 0 on A = B, -1 on A < B
     * @throws Exception when version string is incorrectly formatted or provided different scheme names
     */
    public static int compareVersions(String a, String b) throws Exception {
        if (!isValidVersionName(a) || !isValidVersionName(b)) {
            throw new Exception("WRONG VERSION SCHEME");
        }

        if (!extractNameFromNameString(a).equals(extractNameFromNameString(b))) {
            throw new Exception("DIFFERENT VERSION NAME");
        }

        String[] partsA = extractVersionFromNameString(a).split("\\.");
        String[] partsB = extractVersionFromNameString(b).split("\\.");

        int[] numA = Arrays.stream(partsA).mapToInt(Integer::parseInt).toArray();
        int[] numB = Arrays.stream(partsB).mapToInt(Integer::parseInt).toArray();

        long valA = (long) (numA[0] * 1e12 + numA[1] * 1e6 + numA[2]);
        long valB = (long) (numB[0] * 1e12 + numB[1] * 1e6 + numB[2]);

        if (valA == valB) {
            return 0;
        }

        return valA > valB ? 1 : -1;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String extractVersionFromNameString(String nameString) {
        Matcher m = Pattern.compile(Software.VERSION_REGEX).matcher(nameString);
        m.matches();
        return m.group(2);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String extractNameFromNameString(String nameString) {
        Matcher m = Pattern.compile(Software.VERSION_REGEX).matcher(nameString);
        m.matches();
        return m.group(1);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidVersionName(String name) {
        return Pattern.compile(Software.VERSION_REGEX).matcher(name).matches();
    }

    public static String createSoftwareNameSchemeFromSoftwareName(String name) {
        return name + "_#.#.#";
    }

    public static String getSoftwarePath(String file) {
        return SOFTWARE_DIRECTORY_PATH + file;
    }

    /**
     * @param update Software
     * @return true on success
     */
    public boolean applyUpdate(Software update) {
        if (update.getId() != 0) {
            return false;
        }

        if (update.getDeviceId() != null) {
            this.deviceId = update.getDeviceId();
        }

        if (update.getFile() != null) {
            this.file = update.getFile();
            this.md5 = this.calculateFileHash(getSoftwarePath(this.file));
        }

        if (update.getVersion() != null) {
            this.version = update.getVersion();
        }

        if (update.getPreviousVersionId() != null) {
            this.previousVersionId = update.getPreviousVersionId();
        }

        return true;
    }

    private String calculateFileHash(String filePath) {
        try {
            return MD5Checksum.get(filePath);
        } catch (Exception e) {
            return "md5-error";
        }
    }

    public String getSoftwarePath() {
        return getSoftwarePath(this.file);
    }

    public byte[] getBinaries() {
        try {
            return Files.readAllBytes(Path.of(this.getSoftwarePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getPreviousVersionId() {
        return previousVersionId;
    }

    public void setPreviousVersionId(Integer previousVersionId) {
        this.previousVersionId = previousVersionId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
        this.md5 = calculateFileHash(getSoftwarePath(file));
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }
}
