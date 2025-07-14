package com.global.productdatamanagement.payload.request;

import com.eon.springbootdatamanagement.payload.request.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDataRequest extends PaginationRequest {
    private String searchText;
    private double amount;
}
