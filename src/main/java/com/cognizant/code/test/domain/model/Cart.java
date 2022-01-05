package com.cognizant.code.test.domain.model;

import com.cognizant.code.test.infrastructure.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Valid
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_customer", columnNames = {"customerId"})
        }
)
public class Cart extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 40)
    private String customerId;

    public Cart(String customerId) {
        this.customerId = customerId;
    }
}
