package com.global.productdatamanagement.controller;

import com.eon.springbootdatamanagement.enums.ApiStatusEnum;
import com.eon.springbootdatamanagement.exception.GlobalException;
import com.eon.springbootdatamanagement.payload.request.StatusUpdateRequest;
import com.eon.springbootdatamanagement.payload.response.GlobalResponse;
import com.global.productdatamanagement.annotation.TransactionRestController;
import com.global.productdatamanagement.payload.request.TransactionCreateUpdateRequest;
import com.global.productdatamanagement.payload.request.TransactionDataRequest;
import com.global.productdatamanagement.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@TransactionRestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<GlobalResponse> createTransaction(@RequestBody TransactionCreateUpdateRequest transactionCreateUpdateRequest) throws GlobalException {
        return ResponseEntity.ok(transactionService.createTransaction(transactionCreateUpdateRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<GlobalResponse> updateTransaction(@RequestBody TransactionCreateUpdateRequest currencyCreateUpdateRequest, @RequestHeader Long id) throws GlobalException {
        GlobalResponse.builder().status(ApiStatusEnum.SUCCESS).httpStatus(HttpStatus.OK).build();
        return ResponseEntity.ok(transactionService.updateTransaction(currencyCreateUpdateRequest, id));
    }

    @PatchMapping("/updateStatus")
    public ResponseEntity<GlobalResponse> updateTransactionStatus(@RequestHeader Long id, @RequestBody StatusUpdateRequest statusUpdateRequest) throws GlobalException {
        return ResponseEntity.ok(transactionService.updateTransactionStatus(id, statusUpdateRequest));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<GlobalResponse> findTransactionById(@RequestHeader Long id) throws GlobalException {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/find-all")
    public ResponseEntity<GlobalResponse> findAllTransaction() throws GlobalException {
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }

    @PostMapping("/find-paginated-data")
    public ResponseEntity<GlobalResponse> findTransactionPaginatedData(@RequestBody TransactionDataRequest transactionDataRequest) throws GlobalException {
        return ResponseEntity.ok(transactionService.getAllPaginatedTransactions(transactionDataRequest));
    }
}
