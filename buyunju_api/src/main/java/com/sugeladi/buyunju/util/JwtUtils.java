package com.sugeladi.buyunju.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {

    public static SecretKey getBase64Key() {
        String stringKey = "MyJwtSecret";
        byte[] encodeKey = Base64.getDecoder().decode(stringKey);
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }

    /**
     * 签发token
     *
     * @param userName 用户名
     * @return token
     */
    public static String create(String userName) {
        Date now = new Date(System.currentTimeMillis());
        String token = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, getBase64Key())
                .compact();

        return token;
    }

    /**
     * 解析token
     *
     * @param token token
     * @return 用户名
     */
    public static String parse(String token) {
        String username = null;
        try {
            username = Jwts.parser()
                    .setSigningKey(getBase64Key())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 检验token是否过期
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        Date expiraDate = null;
        Date currentDate = new Date();
        try {
            expiraDate = Jwts.parser()
                    .setSigningKey(getBase64Key())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getExpiration();
            if (currentDate.before(expiraDate)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
