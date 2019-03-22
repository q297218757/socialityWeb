package com.czc.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateJwt {
    public static void main(String[] args) {

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("66")
                .setSubject("小马")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"itcast")
                .setExpiration(new Date(new Date().getTime()+60000))
                .claim("role","admin");

          System.out.println(jwtBuilder.compact());
    }


}
