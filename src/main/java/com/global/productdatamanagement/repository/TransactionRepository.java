package com.global.productdatamanagement.repository;

import com.eon.springbootdatamanagement.repository.BaseRepository;
import com.global.productdatamanagement.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<TransactionEntity> {
    boolean existsTransactionEntityById(Long id);
    boolean existsTransactionEntityByCustomerNameAndBeneficiaryName(String customerName, String beneficiaryName);

    TransactionEntity findTransactionEntityById(Long id);
}
