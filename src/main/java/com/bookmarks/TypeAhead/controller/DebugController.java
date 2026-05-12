package com.bookmarks.TypeAhead.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DebugController {

    @GetMapping("/debug/db")
    public Map<String, String> debugDb() {
        Map<String, String> info = new HashMap<>();

        // Check environment variables
        info.put("MYSQL_URL_ENV", mask(System.getenv("MYSQL_URL")));
        info.put("MYSQLHOST_ENV", mask(System.getenv("MYSQLHOST")));
        info.put("MYSQLPORT_ENV", System.getenv("MYSQLPORT"));
        info.put("MYSQLDATABASE_ENV", System.getenv("MYSQLDATABASE"));
        info.put("MYSQLUSER_ENV", System.getenv("MYSQLUSER"));
        info.put("JWT_SECRET_ENV", System.getenv("JWT_SECRET") != null ? "set" : "NOT SET");

        // Check system properties
        info.put("spring.datasource.url_sys", System.getProperty("spring.datasource.url"));

        return info;
    }

    private String mask(String s) {
        if (s == null) return "null";
        if (s.contains("mysql://")) {
            // Mask password in URL
            return s.replaceAll(":([^:@]+)@", ":***@");
        }
        return s.length() > 10 ? s.substring(0, 10) + "..." : s;
    }
}