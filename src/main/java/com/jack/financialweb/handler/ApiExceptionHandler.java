package com.jack.financialweb.handler;

import com.jack.financialweb.exception.InvalidRequestException;
import com.jack.financialweb.exception.NotFoundException;
import com.jack.financialweb.resource.ErrorResource;
import com.jack.financialweb.resource.FieldResource;
import com.jack.financialweb.resource.InvalidErrorResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * 處理找不到資料異常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFound(RuntimeException e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        return new ResponseEntity<Object>(errorResource, HttpStatus.NOT_FOUND);
    }

    /**
     * 處理參數驗證失敗
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidRequest(InvalidRequestException e) {
        List<FieldResource> fieldResourceList = new ArrayList<>();
        for (FieldError fe : e.getErrors().getFieldErrors()) {
            FieldResource fieldResource = new FieldResource(fe.getObjectName(),
                    fe.getField(), fe.getCode(), fe.getDefaultMessage());
            fieldResourceList.add(fieldResource);
        }
        InvalidErrorResource invalidErrorResource = new InvalidErrorResource(e.getMessage(), fieldResourceList);
        return new ResponseEntity<Object>(invalidErrorResource, HttpStatus.BAD_REQUEST);
    }

    /**
     * 處理全局異常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception e) {
        ErrorResource errorResource = new ErrorResource(e.getMessage());
        return new ResponseEntity<Object>(errorResource, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
