package com.jack.financialweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPayQueryResponse extends Order {
    @Getter
    @Setter
    public class TSResultContent{
        @JsonProperty("APType")
        private APType APType;

        @JsonProperty("TSNo")
        private String TSNo;

        @JsonProperty("OrderNo")
        private String OrderNo;

        @JsonProperty("ShopNo")
        private String ShopNo;

        @JsonProperty("PayType")
        private PayType PayType;

        @JsonProperty("Amount")
        private Integer Amount;

        @JsonProperty("Status")
        private Status Status;

        @JsonProperty("Description")
        private String Description;

        @JsonProperty("Param1")
        private String Param1;

        @JsonProperty("Param2")
        private String Param2;

        @JsonProperty("Param3")
        private String Param3;

        @JsonProperty("LeftCCNo")
        private String LeftCCNo;

        @JsonProperty("RightCCNo")
        private String RightCCNo;

        @JsonProperty("CCExpDate")
        private String CCExpDate;

        @JsonProperty("CCToken")
        private String CCToken;

        @JsonProperty("PayDate")
        private String PayDate;

        @JsonProperty("MasterOrderNo")
        private String MasterOrderNo;
    }

    @JsonProperty("ShopNo")
    private String ShopNo;

    @JsonProperty("PayToken")
    private String PayToken;

    @JsonProperty("Date")
    private String Date;

    @JsonProperty("Status")
    private String Status;

    @JsonProperty("Description")
    private String Description;

    @JsonProperty("TSResultContent")
    private TSResultContent TSResultContent;
}
