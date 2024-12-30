package com.spring;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testJwtGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");
        String token = JWT.create().
                withClaim("user", claims).
                withExpiresAt(new Date(System.currentTimeMillis()+1000)).
                sign(Algorithm.HMAC256("xiuxiu"));
        System.out.println(token);
    }
    @Test
    public void testJwt() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MzEzMTUxNzJ9.n2bXvybfDAb4RmrepmWKD484xTo5Pv_4KWGA85KlwM8";
        JWTVerifier xiuxiu = JWT.require(Algorithm.HMAC256("xiuxiu")).build();
        DecodedJWT verify = xiuxiu.verify(token);
        Map<String, Claim> claims = verify.getClaims();
        System.out.println(claims.get("user"));


    }

}
