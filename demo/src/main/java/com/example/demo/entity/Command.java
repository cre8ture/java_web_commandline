package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; 

@Entity
public class Command implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String command;
    private String output;

    public void setCommand(String command) {
        this.command = command;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Long getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public String getOutput() {
        return output;
    }

    public void setId(long id) {
        this.id = id;
    }
}