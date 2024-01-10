package org.batikan.test.system.repository;

import org.batikan.test.system.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    public Optional<Test> findByName(String name);
}
