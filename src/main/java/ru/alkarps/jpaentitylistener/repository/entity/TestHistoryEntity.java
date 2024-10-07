package ru.alkarps.jpaentitylistener.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
public class TestHistoryEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column
    private UUID testId;

    @Column
    @Enumerated(EnumType.STRING)
    private TestEntity.Status oldStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private TestEntity.Status newStatus;

    @Column
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column
    private String someDataForHistory;

}
