package org.batikan.test.system;

import lombok.Builder;
import lombok.Data;
import org.batikan.test.system.exception.BusinessException;
import org.batikan.test.system.exception.BusinessError;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Response {
    private int errorCode;
    private String errorDescription;
    private HttpStatus errorStatus;
    private Object payload;

    public static Response success(Object payload) {
        return Response.builder().payload(payload).build();
    }

    public static Response failure(BusinessException businessException) {
        BusinessError businessError = businessException.getBusinessError();
        return Response.builder()
                .errorCode(businessError.getErrorCode())
                .errorDescription(businessError.getErrorDescription())
                .errorStatus(businessError.getErrorStatus())
                .build();
    }
}
