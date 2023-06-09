package com.jack.financialweb.util;

import com.jack.financialweb.exception.NotFoundException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static Key getHmacShaKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SysConfigUtil.getJwtKey()));
    }

    /**
     * 產生Jwt Token
     * @param email
     * @return
     */
    public static String GetToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuer(SysConfigUtil.getJwtIssuer())
                .setAudience(SysConfigUtil.getJwtAudience())
                .setExpiration(new Date(System.currentTimeMillis() + SysConfigUtil.getJwtExpireTimeSec()*1000))
                .signWith(SignatureAlgorithm.HS512, getHmacShaKey())
                .compact();
    }

    /**
     * 取得subject(email)
     * @param token
     * @return
     */
    public static String GetSubjectFormToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getHmacShaKey())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();

    }

    public static boolean ValidToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getHmacShaKey())
                    .build()
                    .parse(token);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
