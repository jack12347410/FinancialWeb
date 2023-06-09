package com.jack.financialweb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QPayResponse {
    @JsonProperty("Nonce")
    private String Nonce;

    @JsonProperty("Sign")
    private String Sign;

    @JsonProperty("Message")
    private String Message;
}
