package com.ihrm.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * @author xf
 * @create 2020-03-06-13:26
 */
@Getter
@Setter
@ConfigurationProperties("jwt.config")
public class JwtUtils {
    private String key ; //签名私钥
    private Long ttl; //签名失效时间

    /**
     * 设置认证token
     * id:登陆用户id
     * subject:登陆用户名
     */
    public String createJwt(String id, String name, Map<String,Object> map){
        //设置失效时间
        long now = System.currentTimeMillis();//当前毫秒数
        long exp = now +ttl;
        //创建jwtBuilder
        JwtBuilder builder = Jwts.builder().setId(id).setSubject(name).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        //根据map设置claims
        for (Map.Entry<String,Object> entry : map.entrySet()){
            builder.claim(entry.getKey(),entry.getValue());
        }
        //获取token
        builder.setExpiration(new Date(exp));
        String token = builder.compact();
        return token;

    }

    /**
     * 获取token字符串
     */
    public Claims parseJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }
}
