package com.example.weekly.controller;

import com.example.weekly.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/search")
    public List<String> searchLogs(@RequestParam String keyword) {
        return logService.searchLogs(keyword);
    }
}
