package com.esp_ota_update.server.util;

public class Log {
    public static void log(String message, int level) {
        StringBuilder sb = new StringBuilder();

        while (level-- != 0) {
            sb.append("    ");
        }

        sb.append(message);
        System.out.println(sb.toString());
    }

    public static void log(String message) {
        Log.log(message, 0);
    }

    public static void log() {
        Log.log("", 0);
    }
}
