package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Command;
// import com.example.demo.entity.Command;
// import com.example.demo.repository.CommandRepository;
import com.example.demo.repository.InMemoryDatabase;

@Service
public class CommandService {


    // @Autowired
    // private CommandRepository commandRepository;

        @Autowired
    private InMemoryDatabase inMemoryDatabase;


  @Async
    public CompletableFuture<String> executeCommand(String command) throws Exception {
       StringBuilder output = new StringBuilder();

    ProcessBuilder processBuilder = new ProcessBuilder();
    String os = System.getProperty("os.name").toLowerCase();

    if (os.contains("win")) {
        processBuilder.command("cmd", "/c", command);
    } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
        processBuilder.command("bash", "-c", command);
    } else {
        throw new UnsupportedOperationException("Unsupported operating system: " + os);
    }

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal != 0) {
                // check for errors
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((line = errorReader.readLine()) != null) {
                    output.append(line + "\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Command cmd = new Command();
        cmd.setCommand(command);
        cmd.setOutput(output.toString());
        inMemoryDatabase.save(cmd);

        return CompletableFuture.completedFuture(output.toString());
    }

    public String getCommand() {
        return null;
    }

    public FileInputStream getInputStreamReader() {
        File file = new File("database.ser");
        if (file.exists()) {
            try {
                return new FileInputStream(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}