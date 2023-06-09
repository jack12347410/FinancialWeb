package com.jack.financialweb.controller;

import com.jack.financialweb.exception.InvalidRequestException;
import com.jack.financialweb.helper.QpayHelper;
import com.jack.financialweb.model.OrderCreateBody;
import com.jack.financialweb.model.OrderCreateRequest;
import com.jack.financialweb.model.OrderCreateRequestDto;
import com.jack.financialweb.util.SysConfigUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Qpay")
@RequiredArgsConstructor
public class QpayController {

    private final QpayHelper _qpayHelper;

    @PostMapping("/order")
    public ResponseEntity<String> OrderCreate(@Valid @RequestBody OrderCreateRequestDto orderCreateRequestDto,
                                              BindingResult result) throws Exception {
        if(result.hasErrors()){
            throw new InvalidRequestException("Invalid parameter", result);
        }

        OrderCreateRequest request =  orderCreateRequestDto.Convert();
        request.setOrderNo(System.currentTimeMillis() + "");
        request.setShopNo(SysConfigUtil.getShopNo());
        request.setReturnURL("https://minalife.blog/");
        request.setBackendURL("https://tw.yahoo.com/");

        OrderCreateRequest.CardParam cardParam =  request.new CardParam();
        cardParam.setAutoBilling(OrderCreateBody.AutoBilling.Y);
        request.setCardParam(cardParam);

        return new ResponseEntity<String>(_qpayHelper.CreateOrder(request), HttpStatus.OK);
    }

}
