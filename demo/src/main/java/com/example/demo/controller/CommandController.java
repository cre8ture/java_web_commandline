package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CommandService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileInputStream;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class CommandController {

    @Autowired
    private CommandService commandService;
    
    @PostMapping("/execute")
    public CompletableFuture<String> executeCommand(@RequestBody String command) throws Exception {
        return commandService.executeCommand(command);
    }

    @GetMapping("/database")
    public ResponseEntity<Resource> getDataBase( )throws Exception {
        FileInputStream in = commandService.getInputStreamReader();

        if (in != null) {
            InputStreamResource resource = new InputStreamResource(in);
            return ResponseEntity.ok()
                    .contentLength(in.available())
                    .header("Content-Disposition", "attachment; filename=\"database.ser\"")
                    .body(resource);
        }
        return null;
    }
    
}

