package ru.alkarps.jpaentitylistener.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import ru.alkarps.jpaentitylistener.repository.TestHistoryListener;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@EntityListeners(TestHistoryListener.class)
public class TestEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private Status oldStatus;

    @Column
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @Transient
    private String someDataForHistory;

    public void setStatus(Status status) {
        this.oldStatus = this.status;
        this.status = status;
    }

    public enum Status {
        NEW, UPDATED, DELETED
    }
}
