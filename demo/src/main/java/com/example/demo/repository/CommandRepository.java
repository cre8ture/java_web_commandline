package com.example.demo.repository;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Command;

public interface CommandRepository extends CrudRepository<Command, Long> {
}