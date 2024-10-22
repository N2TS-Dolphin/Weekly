package com.example.weekly.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    // Đường dẫn tới thư mục chứa các file log
    private static final String LOG_DIRECTORY = "logs/";

    public List<String> searchLogs(String keyword) {
        List<String> matchingLines = new ArrayList<>();
        File logDir = new File(LOG_DIRECTORY);

        // Kiểm tra thư mục logs tồn tại
        if (logDir.exists() && logDir.isDirectory()) {
            // Lấy danh sách các file log trong thư mục
            File[] logFiles = logDir.listFiles((dir, name) -> name.startsWith("app") && name.endsWith(".log"));

            if (logFiles != null) {
                for (File logFile : logFiles) {
                    try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            if (line.contains(keyword)) {
                                matchingLines.add("[" + logFile.getName() + "] " + line);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return matchingLines;
    }
}

