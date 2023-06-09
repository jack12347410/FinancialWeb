package com.jack.financialweb.controller;

import com.jack.financialweb.exception.InvalidRequestException;
import com.jack.financialweb.helper.QpayHelper;
import com.jack.financialweb.model.*;
import com.jack.financialweb.util.SysConfigUtil;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Qpay")
@RequiredArgsConstructor
public class QpayController {

    private final QpayHelper _qpayHelper;

    /**
     * 送出訂單
     * @param orderCreateRequestDto
     * @param result
     * @return
     * @throws Exception
     */
    @PostMapping("/order")
    public ResponseEntity<?> OrderCreate(@Valid @RequestBody OrderCreateRequestDto orderCreateRequestDto,
                                              BindingResult result) throws Exception {
        if(result.hasErrors()){
            throw new InvalidRequestException("Invalid parameter", result);
        }

        OrderCreateRequest request =  orderCreateRequestDto.Convert();
        request.setOrderNo(System.currentTimeMillis() + "");
        request.setShopNo(SysConfigUtil.getShopNo());
//        request.setReturnURL("https://minalife.blog/");
        request.setReturnURL("http://localhost/jack/v1/api/Qpay/query/");
        request.setBackendURL("https://minalife.blog/");

        OrderCreateRequest.CardParam cardParam =  request.new CardParam();
        cardParam.setAutoBilling(OrderCreateBody.AutoBilling.Y);
        request.setCardParam(cardParam);

        return new ResponseEntity<String>(_qpayHelper.Post(request,
                OrderCreateResponse.class, "OrderCreate"), HttpStatus.OK);
    }

    /**
     * 查詢訂單
     * @return
     */
    @PostMapping("/query")
    public ResponseEntity<?> OrderPayQuery(@Valid @RequestBody OrderPayQueryRequest request,
                                           BindingResult result) throws Exception{

        return new ResponseEntity<String>(_qpayHelper.Post(request,
                OrderPayQueryResponse.class, "OrderPayQuery"), HttpStatus.OK);
    }

}
