package com.jack.financialweb.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class EncryptUtil {

    /**
     * AES CBC加密
     *
     * @param content
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String Encrypt(String content, String key, String iv) throws Exception {
        byte[] raw = key.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ips);
        byte[] encrypted = cipher.doFinal(content.getBytes());

        return Hex.encodeHexString(encrypted);
    }

    /**
     * AES CBC解密
     * @param hexContent
     * @param key
     * @param iv
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     * @throws DecoderException
     */
    public static String Decrypt(String hexContent, String key,String iv)
            throws IOException, GeneralSecurityException, DecoderException {

        byte[] raw = key.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE,keySpec,ips);

        byte[] encrypted = Hex.decodeHex(hexContent.toCharArray());
        byte[] origin = cipher.doFinal(encrypted);
        String string = new String(origin);

        return string;

    }
}
