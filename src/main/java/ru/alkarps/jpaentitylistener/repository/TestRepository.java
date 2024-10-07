package ru.alkarps.jpaentitylistener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alkarps.jpaentitylistener.repository.entity.TestEntity;

import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, UUID> {
    @Modifying
    @Query("update TestEntity set status = :newStatus where id = :id")
    void updateBy(UUID id, TestEntity.Status newStatus);
}
