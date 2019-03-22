package com.czc.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class JieXiJwt {
    public static void main(String[] args) {
       Claims claims =  Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NiIsInN1YiI6IuWwj-mprCIsImlhdCI6MTU1MzA2OTM0OSwiZXhwIjoxNTUzMDY5NDA5LCJyb2xlIjoiYWRtaW4ifQ.0-ankmRa27XD8nNB2TSk754CSsxwSJIKslQ1xtYAr74")
              .getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("用户名:"+claims.getSubject());
        System.out.println("创建时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户角色:"+claims.get("role"));
    }
}
