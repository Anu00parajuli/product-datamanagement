package com.global.productdatamanagement.entity;

import com.eon.springbootdatamanagement.entity.BaseEntity;
import com.eon.springbootdatamanagement.enums.StatusEnum;
import com.global.productdatamanagement.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transaction")
public class TransactionEntity extends BaseEntity {
    private String customerName;
    private String beneficiaryName;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    @PrePersist
    void prePersist() {
        this.status = TransactionStatus.INITIATED;
        this.statusEnum = StatusEnum.ACTIVE;
    }
}
