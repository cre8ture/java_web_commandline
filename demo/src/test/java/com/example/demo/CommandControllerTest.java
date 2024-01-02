package com.example.demo;

import com.example.demo.service.CommandService;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// Remove the existing import statement for org.junit.jupiter.api.Assumptions
// import org.junit.jupiter.api.Assumptions;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Assumptions;

@SpringBootTest
@AutoConfigureMockMvc
public class CommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommandService commandService;

    @Test
    public void testExecuteCommandWindows() throws Exception {
        Assumptions.assumeTrue(System.getProperty("os.name").startsWith("Windows"));
    
        String command = "dir";
        when(commandService.executeCommand(command)).thenReturn(CompletableFuture.completedFuture("file1\nfile2\n"));
    
        mockMvc.perform(post("/execute")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(command)))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testExecuteCommandLinux() throws Exception {
        Assumptions.assumeTrue(System.getProperty("os.name").startsWith("Linux"));
    
        String command = "ls";
        when(commandService.executeCommand(command)).thenReturn(CompletableFuture.completedFuture("file1\nfile2\n"));
    
        mockMvc.perform(post("/execute")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(command)))
                .andExpect(status().isOk());
    }
}