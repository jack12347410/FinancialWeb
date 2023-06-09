package com.jack.financialweb.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jack.financialweb.exception.NotFoundException;
import com.jack.financialweb.model.OrderCreateBody;
import com.jack.financialweb.model.OrderCreateRequest;
import com.jack.financialweb.model.OrderCreateResponse;
import com.jack.financialweb.util.EncryptUtil;
import com.jack.financialweb.util.HttpUtil;
import com.jack.financialweb.util.JsonUtil;
import com.jack.financialweb.util.SysConfigUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
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
     * @param oc
     * @param nonce
     * @param hashId
     * @return
     */
    private String getSign(OrderCreateBody oc,
                           String nonce,
                           String hashId) {
        StringBuilder content = new StringBuilder();
        content.append(oc.toString())
                .append(nonce)
                .append(hashId);

        return DigestUtils.sha256Hex(content.toString()).toUpperCase();
    }

    /**
     * 取得message
     *
     * @param oc
     * @param hashId
     * @param iv
     * @return
     * @throws Exception
     */
    private String getMessage(OrderCreateBody oc,
                              String hashId,
                              String iv) throws Exception {
        return EncryptUtil.Encrypt(JsonUtil.toJson(oc), hashId, iv).toUpperCase();
    }

    /**
     * 訂單送出
     * @param ocreq
     * @return
     * @throws Exception
     */
    public String CreateOrder(OrderCreateRequest ocreq) throws Exception {
        String nonce = getNonce();
        String hashId = getHashId();
        String iv = getIV(nonce);
        String sign = getSign(ocreq, nonce, hashId);
        String message = getMessage(ocreq, hashId, iv);

        Map<String, String> request = new HashMap<String, String>();
        request.put("Version", "1.0.0");
        request.put("ShopNo", SysConfigUtil.getShopNo());
        request.put("APIService", "OrderCreate");
        request.put("Sign", sign);
        request.put("Nonce", nonce);
        request.put("Message", message);

//        System.out.println("Nonce: " + nonce);
//        System.out.println("AESKey: " + hashId);
//        System.out.println("IV: " + iv);
//        System.out.println("Sign: " + sign);
//        System.out.println("Message: " + message);

        String response = HttpUtil.Post(SysConfigUtil.getOrderApi(), JsonUtil.toJson(request));
        JsonNode resJson = JsonUtil.toNode(response);

        String resNonce = resJson.get("Nonce").asText();
        String resIv = getIV(resNonce);
        String resSign = resJson.get("Sign").asText();
        String resMessage = resJson.get("Message").asText();
        resMessage = EncryptUtil.Decrypt(resMessage, hashId, resIv);

//        System.out.println("response: " + response);
//        System.out.println("resNonce: " + resNonce);
//        System.out.println("resIv: " + resIv);
//        System.out.println("resSign: " + resSign);
//        System.out.println("resMessage: " + resMessage);

        OrderCreateResponse ocres = JsonUtil.toObject(resMessage, OrderCreateResponse.class);
        //驗證response sign
        if (resSign.equals(getSign(ocres, resNonce, hashId))) {
            return resMessage;
        }

        throw new NotFoundException(ocres.getDescription() == null? "簽章驗證失敗": ocres.getDescription());
    }
}
