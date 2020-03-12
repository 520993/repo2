package com.ihrm.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author xf
 * @create 2020-03-06-13:14
 */
public class ParseJwtTest {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4OCIsInN1YiI6IueUqOaIt-S_oeaBryIsImlhdCI6MTU4MzQ3MjEzNSwiY29tcGFueUlkIjoiMTIzNDU2IiwiY29tcGFueU5hbWUiOiJNU-aciemZkOWFrOWPuCJ9.jGnWkZG74BNaTm_dL1CSSU41tXwtFyLkSCjk3sssNBk";
        Claims claims = Jwts.parser().setSigningKey("ihrm").parseClaimsJws(token).getBody();
        //私有数据存放在claims对象中
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());
        //解析claims中自定义内容
        System.out.println(claims.get("companyId"));
        System.out.println(claims.get("companyName"));
    }
}
