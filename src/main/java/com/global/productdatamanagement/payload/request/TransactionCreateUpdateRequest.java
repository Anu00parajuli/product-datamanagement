package com.global.productdatamanagement.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCreateUpdateRequest {
    private String customerName;
    private String beneficiaryName;
    private double amount;
}
