package com.ihrm.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author xf
 * @create 2020-03-06-13:04
 */
public class CreateJwtTest {
    /**
     * jjwt创建token字符串
     * @param args
     */
    public static void main(String[] args) {
        JwtBuilder builder = Jwts.builder().setId("88").setSubject("用户信息").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "ihrm")//参数1:加密方式 ,//参数2:加密私钥
                .claim("companyId","123456")
                .claim("companyName","MS有限公司");
        String token = builder.compact();
        System.out.println(token);
    }
}
