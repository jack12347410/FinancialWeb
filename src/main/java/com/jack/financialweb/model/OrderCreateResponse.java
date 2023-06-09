package com.jack.financialweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateResponse extends OrderCreateBody {
    @Getter
    @Setter
    public class ATMParam{
        @JsonProperty("AtmPayNo")
        private String AtmPayNo;

        @JsonProperty("WebAtmURL")
        private String WebAtmURL;

        @JsonProperty("OtpUrl")
        private String OtpUrl;
    }
    @Getter
    @Setter
    public class CardParam{
        @JsonProperty("CardPayURL")
        private String CardPayURL;
    }

    @JsonProperty("ATMParam")
    private ATMParam ATMParam;

    @JsonProperty("CardParam")
    private CardParam CardParam;

    @JsonProperty("TSNo")
    private String TSNo;

    @JsonProperty("Status")
    private Status Status;

    @JsonProperty("Description")
    private String Description;


}
