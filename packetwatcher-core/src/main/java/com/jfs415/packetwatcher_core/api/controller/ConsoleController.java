package com.jfs415.packetwatcher_core.api.controller;

import com.jfs415.packetwatcher_core.api.service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConsoleController {

    private final ConsoleService consoleService;

    @Autowired
    public ConsoleController(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    @PostMapping("/console/command")
    public ResponseEntity<?> processCommand() {
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/console/", params = "query")
    public ResponseEntity<?> processQuery(@RequestParam String query) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/command")
    public ResponseEntity<?> displayConsoleOutput() {
        return ResponseEntity.ok(null);
    }
}
