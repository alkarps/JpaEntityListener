package ru.alkarps.jpaentitylistener.repository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.alkarps.jpaentitylistener.repository.entity.TestEntity;
import ru.alkarps.jpaentitylistener.repository.entity.TestHistoryEntity;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestHistoryListener {
    private final ApplicationContext context;

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(TestEntity testEntity) {
        if (testEntity.getStatus() != TestEntity.Status.NEW) {
            log.info("Create new history entity for id {}", testEntity.getId());
            TestHistoryEntity historyEntity = new TestHistoryEntity();
            historyEntity.setTestId(testEntity.getId());
            historyEntity.setOldStatus(testEntity.getOldStatus());
            historyEntity.setNewStatus(testEntity.getStatus());
            historyEntity.setSomeDataForHistory(testEntity.getSomeDataForHistory());
            context.getBean(TestHistoryRepository.class).save(historyEntity);
        }
    }
}
