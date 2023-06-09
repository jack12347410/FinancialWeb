package com.jack.financialweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPayQueryRequest extends Order{

    @JsonProperty("ShopNo")
    private String ShopNo;

    @JsonProperty("PayToken")
    private String PayToken;
}
