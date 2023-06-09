package com.jack.financialweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequest extends OrderCreateBody {
    @Getter
    @Setter
    public class ATMParam{
        @JsonProperty("ExpireDate")
        private Integer ExpireDate;
    }
    @Getter
    @Setter
    public class CardParam{
        @JsonProperty("AutoBilling")
        private AutoBilling AutoBilling;

        @JsonProperty("ExpBillingDays")
        private Integer ExpBillingDays;

        @JsonProperty("ExpMinutes")
        private Integer ExpMinutes;

        @JsonProperty("PayTypeSub")
        private String PayTypeSub;
    }

    @JsonProperty("ATMParam")
    private ATMParam ATMParam;

    @JsonProperty("CardParam")
    private CardParam CardParam;

    @JsonProperty("CurrencyID")
    private String CurrencyID;

    @JsonProperty("PrdtName")
    private String PrdtName;

    @JsonProperty("Memo")
    private String Memo;

    @JsonProperty("ReturnURL")
    private String ReturnURL;

    @JsonProperty("BackendURL")
    private String BackendURL;

}
