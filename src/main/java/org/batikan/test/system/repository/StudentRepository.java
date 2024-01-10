package org.batikan.test.system.repository;

import org.batikan.test.system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public Optional<Student> findByName(String name);
}
