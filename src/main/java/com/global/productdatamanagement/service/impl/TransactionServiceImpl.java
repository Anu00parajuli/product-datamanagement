package com.global.productdatamanagement.service.impl;

import com.eon.springbootdatamanagement.builder.ServiceResponseBuilder;
import com.eon.springbootdatamanagement.exception.GlobalException;
import com.eon.springbootdatamanagement.payload.request.StatusUpdateRequest;
import com.eon.springbootdatamanagement.payload.response.DataPaginationResponse;
import com.eon.springbootdatamanagement.payload.response.GlobalResponse;
import com.eon.springbootdatamanagement.util.Helper;
import com.global.productdatamanagement.entity.TransactionEntity;
import com.global.productdatamanagement.payload.request.TransactionCreateUpdateRequest;
import com.global.productdatamanagement.payload.request.TransactionDataRequest;
import com.global.productdatamanagement.payload.response.TransactionResponse;
import com.global.productdatamanagement.repository.TransactionRepository;
import com.global.productdatamanagement.service.TransactionService;
import com.global.productdatamanagement.specification.TransactionSpecification;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final MessageSource messageSource;

    public TransactionServiceImpl(TransactionRepository transactionRepository, MessageSource messageSource) {
        this.transactionRepository = transactionRepository;
        this.messageSource = messageSource;
    }

    @Override
    public GlobalResponse createTransaction(TransactionCreateUpdateRequest transactionCreateUpdateRequest) throws GlobalException {
        if (transactionRepository.existsTransactionEntityByCustomerNameAndBeneficiaryName(transactionCreateUpdateRequest.getCustomerName(), transactionCreateUpdateRequest.getBeneficiaryName())) {
            throw new GlobalException("TRA001", HttpStatus.CONFLICT);
        }
        if (transactionCreateUpdateRequest.getCustomerName() == null) {
            throw new GlobalException("TRA003", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (transactionCreateUpdateRequest.getBeneficiaryName() == null) {
            throw new GlobalException("TRA004", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (transactionCreateUpdateRequest.getAmount() < 0.0) {
            throw new GlobalException("TRA005", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .customerName(transactionCreateUpdateRequest.getCustomerName())
                .beneficiaryName(transactionCreateUpdateRequest.getBeneficiaryName())
                .amount(transactionCreateUpdateRequest.getAmount())
                .build();
        transactionRepository.save(transactionEntity);
        return ServiceResponseBuilder.buildSuccessResponse(messageSource.getMessage("transaction.create.success", null, LocaleContextHolder.getLocale()));
    }

    @Override
    public GlobalResponse updateTransaction(TransactionCreateUpdateRequest transactionCreateUpdateRequest, Long id) throws GlobalException {
        if (!transactionRepository.existsTransactionEntityById(id)) {
            throw new GlobalException("TRA002", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        TransactionEntity transactionEntity = transactionRepository.findTransactionEntityById(id);
        if (transactionCreateUpdateRequest.getCustomerName() != null) {
            transactionEntity.setCustomerName(transactionCreateUpdateRequest.getCustomerName());
        }
        if (transactionCreateUpdateRequest.getBeneficiaryName() != null) {
            transactionEntity.setBeneficiaryName(transactionCreateUpdateRequest.getBeneficiaryName());
        }
        if (transactionCreateUpdateRequest.getAmount() < 0.0) {
            transactionEntity.setAmount(transactionCreateUpdateRequest.getAmount());
        }
        transactionRepository.save(transactionEntity);
        return ServiceResponseBuilder.buildSuccessResponse(messageSource.getMessage("transaction.update.success", null, LocaleContextHolder.getLocale()));
    }

    @Override
    public GlobalResponse updateTransactionStatus(Long id, StatusUpdateRequest statusUpdateRequest) throws GlobalException {
        if (!transactionRepository.existsTransactionEntityById(id)) {
            throw new GlobalException("TRA002", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        TransactionEntity transactionEntity = transactionRepository.findTransactionEntityById(id);
        TransactionResponse transactionResponse = createTransactionResponse(transactionEntity);
        return ServiceResponseBuilder.buildSuccessResponse(messageSource.getMessage("transaction.update.success", null, LocaleContextHolder.getLocale()), transactionResponse);
    }

    @Override
    public GlobalResponse getTransactionById(Long id) throws GlobalException {
        if (!transactionRepository.existsTransactionEntityById(id)) {
            throw new GlobalException("TRA002", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        TransactionEntity transactionEntity = transactionRepository.findTransactionEntityById(id);
        TransactionResponse transactionResponse = createTransactionResponse(transactionEntity);
        return ServiceResponseBuilder.buildSuccessResponseWithMessage(messageSource.getMessage("transaction.fetch.success", null, LocaleContextHolder.getLocale()), transactionResponse);

    }

    @Override
    public GlobalResponse getAllTransaction() throws GlobalException {
        List<TransactionEntity> transactionEntities = transactionRepository.findAll();
        List<TransactionResponse> transactionResponseList = transactionEntities.stream().map(this::createTransactionResponse).toList();
        return ServiceResponseBuilder.buildSuccessResponseWithMessage(messageSource.getMessage("transaction.fetch.success", null, LocaleContextHolder.getLocale()), transactionResponseList);
    }

    @Override
    public GlobalResponse getAllPaginatedTransactions(TransactionDataRequest transactionDataRequest) throws GlobalException {
        Specification<TransactionEntity> specification = TransactionSpecification.filterTransaction(transactionDataRequest);
        Page<TransactionEntity> transactionEntityPage = transactionRepository.findAll(specification, Helper.getPageable(transactionDataRequest));
        List<TransactionEntity> transactionEntityList = transactionEntityPage.getContent();
        List<TransactionResponse> transactionResponseList = transactionEntityList.stream().map(this::createTransactionResponse).toList();
        DataPaginationResponse dataPaginationResponse = DataPaginationResponse.builder()
                .result(transactionResponseList)
                .totalElementCount(transactionEntityPage.getTotalElements())
                .build();
        return ServiceResponseBuilder.buildSuccessResponseWithMessage(messageSource.getMessage("transaction.fetch.success", null, LocaleContextHolder.getLocale()), dataPaginationResponse);
    }

    private TransactionResponse createTransactionResponse(TransactionEntity transactionEntity) {
        return TransactionResponse.builder()
                .id(transactionEntity.getId())
                .customerName(transactionEntity.getCustomerName())
                .beneficiaryName(transactionEntity.getBeneficiaryName())
                .amount(transactionEntity.getAmount())
                .build();
    }
}
