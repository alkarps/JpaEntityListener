package ru.alkarps.jpaentitylistener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alkarps.jpaentitylistener.repository.entity.TestEntity;

import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, UUID> {
}
