package com.global.productdatamanagement.payload.response;

import com.eon.springbootdatamanagement.enums.StatusEnum;
import com.global.productdatamanagement.enums.TransactionStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private Long id;
    private String customerName;
    private String beneficiaryName;
    private double amount;
    private TransactionStatus transactionStatus;
    private StatusEnum status;
}
