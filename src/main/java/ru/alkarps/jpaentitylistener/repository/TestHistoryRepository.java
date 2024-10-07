package ru.alkarps.jpaentitylistener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alkarps.jpaentitylistener.repository.entity.TestHistoryEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistoryEntity, UUID> {
    List<TestHistoryEntity> findAllByTestId(UUID id);
}
