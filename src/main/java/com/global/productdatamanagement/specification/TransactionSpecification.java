package com.global.productdatamanagement.specification;

import com.global.productdatamanagement.entity.TransactionEntity;
import com.global.productdatamanagement.payload.request.TransactionDataRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {
    private TransactionSpecification() {

    }

    public static Specification<TransactionEntity> filterTransaction(TransactionDataRequest transactionDataRequest) {
        return ((root, query, criteriaBuilder) -> {
            Predicate finalPredicate = criteriaBuilder.conjunction();

            if (StringUtils.isNotBlank(transactionDataRequest.getSearchText())) {
                String searchText = likePattern(transactionDataRequest.getSearchText());
                Predicate searchTextPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("customerName")), searchText),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("beneficiaryName")), searchText)
                );
                finalPredicate = criteriaBuilder.and(finalPredicate, searchTextPredicate);
            }

            //Using amount filter
            if (transactionDataRequest.getAmount() > 0.0) {
                Predicate currencyNamePredicate = criteriaBuilder.equal(root.get("amount"), transactionDataRequest.getAmount());
                finalPredicate = criteriaBuilder.and(finalPredicate, currencyNamePredicate);
            }
            return finalPredicate;

        });
    }

    private static String likePattern(String value) {
        return "%" + value + "%";
    }

}
