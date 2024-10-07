package ru.alkarps.jpaentitylistener.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alkarps.jpaentitylistener.repository.TestHistoryRepository;
import ru.alkarps.jpaentitylistener.repository.TestRepository;
import ru.alkarps.jpaentitylistener.repository.entity.TestEntity;
import ru.alkarps.jpaentitylistener.repository.entity.TestHistoryEntity;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "test", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    private final TestRepository testRepository;
    private final TestHistoryRepository testHistoryRepository;

    @PostMapping
    public ResponseEntity<TestEntity> create(@RequestBody String name) {
        TestEntity entity = new TestEntity();
        entity.setStatus(TestEntity.Status.NEW);
        entity.setName(name);
        return ResponseEntity.ok(testRepository.save(entity));
    }

    @GetMapping
    public ResponseEntity<Iterable<TestEntity>> findAll() {
        return ResponseEntity.ok(testRepository.findAll());
    }

    @PatchMapping("{id}")
    public ResponseEntity<TestEntity> update(@PathVariable UUID id) {
        TestEntity entity = testRepository.findById(id).orElseThrow();
        entity.setStatus(TestEntity.Status.UPDATED);
        entity.setSomeDataForHistory("Update");
        return ResponseEntity.ok(testRepository.save(entity));
    }

    @Transactional
    @PatchMapping("{id}/Transactional")
    public ResponseEntity<TestEntity> updateTransactional(@PathVariable UUID id) {
        boolean fail = new Random().nextBoolean();
        TestEntity entity = testRepository.findById(id).orElseThrow();
        entity.setStatus(TestEntity.Status.UPDATED);
        entity.setSomeDataForHistory(fail ? "Fail" : "NotFail");
        entity = testRepository.save(entity);
        log.info(entity.getSomeDataForHistory());
        if (fail) {
            throw new RuntimeException("ExceptionTest");
        }
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        TestEntity entity = testRepository.findById(id).orElseThrow();
        entity.setStatus(TestEntity.Status.DELETED);
        testRepository.save(entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        List<TestEntity> entities = testRepository.findAll();
        entities.forEach(entity -> entity.setStatus(TestEntity.Status.DELETED));
        testRepository.saveAll(entities);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/history")
    public ResponseEntity<Iterable<TestHistoryEntity>> findAllHistoryBy(@PathVariable UUID id) {
        return ResponseEntity.ok(testHistoryRepository.findAllByTestId(id));
    }

}
