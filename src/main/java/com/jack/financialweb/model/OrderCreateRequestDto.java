package com.jack.financialweb.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class OrderCreateRequestDto {
    @NotBlank
    private String prdtName;
    @Min(value = 0)
    private Integer amount;
    @NotBlank
    private String currencyID;
    @NotBlank
    private String payType;

    /**
     * 轉換成 OrderCreateRequest
     * @return
     */
    public OrderCreateRequest Convert(){
        OrderCreateRequest request = new OrderCreateRequest();
        BeanUtils.copyProperties(this, request);
        request.setPayType(com.jack.financialweb.model.OrderCreateBody.PayType.valueOf(payType));
        return request;
    }
}
