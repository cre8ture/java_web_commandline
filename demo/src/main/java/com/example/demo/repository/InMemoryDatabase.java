package com.example.demo.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Command;

@Repository
public class InMemoryDatabase {
    private ConcurrentHashMap<Long, Command> database = new ConcurrentHashMap<>();
    private AtomicLong idGenerator = new AtomicLong();
    private final ReentrantLock lock = new ReentrantLock();

    public InMemoryDatabase() {
        File file = new File("database.ser");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                database = (ConcurrentHashMap<Long, Command>) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Command save(Command command) throws Exception {
        lock.lock();
        try {
            long id = idGenerator.incrementAndGet();
            if (command.getId() != null) throw new Exception("Id must be null");
            command.setId(id);
            database.put(id, command);

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("database.ser"))) {
                out.writeObject(database);
            }

            return command;
        } finally {
            lock.unlock();
        }
    }

    public Command find(Long id) {
        lock.lock();
        try {
            return database.get(id);
        } finally {
            lock.unlock();
        }
    }

    public Command delete(Long id) {
        lock.lock();
        try {
            return database.remove(id);
        } finally {
            lock.unlock();
        }
    }

    public List<Command> findAll() {
        lock.lock();
        try {
            return new ArrayList<>(database.values());
        } finally {
            lock.unlock();
        }
    }
}