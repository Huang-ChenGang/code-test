package com.cognizant.code.test.infrastructure.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.Random;

@MappedSuperclass
public class BaseEntity {

    @Id
    @Getter
    @Column(nullable = false, updatable = false, length = 40)
    private String id;

    @Getter
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;

    @Getter
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateTime;

    @PrePersist
    private void onCreate() {
        if (StringUtils.isEmpty(id)) {
            id = getUniqueKey();
        }
    }

    private synchronized String getUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
