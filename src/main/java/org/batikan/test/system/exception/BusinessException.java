package org.batikan.test.system.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private final BusinessError businessError;

    public BusinessException(BusinessError businessError) {
        super(businessError.getErrorDescription());
        this.businessError = businessError;
    }
}
