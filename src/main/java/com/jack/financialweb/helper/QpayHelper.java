package com.jack.financialweb.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jack.financialweb.exception.NotFoundException;
import com.jack.financialweb.model.*;
import com.jack.financialweb.util.EncryptUtil;
import com.jack.financialweb.util.HttpUtil;
import com.jack.financialweb.util.JsonUtil;
import com.jack.financialweb.util.SysConfigUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@Component
public class QpayHelper {
    /**
     * 取得Nonce
     *
     * @return
     * @throws IOException
     */
    private String getNonce() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ShopNo", SysConfigUtil.getShopNo());
        String response = HttpUtil.Post(SysConfigUtil.getNonceApi(), JsonUtil.toJson(map));

        return new ObjectMapper().readTree(response).get("Nonce").asText();
    }

    /**
     * 取得HashId
     *
     * @return
     */
    private String getHashId() {
        BigInteger hashA1 = new BigInteger(SysConfigUtil.getHashA1(), 16);
        BigInteger hashA2 = new BigInteger(SysConfigUtil.getHashA2(), 16);
        BigInteger hashB1 = new BigInteger(SysConfigUtil.getHashB1(), 16);
        BigInteger hashB2 = new BigInteger(SysConfigUtil.getHashB2(), 16);
        BigInteger hashA = hashA1.xor(hashA2);
        BigInteger hashB = hashB1.xor(hashB2);

        return (hashA.toString(16) + hashB.toString(16)).toUpperCase();
    }

    /**
     * 取得IV
     *
     * @return
     */
    private String getIV(String nonce) {
        String sha256hex = DigestUtils.sha256Hex(nonce);

        return sha256hex.substring(sha256hex.length() - 16, sha256hex.length()).toUpperCase();
    }

    /**
     * 取得安全簽章(Sign)
     *
     * @param order
     * @param nonce
     * @param hashId
     * @return
     */
    private String getSign(Order order,
                           String nonce,
                           String hashId) {
        StringBuilder content = new StringBuilder();
        content.append(order.toString())
                .append(nonce)
                .append(hashId);

//        System.out.println(content);
        return DigestUtils.sha256Hex(content.toString()).toUpperCase();
    }

    /**
     * 取得message
     *
     * @param order
     * @param hashId
     * @param iv
     * @return
     * @throws Exception
     */
    private String getMessage(Order order,
                              String hashId,
                              String iv) throws Exception {
        return EncryptUtil.Encrypt(JsonUtil.toJson(order), hashId, iv).toUpperCase();
    }

    /**
     * 發送API並驗證
     * @param order
     * @param responseClass
     * @param apiService
     * @return
     * @throws Exception
     */
    public String Post(Order order, Class<?> responseClass, String apiService) throws Exception {
        String hashId = getHashId();
        String nonce = getNonce();
        String iv = getIV(nonce);
        String sign = getSign(order, nonce, hashId);
        String message = getMessage(order, hashId, iv);

        Map<String, String> request = new HashMap<String, String>();
        request.put("Version", "1.0.0");
        request.put("ShopNo", SysConfigUtil.getShopNo());
        request.put("APIService", apiService);
        request.put("Sign", sign);
        request.put("Nonce", nonce);
        request.put("Message", message);

//        System.out.println("Nonce: " + nonce);
//        System.out.println("AESKey: " + hashId);
//        System.out.println("IV: " + iv);
//        System.out.println("Sign: " + sign);
//        System.out.println("Message: " + message);

        String response = HttpUtil.Post(SysConfigUtil.getOrderApi(), JsonUtil.toJson(request));
        QPayResponse qPayResponse = JsonUtil.toObject(response, QPayResponse.class);
        qPayResponse.setMessage(EncryptUtil.Decrypt(qPayResponse.getMessage(), hashId, getIV(qPayResponse.getNonce())));

        Order orderResponse = (Order) JsonUtil.toObject(qPayResponse.getMessage(), responseClass);
        //驗證response sign
        if (qPayResponse.getSign().equals(getSign(orderResponse, qPayResponse.getNonce(), hashId))) {
            return qPayResponse.getMessage();
        }

        throw new NotFoundException("簽章驗證失敗");

//        JsonNode resJson = JsonUtil.toNode(response);
//        String resNonce = resJson.get("Nonce").asText();
//        String resIv = getIV(resNonce);
//        String resSign = resJson.get("Sign").asText();
//        String resMessage = resJson.get("Message").asText();
//        resMessage = EncryptUtil.Decrypt(resMessage, hashId, resIv);


//        System.out.println("response: " + response);
//        System.out.println("resNonce: " + resNonce);
//        System.out.println("resIv: " + resIv);
//        System.out.println("resSign: " + resSign);
//        System.out.println("resMessage: " + resMessage);

//        return qPayResponse;
    }

    /**
     * 加密 send to OrderCreateAPI 並驗證
     *
     * @param request
     * @return
     * @throws Exception
     */
//    public String OrderCreate(OrderCreateRequest request) throws Exception {
//        String hashId = getHashId();
//        QPayResponse qPayResponse = Post(request, "OrderCreate", hashId);
//
//        OrderCreateResponse orderResponse = JsonUtil.toObject(qPayResponse.getMessage(), OrderCreateResponse.class);
//        //驗證response sign
//        if (qPayResponse.getSign().equals(getSign(orderResponse, qPayResponse.getNonce(), hashId))) {
//            return qPayResponse.getMessage();
//        }
//
//        throw new NotFoundException("簽章驗證失敗");
//    }

    /**
     * 加密 send to OrderPayQueryAPI 並驗證
     * @param request
     * @return
     * @throws Exception
     */
//    public String OrderPayQuery(OrderPayQueryRequest request) throws Exception {
//        String hashId = getHashId();
//        QPayResponse qPayResponse = Post(request, "OrderPayQuery", hashId);
//
//        OrderPayQueryResponse orderResponse = JsonUtil.toObject(qPayResponse.getMessage(), OrderPayQueryResponse.class);
//
//        //驗證response sign
//        if (qPayResponse.getSign().equals(getSign(orderResponse, qPayResponse.getNonce(), hashId))) {
//            return qPayResponse.getMessage();
//        }
//
//        throw new NotFoundException(orderResponse.getDescription() == null ? "簽章驗證失敗" : orderResponse.getDescription());
//    }
}
