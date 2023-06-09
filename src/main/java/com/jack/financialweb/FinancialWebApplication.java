package com.jack.financialweb;

import com.jack.financialweb.helper.QpayHelper;
import com.jack.financialweb.model.OrderCreateBody;
import com.jack.financialweb.model.OrderCreateRequest;
import com.jack.financialweb.model.OrderCreateRequestDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancialWebApplication {

    public static void main(String[] args) throws Exception {

//        OrderCreateRequest orderCreateRequestDto = new OrderCreateRequest();
//        orderCreateRequestDto.setShopNo("NA0001_001");
//        orderCreateRequestDto.setOrderNo("211807111119291752");
//        orderCreateRequestDto.setAmount(50000);
//        orderCreateRequestDto.setCurrencyID("TWD");
//        orderCreateRequestDto.setPrdtName("信用卡訂單");
//        orderCreateRequestDto.setReturnURL("http://10.11.22.113:8803/QPay.ApiClient-Sandbox/Store/Return");
//        orderCreateRequestDto.setBackendURL("https://sandbox.sinopac.com/funBIZ.ApiClient/AutoPush/PushSuccess");
//        orderCreateRequestDto.setPayType(OrderCreateBody.PayType.C);
//
//        OrderCreateRequest.CardParam cardParam = orderCreateRequestDto.new CardParam();
//        cardParam.setAutoBilling(OrderCreateRequest.AutoBilling.N);
//        cardParam.setExpMinutes(30);
//        orderCreateRequestDto.setCardParam(cardParam);
//
//        QpayHelper qpayHelper = new QpayHelper();
//        System.out.println(qpayHelper.CreateOrder(orderCreateRequestDto));

        SpringApplication.run(FinancialWebApplication.class, args);
    }

}
