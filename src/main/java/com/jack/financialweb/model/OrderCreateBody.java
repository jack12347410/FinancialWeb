package com.jack.financialweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jack.financialweb.util.ReflectionUtil;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class OrderCreateBody extends Order{


    @JsonProperty("ShopNo")
    private String ShopNo;

    @JsonProperty("OrderNo")
    private String OrderNo;

    @JsonProperty("Amount")
    private Integer Amount;

    @JsonProperty("Param1")
    private String Param1;

    @JsonProperty("Param2")
    private String Param2;

    @JsonProperty("Param3")
    private String Param3;

    @JsonProperty("PayType")
    private PayType PayType;

}
