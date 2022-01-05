package com.cognizant.code.test.domain.model;

import com.cognizant.code.test.infrastructure.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Valid
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 300)
    private String description;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal tax;

}
