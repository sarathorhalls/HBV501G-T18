package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EmployeeRepository repository;

    @Autowired
    public DatabaseLoader(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.repository.save(new Employee("John", "Doe", "John Doe is a hard working employee"));
        this.repository.save(new Employee("Jane", "Doe", "Jane Doe is a lazy employee"));
        this.repository.save(new Employee("Jack", "Doe", "Jack Doe is a lazy employee"));
        this.repository.save(new Employee("Jill", "Doe", "Jill Doe is a lazy employee"));
    }
    
}
