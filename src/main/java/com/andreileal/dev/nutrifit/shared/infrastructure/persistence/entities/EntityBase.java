package com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class EntityBase {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid default uuid_generate_v4()")
    private UUID id;

    public EntityBase() {
        var now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

    }

    public EntityBase(UUID id) {
        this.id = id;
    }

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setDeletedAt() {
        this.deletedAt = LocalDateTime.now();
    }
}
