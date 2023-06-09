package com.jack.financialweb.util;

import java.math.BigInteger;

public class SysConfigUtil {
    private static String jwtKey = "ASDAGIJGLDKSAJDIEOJDSDASPJDKASHHNJKANJKFHJFHDAHJHGJKNJASDHAIORUQPIOPUTHVKLZFEJGIOHRJGKABJKNJDF";
    private static int jwtExpireTimeSec = 30 * 60;
    private static String jwtIssuer = "com.jack";
    private static String jwtAudience = "Jack";
    private static String hashA1 = "65960834240E44B7";
    private static String hashA2 = "2831076A098E49E7";
    private static String hashB1 = "CB1AFFBF915A492B";
    private static String hashB2 = "7F242C0AA612454F";
    private static String shopNo = "NA0001_001";
    private static String nonceApi = "https://sandbox.sinopac.com/QPay.WebAPI/api/Nonce";
    private static String orderApi = "https://apisbx.sinopac.com/funBIZ/QPay.WebAPI/api/Order";

    public static String getJwtKey() {
        return jwtKey;
    }

    public static int getJwtExpireTimeSec() {
        return jwtExpireTimeSec;
    }

    public static String getJwtIssuer() {
        return jwtIssuer;
    }

    public static String getJwtAudience() {
        return jwtAudience;
    }

    public static String getHashA1() {
        return hashA1;
    }

    public static String getHashA2() {
        return hashA2;
    }

    public static String getHashB1() {
        return hashB1;
    }

    public static String getHashB2() {
        return hashB2;
    }

    public static String getShopNo() {
        return shopNo;
    }

    public static String getNonceApi() {
        return nonceApi;
    }

    public static String getOrderApi() {
        return orderApi;
    }
}
