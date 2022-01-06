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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Valid
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_product", columnNames = {"cartId", "productId"})
        }
)
public class CartItem extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 40)
    private String cartId;

    @NotBlank
    @Column(nullable = false, length = 40)
    private String productId;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity;

    public CartItem(String cartId, String productId, Integer quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
