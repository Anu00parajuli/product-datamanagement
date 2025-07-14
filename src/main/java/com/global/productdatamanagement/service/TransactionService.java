package com.global.productdatamanagement.service;

import com.eon.springbootdatamanagement.exception.GlobalException;
import com.eon.springbootdatamanagement.payload.request.StatusUpdateRequest;
import com.eon.springbootdatamanagement.payload.response.GlobalResponse;
import com.global.productdatamanagement.payload.request.TransactionCreateUpdateRequest;
import com.global.productdatamanagement.payload.request.TransactionDataRequest;
import org.springframework.stereotype.Service;

public interface TransactionService {
    GlobalResponse createTransaction(TransactionCreateUpdateRequest transactionCreateUpdateRequest) throws GlobalException;

    GlobalResponse updateTransaction(TransactionCreateUpdateRequest transactionCreateUpdateRequest, Long id) throws GlobalException;

    GlobalResponse updateTransactionStatus(Long id, StatusUpdateRequest statusUpdateRequest) throws GlobalException;

    GlobalResponse getTransactionById(Long id) throws GlobalException;

    GlobalResponse getAllTransaction() throws GlobalException;

    GlobalResponse getAllPaginatedTransactions(TransactionDataRequest transactionDataRequest) throws GlobalException;
}
